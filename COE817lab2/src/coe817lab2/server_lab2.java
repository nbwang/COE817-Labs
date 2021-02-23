package coe817lab2;

import java.io.*;
import java.net.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;

public class server_lab2 {
    public static void main(String[] args) {
        int port = 60000;
        String Serverid = "RESPONDER B";
        String km = "NETWORK SECURITY";
        String ks = "RYERSON";
        ServerSocket serverSocket;
        byte[] encryptedText, plainTextByte;
        byte[] fromClient = null;
        SecretKeyFactory keyFac = null;
        SecretKey key = null;
        Cipher cipher = null;
        String clientID = null;
        String plainText = null;

        try {
            // Creating the server socket with the predefined port
            serverSocket = new ServerSocket(port);
            System.out.println("Waiting for Client Connection\n");

            // Listening for clients on the socket
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client Found!\n");

            // Creates input an output for sending information
            DataInputStream input = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());

            // Printout for client Id
            clientID = input.readUTF();
            System.out.println("The client's id is:" + clientID);

            // sets the key generator to DES mode and generated the secret DES key
            keyFac = SecretKeyFactory.getInstance("DES");
            key = keyFac.generateSecret(new DESKeySpec(km.getBytes()));

            //Creates message 2 with space to sperate the key, IDA, and IDB and converts the string to bytes
            plainText = ks + " " + clientID + " " + Serverid;
            plainTextByte = plainText.getBytes();

            //Sets the cipher object ot encryption mode
            cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            //Encrypt the text
            encryptedText = cipher.doFinal(plainTextByte); 
            System.out.println("\nThe encrypted text to be sent to the client is :" + encryptedText);

            //Sending the encrypted text to the client
            output.write(encryptedText);

            //Recieve the cipher text message 3 from client
            input.read(fromClient);
            System.out.println("\nThe ciphher recived was:" + fromClient);

            //decode the cipher using the session key
            keyFac = SecretKeyFactory.getInstance("DES");
            key = keyFac.generateSecret(new DESKeySpec(ks.getBytes()));

            //reinitialize in decrytion mode and decrypting the message
            cipher.init(Cipher.DECRYPT_MODE, key);
            plainTextByte = cipher.doFinal(fromClient);
            System.out.println("\nThe decrypted text is: " + new String(plainTextByte));

            //closing the input and output and server socket after use
            input.close();
            output.close();
            serverSocket.close();

        } 
        catch (NoSuchAlgorithmException | IOException | InvalidKeyException | InvalidKeySpecException
                | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
    }
}
