package coe817lab2;

import java.security.*;
import javax.crypto.*;
import java.util.Scanner;


public class JEncryptDES {
    public static void main(String[] args) {
        byte[] input, encryptedText, plaintext;
        Scanner scan = new Scanner(System.in);
        String userInput = null;
        KeyGenerator keyGen = null;
        SecretKey key = null;
        Cipher cipher = null;

        System.out.println("Please enter: 'No body can see me'.\n");
        userInput = scan.nextLine();
        scan.close();

        //converts the userinput string into a byte for encryption
        input = userInput.getBytes();

        try{
            //sets the key generator to DES mode and generated the secret DES key
            keyGen = KeyGenerator.getInstance("DES");
            key = keyGen.generateKey();

            //sets the cipher object to DES mode and initialize it to encryption mode
            cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            //Encrypt the input
            encryptedText = cipher.doFinal(input); 

            System.out.println("The encrypted text is: " + new String(encryptedText));

            //reinitialize in decrytion mode
            cipher.init(Cipher.DECRYPT_MODE, key);

            //decrypt the message
            plaintext = cipher.doFinal(encryptedText);

            System.out.println("The decrypted text is: " + new String(plaintext));
        }
        catch(IllegalBlockSizeException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException e){}
    }
}