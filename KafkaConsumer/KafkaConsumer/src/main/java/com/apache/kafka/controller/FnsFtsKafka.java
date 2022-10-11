package com.apache.kafka.controller;

import com.apache.kafka.operation.ToFnsResp;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Date;
import java.util.Random;

@Component
@RestController
public class FnsFtsKafka {
    @Autowired
    KafkaTemplate<String, JsonNode> kafkaTemplateFns;

    private static final String TOPIC_DEST_FNS = "response";
    private final String consumerTopicFns = "request";
    public static Random RANDOM = new Random();
    int timeoutInit = 6000;
    String UUIDtaken = "40d0c0e5-b7b8-44d9-8d73-ba1e7890761d";
    String bik="044525411";
    String innUl="4101176294";

    @KafkaListener(topics = consumerTopicFns ,groupId = "nt", concurrency = "10")
    public void consumer(String message) throws InterruptedException, JsonProcessingException {
        bik = StringUtils.substringBetween(message,"bik\":\"","\",");
        UUIDtaken = StringUtils.substringBetween(message,"queryID\":\"","\",\"innUL");
        innUl = StringUtils.substringBetween(message,"innUL\":\"","\",");

        Instant now2 = Instant.now();
        System.out.println(" time received"+ Date.from(now2) + " UUID " + UUIDtaken + "; bik: "+bik+ "; innUl: "+ innUl);
        ObjectMapper mapper =new ObjectMapper();
        JsonNode jsonNode;
        System.out.println(message);
        Thread.sleep(RANDOM.nextInt((timeoutInit/10+1))+timeoutInit);
            jsonNode = mapper.readTree(new ToFnsResp().response(UUIDtaken,bik,innUl));
            kafkaTemplateFns.send(TOPIC_DEST_FNS, jsonNode);
        System.out.println(" time done "+ Date.from(now2));
    }
}
