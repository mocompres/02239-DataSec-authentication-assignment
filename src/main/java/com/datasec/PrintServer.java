package com.datasec;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PrintServer extends UnicastRemoteObject implements IPrintService{

    public PrintServer() throws RemoteException {
        super();
    }

    public String print(String filename, String printer){
        System.out.println("<user> Print: parameters " + filename + ", " + printer);
        return "Printing " + filename + " on printer " + printer;
    }
    public String queue(String printer){
        System.out.println("<user> Queue: parameters " + printer);
        return "Queue for print " + printer + ": 0";
    }
    public void topQueue(String printer, int job){
        System.out.println("<user> topQueue: parameters " + printer + ", " + job);
    }   

    public void start(){
        System.out.println("<user> Start");
    }

    public void stop(){
        System.out.println("<user> Stop");
    }

    public void restart(){
        System.out.println("<user> Restart");
    }

    public String status(String printer){
        System.out.println("<user> Status: parameters " + printer);
        return "Status for printer " + printer;
    }

    public String readConfig(String parameter){
        System.out.println("<user> ReadConfig: parameters " + parameter);
        return "Config for parameter " + parameter;
    }

    public void setConfig(String parameter, String value){
        System.out.println("<user> SetConfig: parameters " + parameter + ", " + value);
    }

    public String authenticateUser(String username, String password) throws RemoteException{
        System.out.println("Authenticate User: " + username + ", " + password);
        return null;
    }

}
