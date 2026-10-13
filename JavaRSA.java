/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javarsa;

/**
 *
 * @author fatema
 */

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.Signature;

public class JavaRSA {

    public static void main(String[] args) throws Exception {

     
        KeyPairGenerator keyGen =
                KeyPairGenerator.getInstance("RSA");

        keyGen.initialize(2004);

        KeyPair keyPair = keyGen.generateKeyPair();


        String message = "Hello Bob";

        byte[] messageBytes =
                message.getBytes(StandardCharsets.UTF_8);

        System.out.println("Original message: " + message);

        Signature signer =
                Signature.getInstance("SHA256withRSA");

        signer.initSign(keyPair.getPrivate());
        signer.update(messageBytes);

        byte[] digitalSignature = signer.sign();

        System.out.println("Signature generated successfully!");
        System.out.println("Signature length: "
                + digitalSignature.length + " bytes");

        boolean originalResult = verifySignature(
                keyPair.getPublic(),
                messageBytes,
                digitalSignature
        );

        System.out.println("Original message verification: "
                + (originalResult ? "VALID" : "INVALID"));

        String modifiedMessage = "HELLO FATEMA";

        byte[] modifiedBytes =
                modifiedMessage.getBytes(StandardCharsets.UTF_8);

        boolean modifiedResult = verifySignature(
                keyPair.getPublic(),
                modifiedBytes,
                digitalSignature
        );

        System.out.println("Modified message verification: "
                + (modifiedResult ? "VALID" : "INVALID"));
    }

    public static boolean verifySignature(
            PublicKey publicKey,
            byte[] message,
            byte[] digitalSignature) throws Exception {

        Signature verifier =
                Signature.getInstance("SHA256withRSA");

        verifier.initVerify(publicKey);
        verifier.update(message);

        return verifier.verify(digitalSignature);
    }
}
