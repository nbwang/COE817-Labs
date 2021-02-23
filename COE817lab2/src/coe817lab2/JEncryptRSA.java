package coe817lab2;

import java.security.*;
import javax.crypto.*;
import java.util.Scanner;

public class JEncryptRSA {
    public static void main(String[] args) {
        byte[] input, encryptedText, plaintext;
        Scanner scan = new Scanner(System.in);
        String userInput = null;
        KeyPairGenerator keyGen = null;
        Key publicKey, privateKey;
        KeyPair keyPair;
        Cipher cipher = null;

        System.out.println("Please enter: 'No body can see me'.\n");
        userInput = scan.nextLine();
        scan.close();

        //converts the userinput string into a byte for encryption
        input = userInput.getBytes();

        try{
            //sets the key generator to RSA mode and generate the private and public keys
            keyGen = KeyPairGenerator.getInstance("RSA");
            keyPair = keyGen.generateKeyPair();
            publicKey = keyPair.getPublic();
            privateKey = keyPair.getPrivate();

            //sets the cipher object to RSA mode and set it to encryption mode with the public key
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            //encrypt the userinput
            encryptedText = cipher.doFinal(input);

            System.out.println("The encrypted text is: " + new String(encryptedText));

            //reinitialize in decrytion mode with the private key
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            //decrypt the message
            plaintext = cipher.doFinal(encryptedText);

            System.out.println("The decrypted text is: " + new String(plaintext));
        }
        catch(IllegalBlockSizeException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException e){}
    }
}