package com.ru.operation;

public class Sdtb extends AbcstactOperation {
    public  String sendMsg(String sessionId) {
        return msg = "{\n" +
                "        \"emails\": [\n" +
                "            {\n" +
                "                \"email\": \"uh@tu.ru\",\n" +
                "            }\n" +
                "        ],\n" +
                 "}";
    }
}
