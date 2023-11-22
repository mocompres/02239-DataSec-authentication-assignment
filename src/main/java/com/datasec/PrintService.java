package com.datasec;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Scanner;

public class PrintService extends UnicastRemoteObject implements IPrintService{

    static KeyPair Keys;
    private AuthorizationService authorizationService = new AuthorizationService();
    public PrintService() throws RemoteException, NoSuchAlgorithmException {
        super();
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        KeyPair pair = generator.generateKeyPair();
        Keys = pair;
    }

    public PublicKey getPublicKey() throws RemoteException {
        return Keys.getPublic();
    }
    public String print(String token,String filename, String printer) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, IOException, InvalidKeySpecException {
        token = Encryption.decrypt(token, Keys.getPrivate());
        filename = Encryption.decrypt(filename, Keys.getPrivate());
        printer = Encryption.decrypt(printer, Keys.getPrivate());
        TokenGenerator tokenGenerator = new TokenGenerator();
        String user = tokenGenerator.theUserBasedOnToken(token);
        File publicKeyFile = new File(user+".key");
        byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        PublicKey pk = keyFactory.generatePublic(publicKeySpec);
        if(!authorizationService.isOperationAllowed(user, "print"))
            return Encryption.encrypt("Access denied", pk);
        System.out.println("<" + user + "> Print: parameters " + filename + ", " + printer);
        return Encryption.encrypt("Printing " + filename + " on printer " + printer, pk);
    }
    public String queue(String token,String printer) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InvalidKeySpecException, IOException {
        token = Encryption.decrypt(token, Keys.getPrivate());
        printer = Encryption.decrypt(printer, Keys.getPrivate());
        TokenGenerator tokenGenerator = new TokenGenerator();
        String user = tokenGenerator.theUserBasedOnToken(token);
        File publicKeyFile = new File(user+".key");
        byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        PublicKey pk = keyFactory.generatePublic(publicKeySpec);
        if(!authorizationService.isOperationAllowed(user, "queue"))
            return Encryption.encrypt("Access denied", pk);
        System.out.println("<" + user + "> Queue: parameters " + printer);
        return Encryption.encrypt("Queue for print " + printer + ": 0", pk);
    }
    public String topQueue(String token,String printer, int job) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, IOException, InvalidKeySpecException {
        token = Encryption.decrypt(token, Keys.getPrivate());
        printer = Encryption.decrypt(printer, Keys.getPrivate());
        TokenGenerator tokenGenerator = new TokenGenerator();
        String user = tokenGenerator.theUserBasedOnToken(token);
        File publicKeyFile = new File(user+".key");
        byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        PublicKey pk = keyFactory.generatePublic(publicKeySpec);
        if(!authorizationService.isOperationAllowed(user, "topQueue"))
            return Encryption.encrypt("Access denied", pk);
        System.out.println("<" + user + "> topQueue: parameters " + printer + ", " + job);
        return Encryption.encrypt("Job set to top of queue", pk);
    }   

    public String start(String token) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InvalidKeySpecException, IOException {
        token = Encryption.decrypt(token, Keys.getPrivate());
        TokenGenerator tokenGenerator = new TokenGenerator();
        String user = tokenGenerator.theUserBasedOnToken(token);
        File publicKeyFile = new File(user+".key");
        byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        PublicKey pk = keyFactory.generatePublic(publicKeySpec);
        if(!authorizationService.isOperationAllowed(user, "start"))
            return Encryption.encrypt("Access denied", pk);
        System.out.println("<" + user + "> Start");
        return Encryption.encrypt("Startet", pk);
    }

    public String stop(String token) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, IOException, InvalidKeySpecException {
        token = Encryption.decrypt(token, Keys.getPrivate());
        TokenGenerator tokenGenerator = new TokenGenerator();
        String user = tokenGenerator.theUserBasedOnToken(token);
        File publicKeyFile = new File(user+".key");
        byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        PublicKey pk = keyFactory.generatePublic(publicKeySpec);
        if(!authorizationService.isOperationAllowed(user, "stop"))
            return Encryption.encrypt("Access denied", pk);
        System.out.println("<" + user + "> Stop");
        return Encryption.encrypt("Stopped", pk);
    }

    public String restart(String token) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, IOException, InvalidKeySpecException {
        token = Encryption.decrypt(token, Keys.getPrivate());
        TokenGenerator tokenGenerator = new TokenGenerator();
        String user = tokenGenerator.theUserBasedOnToken(token);
        File publicKeyFile = new File(user+".key");
        byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        PublicKey pk = keyFactory.generatePublic(publicKeySpec);
        if(!authorizationService.isOperationAllowed(user, "restart"))
            return Encryption.encrypt("Access denied", pk);
        System.out.println("<" + user + "> Restart");
        return Encryption.encrypt("Restarted", pk);
    }

    public String status(String token,String printer) throws InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, IOException {
        token = Encryption.decrypt(token, Keys.getPrivate());
        printer = Encryption.decrypt(printer, Keys.getPrivate());
        TokenGenerator tokenGenerator = new TokenGenerator();
        String user = tokenGenerator.theUserBasedOnToken(token);
        File publicKeyFile = new File(user+".key");
        byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        PublicKey pk = keyFactory.generatePublic(publicKeySpec);
        if(!authorizationService.isOperationAllowed(user, "status"))
            return Encryption.encrypt("Access denied", pk);
        System.out.println("<" + user + "> Status: parameters " + printer);
        return Encryption.encrypt("Status for printer " + printer,pk);
    }

    public String readConfig(String token,String parameter) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, IOException, InvalidKeySpecException {
        parameter = Encryption.decrypt(parameter, Keys.getPrivate());
        token = Encryption.decrypt(token, Keys.getPrivate());
        TokenGenerator tokenGenerator = new TokenGenerator();
        String user = tokenGenerator.theUserBasedOnToken(token);
        File publicKeyFile = new File(user+".key");
        byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        PublicKey pk = keyFactory.generatePublic(publicKeySpec);
        if(!authorizationService.isOperationAllowed(user, "readConfig"))
            return Encryption.encrypt("Access denied", pk);
        System.out.println("<" + user + "> ReadConfig: parameters " + parameter);
        return Encryption.encrypt("Config for parameter " + parameter, pk);
    }

    public String setConfig(String token,String parameter, String value) throws IOException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {
        token = Encryption.decrypt(token, Keys.getPrivate());
        TokenGenerator tokenGenerator = new TokenGenerator();
        String user = tokenGenerator.theUserBasedOnToken(token);
        File publicKeyFile = new File(user+".key");
        byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        PublicKey pk = keyFactory.generatePublic(publicKeySpec);
        if(!authorizationService.isOperationAllowed(user, "setConfig"))
            return Encryption.encrypt("Access denied", pk);
        System.out.println("<" + user + "> SetConfig: parameters " + Encryption.decrypt(parameter, Keys.getPrivate()) + ", " + Encryption.decrypt(value, Keys.getPrivate()));
        return Encryption.encrypt("Config set", pk);
    }

    private String hash(String pass, String salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(salt.getBytes(StandardCharsets.UTF_8));
        byte[] bytes = md.digest(pass.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public String authenticateUser(String username, String password, PublicKey pk) throws RemoteException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        username = Encryption.decrypt(username, Keys.getPrivate());
        password = Encryption.decrypt(password, Keys.getPrivate());
        BufferedReader reader = null;
        try {
            // Creating a BufferedReader object to read from a file
            reader = new BufferedReader(new FileReader("src/main/java/com/datasec/system.txt"));

            String line;
            String token = null;
            // Reading lines from the file until the end is reached
            while ((line = reader.readLine()) != null) {
                
                String[] loginStrings = line.split(", ");
                //System.out.println(Arrays.toString(loginStrings));
                
                if(loginStrings[0].equals(username)){
                    if(loginStrings[1].equals(hash(password, loginStrings[2]))){
                        System.out.println("Authenticated user: " + username);
                        TokenGenerator tokenGenerator = new TokenGenerator();
                        token = tokenGenerator.generateToken(username);
                        try (FileOutputStream fos = new FileOutputStream(username + ".key")) {
                            fos.write(pk.getEncoded());
                        }
                        return Encryption.encrypt(token, pk);
                    }
                    else{
                        System.out.println("The user" + username + " not authenticated");
                        return Encryption.encrypt("Password was incorrect", pk);
                    }
                }
                if(line == null && token == null)
                    return Encryption.encrypt("Couldn't find the user", pk);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
