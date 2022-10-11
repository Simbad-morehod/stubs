package com.ru.operation;

import com.google.crypto.tink.subtle.Random;

public class RGU {
    String msg;
    public String sendMsg(){
        int mdmId= Random.randInt(9999999 + 111111);
        return msg="{\n" +
                "                \"requiredPositions\": [\n" +
                "                    \"CEO\",\n" +
                "                    \"EMPLOYEE\",\n" +
                "                    \"FOUNDER\",\n" +
                "                    \"PROFITPARTICIPAT\",\n" +
                "                    \"BENEFICIARY\"\n" +
                "                ]\n" +
                "        }";
    }
}
