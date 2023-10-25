package com.datasec;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PrintServer extends UnicastRemoteObject implements IPrintService{

    public PrintServer() throws RemoteException {
        super();
    }

    public String print(String filename, String printer){
        return "Test from Server";
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
        return null;
    }

}
