package com.datasec;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

public interface IPrintService extends Remote {
    String print(String token,String filename, String printer) throws IOException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InvalidKeySpecException;   // prints file filename on the specified printer
    String queue(String token,String printer) throws IOException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InvalidKeySpecException;   // lists the print queue for a given printer on the user's display in lines of the form <job number>   <file name>
    void topQueue(String token,String printer, int job) throws RemoteException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;   // moves job to the top of the queue
    void start(String token) throws RemoteException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;  // starts the print server
    void stop(String token) throws RemoteException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;   // stops the print server
    void restart(String token) throws RemoteException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;   // stops the print server, clears the print queue and starts the print server again
    String status(String token,String printer) throws IOException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;  // prints status of printer on the user's display
    String readConfig(String token,String parameter) throws IOException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InvalidKeySpecException;   // prints the value of the parameter on the print server to the user's display
    void setConfig(String token, String parameter, String value) throws IOException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;   // sets the parameter on the print server to value

    String authenticateUser(String username, String password, PublicKey pk) throws RemoteException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;

    PublicKey getPublicKey() throws RemoteException;
}
