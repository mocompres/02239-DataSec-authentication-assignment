package main.java.com.datasec;

import java.rmi.RemoteException;

public interface IPrintServer {
    String print(String filename, String printer);   // prints file filename on the specified printer
    String queue(String printer);   // lists the print queue for a given printer on the user's display in lines of the form <job number>   <file name>
    void topQueue(String printer, int job);   // moves job to the top of the queue
    void start();   // starts the print server
    void stop();   // stops the print server
    void restart();   // stops the print server, clears the print queue and starts the print server again
    String status(String printer);  // prints status of printer on the user's display
    String readConfig(String parameter);   // prints the value of the parameter on the print server to the user's display
    void setConfig(String parameter, String value);   // sets the parameter on the print server to value

    String authenticateUser(String username, String password) throws RemoteException;
}
