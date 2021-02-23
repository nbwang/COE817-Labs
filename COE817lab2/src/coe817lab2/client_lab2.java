package coe817lab2;

import java.io.*;
import java.net.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;

public class client_lab2 {

    public static void main(String [] args){
        int port = 60000;
        String clientID = "INITIATOR A";
        String km = "NETWORK SECURITY";
        String ks = null;
        Socket clientSocket;
        byte[] encryptedText, plainTextByte;
        byte[] fromServer = null;
        SecretKeyFactory keyFac = null;
        SecretKey key = null;
        Cipher cipher = null;
        String serverID = null;
        String[] parsedData = null;
        String message = null;
        String delims = "[ ]+";

        try {
            System.out.println("Attempting to connect to server\n");

            //Creating socket
            clientSocket = new Socket("localhost", port);
            
            System.out.println("Server Connected!\n");

            // Creates input an output for sending information
            DataInputStream input = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());

            //Sending client id
            System.out.println("Sending the server client ID" + clientID);
            output.writeUTF(clientID);

            //Recieve the cipher text message 2 from client with session key
            input.read(fromServer);
            System.out.println("\nThe ciphher recived was:" + fromServer);

            //sets the key generator to DES mode and generated the secret DES key from master key
            keyFac = SecretKeyFactory.getInstance("DES");
            key = keyFac.generateSecret(new DESKeySpec(km.getBytes()));

            //Sets the cipher object ot decryption mode
            cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key);

            //Decrypt the message and printout
            plainTextByte = cipher.doFinal(fromServer);
            message = new String(plainTextByte);
            System.out.println("\nThe decrypted text is: " + message);

            //Parse the text to seperate the key
            parsedData = message.split(delims);
            ks = parsedData[0];
            serverID = parsedData[2];

            //Sets the key generator to DES mode and generated the secret DES key from session key
            keyFac = SecretKeyFactory.getInstance("DES");
            key = keyFac.generateSecret(new DESKeySpec(ks.getBytes()));

            //Sets the cipher object ot encryption mode
            cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            //Convert the serverID to byte
            plainTextByte = serverID.getBytes();

            //Encrypt the text
            encryptedText = cipher.doFinal(plainTextByte); 
            System.out.println("\nThe encrypted text to be sent to the server is :" + encryptedText);

            //Send the encrypted text to the server
            output.write(encryptedText);
            
            //closing the input and output and socket after use
            input.close();
            output.close();
            clientSocket.close();
        }
        catch (NoSuchAlgorithmException | IOException | InvalidKeyException | InvalidKeySpecException
        | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
    e.printStackTrace();
}
    }
}
