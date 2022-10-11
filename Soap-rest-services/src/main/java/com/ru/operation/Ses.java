package com.ru.operation;

public class Ses extends AbcstactOperation {
    public  String sendMsg(String sessionId) {
        return msg = "{\n" +
                 "  \"sessionId\": \""+sessionId+"\",\n" +
                "}";
    }
}
