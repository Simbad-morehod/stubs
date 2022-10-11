package com.ru.commons;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class CommonEnv {
    public static long TestTime = 0;
    public static Random RANDOM = new Random();
    private static HttpURLConnection urlConnection = null;

    public static String now(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        return df.format(calendar.getTime());
    }

    public static void logMessage(String module,String message){
        System.out.println(now() + "\t " + module + ":" + message);
    }
    public static long randomTimeout(long delay, long deviation){
        long timeout = 0;
        long maxDelay, minDelay;
        int range;
        if (delay < deviation){
            delay = deviation;
        }
        maxDelay = (delay + deviation);
        minDelay = (delay - deviation);
        range = (int) (maxDelay - minDelay);

        timeout = RANDOM.nextInt(range+1);
        System.out.println("Время отклика: " + (timeout + minDelay));
        return (long) (timeout + minDelay);
    }

    public static void Delay(long Pause){
        try {
            Thread.sleep(Pause);
        }catch (InterruptedException e){
            e.getMessage();
            Thread.currentThread().interrupt();
        }
    }
    public  static void cycleList(){
       // toUrl("http://localhost:8085/onb?op=size&num=sd");
        postXml("http://localhost:8084/onb?op=size&num=sd","s");
 //       Delay(3600000);
//        cycleList();
    }
    private static void postXml(String urlAd, String xmlString){
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new StringHttpMessageConverter());
        restTemplate.setMessageConverters(messageConverters);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_XML);
        HttpEntity<String> request = new HttpEntity<>(xmlString, headers);
        final ResponseEntity<String> response = restTemplate.postForEntity(urlAd,request,String.class);
    }
    private static synchronized void toUrl(String urlAdress){
        try{
            URL url = new URL(urlAdress);
            urlConnection = (HttpURLConnection) url.openConnection();
       //     urlConnection.setRequestMethod("Post");
//            urlConnection.setRequestProperty("Content-Type","text/xml");
//            urlConnection.setRequestProperty("Body", msg);
            urlConnection.setDoOutput(true);
        }catch (Exception e){
            e.printStackTrace();
        }
        try (InputStream is = urlConnection.getInputStream();
             BufferedReader rd = new BufferedReader(new InputStreamReader(is))){
            StringBuilder reap = new StringBuilder();
//            Thread.sleep(15);
            while (rd.ready())
                reap.append(rd.ready());
        }catch (Exception e){e.printStackTrace();}
        System.out.println("SANK Send Resp");
        if(urlConnection != null) urlConnection.disconnect();
    }
}
