package com.datasec;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

//import com.datasec;

public class Client {
    public static void main(String[] args) throws NotBoundException, MalformedURLException, RemoteException {
        IPrintService service = (IPrintService) Naming.lookup("rmi://localhost:5099/print");
        //System.out.println("---"+ service.print("", ""));
		service.print("TestFilename", "TestPrinter");
		service.queue("TestPrinter");
		service.topQueue("TestPrinter", 1);
		service.start();
		service.stop();
		service.restart();
		service.status("Testprinter");
		service.readConfig("TestParameter");
		service.setConfig("TestParameter", "TestValue");
    }

	
	static Scanner input = new Scanner(System.in);
    
    public boolean login(PrintServer service) throws RemoteException {
		String auth = "";
		
		while (!auth.equals("Login succesful!")) {	
		    System.out.println("Enter your username");
			String userName = input.nextLine();  
			
		    System.out.println("Enter your password");
			String password = input.nextLine();  
			
			auth = service.authenticateUser(userName, password);		
			
			System.out.println(auth);
		}
		return true;
	}
}