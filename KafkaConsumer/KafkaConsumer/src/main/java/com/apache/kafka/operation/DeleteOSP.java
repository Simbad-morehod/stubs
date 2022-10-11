package com.apache.kafka.operation;

import com.apache.kafka.common.CommonEnv;

public class DeleteOSP {
    public String deleteOS(String UUIDtaken,int id){
        String msgDel = "{\n" +
                "        \"uuid\": \""+ UUIDtaken +"\",\n" +
                "    }";
        return msgDel;
    }
}
