package com.ru.operation.pp;

import com.ru.operation.AbcstactOperation;

public class Tripp extends AbcstactOperation {
    public  String sendMsg() {
        return msg = "{\n" +
                "  \"roles\": [\n" +
                "    \"ROLE\"\n" +
                "  ],\n" +
                "}";
    }
}
