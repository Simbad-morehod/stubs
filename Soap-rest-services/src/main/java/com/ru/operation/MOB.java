package com.ru.operation;

public class MOB extends AbcstactOperation {
    public static String sendMsg(String messageId, String EBMID) {
        return msg = "<env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                    "/ns2:Sender><RequestEBMID>5ecb5eecb32e48efb3694b6471d1b326</RequestEBMID>" +
                "</EBMHeader><ebm:DataArea/></1></env:Body></env:Envelope>\n";
    }
}
