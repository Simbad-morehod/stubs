package com.ru.operation;

public class SNK extends AbcstactOperation {
    public static String sendMsg(String EBMID) {
        return msg = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xmime=\"http://www.w3.org/2005/05/xmlmime\">\n" +
                "    <soapenv:Header/>\n" +
                "    <soapenv:Body>\n" +
                "    </soapenv:Body>\n" +
                "</soapenv:Envelope>";
    }
}