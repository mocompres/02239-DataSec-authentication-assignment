package com.datasec;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

public class PrintService extends UnicastRemoteObject implements IPrintService{
    
    public PrintService() throws RemoteException {
        super();
    }

    public String print(String token,String filename, String printer){
        TokenGenerator tokenGenerator = new TokenGenerator();
        String user = tokenGenerator.theUserBasedOnToken(token);
        System.out.println("<" + user + "> Print: parameters " + filename + ", " + printer);
        return "Printing " + filename + " on printer " + printer;
    }
    public String queue(String token,String printer){
        TokenGenerator tokenGenerator = new TokenGenerator();
        String user = tokenGenerator.theUserBasedOnToken(token);
        System.out.println("<" + user + "> Queue: parameters " + printer);
        return "Queue for print " + printer + ": 0";
    }
    public void topQueue(String token,String printer, int job){
        TokenGenerator tokenGenerator = new TokenGenerator();
        String user = tokenGenerator.theUserBasedOnToken(token);
        System.out.println("<" + user + "> topQueue: parameters " + printer + ", " + job);
    }   

    public void start(String token){
        TokenGenerator tokenGenerator = new TokenGenerator();
        String user = tokenGenerator.theUserBasedOnToken(token);
        System.out.println("<" + user + "> Start");
    }

    public void stop(String token){
        TokenGenerator tokenGenerator = new TokenGenerator();
        String user = tokenGenerator.theUserBasedOnToken(token);
        System.out.println("<" + user + "> Stop");
    }

    public void restart(String token){
        TokenGenerator tokenGenerator = new TokenGenerator();
        String user = tokenGenerator.theUserBasedOnToken(token);
        System.out.println("<" + user + "> Restart");
    }

    public String status(String token,String printer){
        TokenGenerator tokenGenerator = new TokenGenerator();
        String user = tokenGenerator.theUserBasedOnToken(token);
        System.out.println("<" + user + "> Status: parameters " + printer);
        return "Status for printer " + printer;
    }

    public String readConfig(String token,String parameter){
        TokenGenerator tokenGenerator = new TokenGenerator();
        String user = tokenGenerator.theUserBasedOnToken(token);
        System.out.println("<" + user + "> ReadConfig: parameters " + parameter);
        return "Config for parameter " + parameter;
    }

    public void setConfig(String token,String parameter, String value){
        TokenGenerator tokenGenerator = new TokenGenerator();
        String user = tokenGenerator.theUserBasedOnToken(token);
        System.out.println("<" + user + "> SetConfig: parameters " + parameter + ", " + value);
    }
    
    public String authenticateUser(String username, String password) throws RemoteException{
        
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
                    if(loginStrings[1].equals(password)){
                        System.out.println("Authenticated user: " + username);
                        TokenGenerator tokenGenerator = new TokenGenerator();
                        token = tokenGenerator.generateToken(username);
                        return token;
                    }
                    else{
                        System.out.println("The user" + username + " not authenticated");
                        return "Password was incorrect";
                    }
                }
                if(line == null && token == null)
                    return "Couldn't find the user";
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
