package com.ru.controller.getToken;

import com.auth0.jwt.algorithms.Algorithm;
import com.google.crypto.tink.subtle.Base64;
import com.google.crypto.tink.subtle.EllipticCurves;

import java.security.GeneralSecurityException;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;

public abstract class AlgorithmBuilder {

    private AlgorithmBuilder() {
    }

    public static Algorithm buildECDSA256(String publicKey, String privateKey) throws GeneralSecurityException {

        ECPublicKey ecPublicKey = EllipticCurves.getEcPublicKey(Base64.decode(publicKey));
        ECPrivateKey ecPrivateKey = EllipticCurves.getEcPrivateKey(Base64.decode(privateKey));

        return Algorithm.ECDSA256(ecPublicKey, ecPrivateKey);
    }
}