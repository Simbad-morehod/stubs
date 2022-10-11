package com.ru.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import com.ru.commons.CommonEnv;
import com.ru.operation.*;
import com.ru.operation.pp.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.time.LocalDate;
import java.util.*;

@RestController
public class Receciver {

    //Список времени ответа от заглушек

    int Stime =5000;
    int Ktime =60000;
    int Datatime = 600;


    String messageId;
    private static HttpURLConnection urlConnection = null;
    public HashMap<String,String> listKey = new HashMap<>();
    String EBMIDlist;
    private int counter = 0;
//    RestTemplate restTemplate = new RestTemplate();
//    List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
    String urlXmlTest = "http://localhost:8084/ord";
    String urlXmlSNK = "http://localhost:8084/bord";

    @RequestMapping("/timeout")
    public String giveListSystemTimeout(){
        return "Stime = " + Stime;
    }
    @RequestMapping("/timeout/{name}/{timeout}")
    public String changeTimeout(@PathVariable String name,@PathVariable int timeout){
        switch (name){
            case "Stime": this.Stime = timeout;
            break;
            case "Ktime":this.Ktime = timeout;
            break;
            case "Datatime":this.Datatime = timeout;
            break;
            default:
                System.out.println("NotCorrect name");
                break;
        }
        return name + " now = " + timeout;
    }

    //----------------------- dfgds-------
    //------------------------sfsd--------
    @RequestMapping("/sdf/1")
    public String dbs(@RequestBody String body) throws IOException {
       System.out.println("Receive msg  1" );
        messageId = StringUtils.substringBetween(body, "<ns2:SenderMessageID>", "</ns2:SenderMessageID>");
        String EBMID = StringUtils.substringBetween(body, "<ns2:EBMID>", "</ns2:EBMID>");
        System.out.println("1 wait");
        CommonEnv.Delay(CommonEnv.randomTimeout(Stime, Stime /10));
        System.out.println("send response 1");
        return MAS.sendMsg(messageId, EBMID);
    }

    @RequestMapping("/sdf/2")
    public String sgdf(@RequestBody String body) throws IOException {
        System.out.println("Receive msg  2");
        messageId = StringUtils.substringBetween(body, "<ns2:SenderMessageID>", "</ns2:SenderMessageID>");
        String EBMID = StringUtils.substringBetween(body, "<ns2:EBMID>", "</ns2:EBMID>");
        System.out.println("2 wait");
        CommonEnv.Delay(CommonEnv.randomTimeout(Stime, Stime /10));
        System.out.println("send response 2 ");
        return MOB.sendMsg(messageId, EBMID);
    }
//----------- ассинхрон------------
     @RequestMapping("/jbkj/3")
       public String sdfg(@RequestBody String body, HttpServletRequest request) throws IOException {
        System.out.println("Receive msg  3");
        messageId = StringUtils.substringBetween(body, "<ns2:SenderMessageID>", "</ns2:SenderMessageID>");
        String EBMID = StringUtils.substringBetween(body, "<ns2:EBMID>", "</ns2:EBMID>");
        postXml("http://localhost:8084/onb?op=add&num="+EBMID,"");
         System.out.println("send responseOK 3");
         return SNK.sendMsg(EBMID);
    }
    @RequestMapping("/ord")
    public String store(@RequestParam(value = "op", required = false) String operation,
                        @RequestParam(value = "num", required = false)String EBMIDlist){
        String result = "no";
        switch (operation){
            case "add":
                System.out.println("add in store");
                result = listKey.put(EBMIDlist,"success");
                break;
            case "size":
                cleanList();
                result = "ok " ;
                break;
        }
        return result;

        }
        public void cleanList(){
        try{
            if (listKey.size() != 0){
                System.out.println("Send assync response from 4 to O " + listKey.size());
                listKey.forEach((EBMIDlist, status) -> {
                    CommonEnv.Delay(100);
                    listKey.remove(EBMIDlist);
                    postXml(urlXmlSNK, SNK.sendMsg(EBMIDlist));

                });
            }
            CommonEnv.Delay(CommonEnv.randomTimeout(Ktime, 60));
            cleanList();}
        catch (Exception ex){
            System.out.println("__________________________________");
            ex.printStackTrace();
         //   listKey.remove(EBMIDlist);
         //   listKey.put(EBMIDlist,"success");
            CommonEnv.Delay(CommonEnv.randomTimeout(200, 60));
           cleanList();
        }
    }

    @GetMapping
    public String dd(){
        return SNK.sendMsg( "777");
    }


    private synchronized void postXml(String urlAd, String xmlString){
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new StringHttpMessageConverter());
        restTemplate.setMessageConverters(messageConverters);
        System.out.println("Send Assync Snk");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_XML);
        HttpEntity<String> request = new HttpEntity<>(xmlString, headers);
        final ResponseEntity<String> response = restTemplate.postForEntity(urlAd,request,String.class);
    }
    //---------описание 3 --------

            @RequestMapping("/ses/{ses}/{sId}/{key}")
    public ResponseEntity<JsonNode> asda(@PathVariable String ses,@PathVariable String sId,@PathVariable String key) throws IOException {
        System.out.println("Receive msg   " + ses +"/"+ sId +"/"+ key);
         System.out.println(" wait");
        CommonEnv.Delay(CommonEnv.randomTimeout(Datatime, Datatime /2));

        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(new Ses().sendMsg(ses));
        System.out.println("send response sessionData /ses/{ses}/{sId}/{key}" );
        return ResponseEntity.ok(json);
    }

   @RequestMapping("/ses/{data}/{serviceId}")
    public ResponseEntity<JsonNode> adsf(@PathVariable char[] data, @PathVariable String serviceId) throws IOException {
       String SESSIONID = new String(data);
        System.out.println("Receive msg  sessionData " + SESSIONID);
        System.out.println("as wait");
        CommonEnv.Delay(CommonEnv.randomTimeout(Datatime, Datatime /2));

        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(new Ses().sendMsg(SESSIONID));
        System.out.println("send response sessionData /ses/{data}/{serviceId}" );
        return ResponseEntity.ok(json);
    }

    @RequestMapping("/cd/route/{id}/api/v1/il")
    public ResponseEntity<JsonNode> sad(@RequestHeader Map<String, String> headers, @RequestBody(required = false) Map<String, String> body) throws IOException {
        System.out.println("IncompleteLeads   ===================");

        System.out.println("headers   =   " + headers);
        System.out.println("BODY    =     "+body);
        System.out.println("=====================");
        CommonEnv.Delay(CommonEnv.randomTimeout(1000, 600));
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(new Bs().sendMsg());
        System.out.println("send response" + json);
        return ResponseEntity.ok(json);
    }
    @RequestMapping("/cd/route/{id}/api/v1.0/l")
    public ResponseEntity<JsonNode> vs(@RequestBody(required = false) String body) throws IOException {
        //System.out.println(headers);
        System.out.println("/cd/route/{id}/api/v1.0/l ==================");
        System.out.println("BODY =  "+ body);
        CommonEnv.Delay(CommonEnv.randomTimeout(1000, 600));
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(new Bs().sendMsg());
        System.out.println("send response" + json);
        return ResponseEntity.ok(json);
    }
    //-------ОС Запросы
    @RequestMapping("/api/v1.0/o")
    public ResponseEntity asd(@RequestBody String body) throws IOException {
        System.out.println("Receive msg  ОС" );
        System.out.println("ОС wait");
        CommonEnv.Delay(CommonEnv.randomTimeout(1000, 600));
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(new MAS().sendMsg("gj","gl"));
        System.out.println("send response " + json);
        return ResponseEntity.ok(json);
    }
    @RequestMapping(value = "/api/v1/d-sd/s",
            produces = "application/json",
            consumes = "application/json",
            method = RequestMethod.POST)
    public ResponseEntity uyvgi(@RequestBody String body) throws IOException {
        System.out.println("Receive msg  СЛ" );
        System.out.println(body);
        CommonEnv.Delay(CommonEnv.randomTimeout(1000, 600));
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(new Sdtp().sendMsg("gfdg"));
        System.out.println("send response " + json);
        return ResponseEntity.ok(json);
    }



    @GetMapping("/client/{guid}/info")
    public ExternalClient info(@PathVariable String guid) {
        ExternalClient result;
        CommonEnv.Delay(CommonEnv.randomTimeout(5000, 600));
        if (guid.equals("1111")) {
            result = null;
        } else {
            result = new ExternalClient();
            result.setFullName("test");
            result.setChkoYear(BigDecimal.valueOf(4300000));
            result.setClientYears(3);
            result.setInn(String.valueOf(123456));
            if (!guid.startsWith("7")) {
                result.setLoyaltyStatus("Профи");
            }
            result.setPeriod(LocalDate.now());
            result.setProducts(3);
            result.setId(guid);
        }
        return result;
    }
    //---------Расширенный профиль
    @RequestMapping("/api/v1/geRP")
    public ResponseEntity<JsonNode> RP() throws IOException {
        System.out.println("Receive msg  RP ");
        System.out.println("RP wait");
        CommonEnv.Delay(CommonEnv.randomTimeout(1000, 600));

        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(new RP().sendMsg());
        System.out.println("send response RasshirenniyProfilUrLic " );
        return ResponseEntity.ok(json);
    }
    //----------Кросс
    @RequestMapping("/api/v1/ds/get")
    public ResponseEntity<JsonNode> ds(@RequestBody String body, HttpServletRequest request) throws IOException {
        System.out.println("Receive msg  ds" + body);
        if(body.contains("subSystemId")){
            CommonEnv.Delay(CommonEnv.randomTimeout(1000, 600));

            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(new CRTPP().sendMsg());
            System.out.println("send response ds " );
            return ResponseEntity.ok(json);
        }
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(new CRTT().sendMsg());
        System.out.println("send response crossRef " );
        return ResponseEntity.ok(json);
    }
    //--------- список
    @RequestMapping("/cd/r/8011/a/gac")
    public ResponseEntity<JsonNode> pad(@RequestBody String body, HttpServletRequest request) throws IOException {
        System.out.println("Receive msg  pad");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(new Bs().sendMsg());
        System.out.println("send response pad " );
        return ResponseEntity.ok(json);
    }
    //--------ЭЦП
    @RequestMapping("/a/s/f/v")
    public ResponseEntity<JsonNode> stp() throws IOException {
        System.out.println("Receive msg  stp");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(new Stripp().sendMsg());
        System.out.println("send response stp" );
        return ResponseEntity.ok(json);
    }
    //--------Авторизация
    @RequestMapping("/api/smb/kk/kk/user-k/kk")
    public ResponseEntity<JsonNode> afjllk(@RequestParam(value = "userPrincipalName",required = false) String userPrincipalName) throws IOException {
        System.out.println("Receive msg  afjllk");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(new Tripp().sendMsg());
        System.out.println("send response afjllk" );
        return ResponseEntity.ok(json);
    }
}
