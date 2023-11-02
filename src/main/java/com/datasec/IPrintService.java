package com.datasec;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IPrintService extends Remote {
    String print(String token,String filename, String printer) throws RemoteException;   // prints file filename on the specified printer
    String queue(String token,String printer)  throws RemoteException;   // lists the print queue for a given printer on the user's display in lines of the form <job number>   <file name>
    void topQueue(String token,String printer, int job)  throws RemoteException;   // moves job to the top of the queue
    void start(String token)  throws RemoteException;  // starts the print server
    void stop(String token)  throws RemoteException;   // stops the print server
    void restart(String token)  throws RemoteException;   // stops the print server, clears the print queue and starts the print server again
    String status(String token,String printer)  throws RemoteException;  // prints status of printer on the user's display
    String readConfig(String token,String parameter)  throws RemoteException;   // prints the value of the parameter on the print server to the user's display
    void setConfig(String token, String parameter, String value) throws RemoteException;   // sets the parameter on the print server to value

    String authenticateUser(String username, String password) throws RemoteException;
}
