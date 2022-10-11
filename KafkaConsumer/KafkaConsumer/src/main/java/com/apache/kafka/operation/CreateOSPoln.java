package com.apache.kafka.operation;

import com.apache.kafka.common.CommonEnv;

import java.util.UUID;

public class CreateOSPoln {

    public String create(String UUIDtaken, int id){
        String msgResp = "{\n" +
                "    \"uuid\": \""+UUIDtaken +"\",\n" +
                "}";
        return msgResp;
    }
}
