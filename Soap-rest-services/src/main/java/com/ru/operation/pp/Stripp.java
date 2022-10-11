package com.ru.operation.pp;

import com.ru.operation.AbcstactOperation;

public class Stripp extends AbcstactOperation {
    public  String sendMsg() {
        return msg = "{\n" +
                "    \"certificate\": {\n" +
                "        \"notAfter\": \"2023-03-19T12:56:35\",\n" +
                "        \"notBefore\": \"2021-03-19T12:56:35\",\n" +
                "    }\n" +
                "}";
    }
}
