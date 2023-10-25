package com.datasec;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PrintServer extends UnicastRemoteObject implements IPrintService{

    public PrintServer() throws RemoteException {
        super();
    }

    public String print(String filename, String printer){
        return "Printing " + filename + " on printer " + printer;
    }
    public String queue(String printer){
        return "Queue for print " + printer + ": 0";
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
        return "Status for printer " + printer;
    }

    public String readConfig(String parameter){
        return "Config for parameter " + parameter;
    }

    public void setConfig(String parameter, String value){
    }

    public String authenticateUser(String username, String password) throws RemoteException{
        return null;
    }

}
