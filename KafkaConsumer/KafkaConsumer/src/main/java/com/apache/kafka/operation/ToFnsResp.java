package com.apache.kafka.operation;

import com.apache.kafka.common.CommonEnv;

public class ToFnsResp {
    public String response(String UUIDtaken,String bik, String innUl){
        String msgFns = "{\n" +
                "  \"queryID\": \""+ UUIDtaken +"\",\n" +
                "}";
        return msgFns;
    }
}
