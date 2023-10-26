package com.datasec;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Scanner;

public class PrintService implements IPrintService{
    public String print(String filename, String printer){
        return null;
    }
    public String queue(String printer){
        return null;
    }
    public void topQueue(String printer, int job){
    }

    public void start(){
    }

    public void stop(){
    }

    public void restart(){
    }

    public String status(String printer){
        return null;
    }

    public String readConfig(String parameter){
        return null;
    }

    public void setConfig(String parameter, String value){
    }
    public String authenticateUser(String username, String password) throws RemoteException{
       
        BufferedReader reader = null;
        try {
            // Creating a BufferedReader object to read from a file
            reader = new BufferedReader(new FileReader("src/main/java/com/datasec/system.txt"));

            String line;
            // Reading lines from the file until the end is reached
            while ((line = reader.readLine()) != null) {
                
                String[] loginStrings = line.split(", ");
                System.out.println(Arrays.toString(loginStrings));
                
                if(loginStrings[0].equals(username)){
                    if(loginStrings[1].equals(password)){
                        System.out.println("generate token");
                        break;
                    }
                    else{
                        System.out.println("Password was incorrect");
                        break;
                    }
                }else
                    System.out.println("Couldn't find username");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
