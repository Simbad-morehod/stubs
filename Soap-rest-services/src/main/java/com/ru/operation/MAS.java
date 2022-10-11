package com.ru.operation;

public class MAS extends AbcstactOperation {
    public static String sendMsg(String messageId, String EBMID) {
        return msg = "<env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                "xmlns:wsa=\"http://www.w3.org/2005/08/addressing\">" +
                "<env:Header><wsa:Action>2</wsa:Action>" +
                 "</EBMHeader><pers:DataArea/></2></env:Body></env:Envelope>\n";
    }
}
