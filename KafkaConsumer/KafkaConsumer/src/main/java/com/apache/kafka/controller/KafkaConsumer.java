package com.apache.kafka.controller;

import com.apache.kafka.operation.CreateOSPoln;
import com.apache.kafka.operation.DeleteOSP;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@Component
@RestController
public class KafkaConsumer {

    @Autowired
    KafkaTemplate<String,JsonNode> kafkaTemplate;

    private static final String TOPIC_DEST = "authority-publisher";
    private final String consumerTopic2 = "BO_FULL";
    private final String consumerTopic = "authority-request";
    StopWatch watch = new StopWatch();
    public static Random RANDOM = new Random();
    int timeoutInit = 100;
    String UUIDtaken = "123";
    int id;

    //Подняли лисенер и слушаем, concurrency - регулирует сколько слушателей
    @KafkaListener(topics = consumerTopic ,groupId = "1357", concurrency = "6")
    public void consumer(String message) throws InterruptedException, JsonProcessingException {
        UUIDtaken = StringUtils.substringBetween(message,"\"uuid\":\"","\",\"risId");
        Instant now2 = Instant.now();
        System.out.println(" time received"+ Date.from(now2) + " UUID " + UUIDtaken);
        id = RANDOM.nextInt(100000 + 1);
        Instant now = Instant.now();
        ObjectMapper mapper =new ObjectMapper();
        JsonNode jsonNode;
        System.out.println(message);
        Thread.sleep(RANDOM.nextInt((timeoutInit/10+1))+timeoutInit);
        watch.start();
        if(message.contains("DELETE")) {
            jsonNode = mapper.readTree(new DeleteOSP().deleteOS(UUIDtaken,id));
            kafkaTemplate.send(TOPIC_DEST, jsonNode);
        }else {
            jsonNode = mapper.readTree(new CreateOSPoln().create(UUIDtaken,id));
            kafkaTemplate.send(TOPIC_DEST, jsonNode);
        }
        watch.stop();
        System.out.println("Trans time " + watch.getTotalTimeMillis());
        System.out.println(" time done "+ Date.from(now2));
    }

    // Отправляем через рест, в случае если из заглушки отправляем запросы
    @GetMapping("/publish")
    public String publishMessage() throws JsonProcessingException {
        id++;
        Instant now = Instant.now();
        ObjectMapper mapper =new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(new CreateOSPoln().create(UUID.randomUUID().toString(),id));
        kafkaTemplate.send(TOPIC_DEST, jsonNode);
        String sentData = "Send in " + Date.from(now);
        System.out.println(sentData + "  " + jsonNode);
        return "Send in " + Date.from(now);
    }
//Отправляем запросы в кафку через заглушку
    @PostMapping("/publish/{dest}")
    public String publishMessagePost(@RequestBody String body,@PathVariable String dest) throws JsonProcessingException {
        Instant now = Instant.now();
        ObjectMapper mapper =new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(body);
        kafkaTemplate.send(dest, jsonNode);
        String sentData = "Send in " + Date.from(now);
        System.out.println(sentData + "  " + jsonNode + " " + dest);
        return "Send in " + Date.from(now);
    }
    Map<Integer, String> ds = new HashMap<>();
    //Задаем таймаут
    @GetMapping("/timeout/{timeoutInit}")
    public String changeTimeout(@PathVariable int timeoutInit){
        if(ds.containsKey(timeoutInit)){
            System.out.println("Est");
        }
        ds.put(timeoutInit,"est");
        this.timeoutInit = timeoutInit;
        return timeoutInit+ " now for OS Polnomochiya";
    }

    @GetMapping("/tack")
    public String checkRandomTime(){
//        String tio = String.valueOf(RANDOM.nextInt((timeoutInit/10+1))+timeoutInit);
//        System.out.println(RANDOM.nextInt((timeoutInit/10+1))+timeoutInit);
//        return tio;
        Calendar calendar = Calendar.getInstance();
        LocalDateTime localDateTime = LocalDateTime.now();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSXXX");

        return df.format(calendar.getTime()) ;
    }
}
