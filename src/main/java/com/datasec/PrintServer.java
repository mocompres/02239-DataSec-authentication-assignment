package com.datasec;

import java.rmi.RemoteException;

public class PrintServer implements IPrintServer{
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
        return null;
    }

}
