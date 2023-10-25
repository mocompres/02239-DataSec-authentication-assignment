package com.datasec;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Scanner;

//import com.datasec;

public class Client {
    public static void main(String[] args) {
		try {
        HelloService servce = (HelloService) Naming.lookup("rmi://localhost:5099/hello");
        System.out.println("---"+ servce.echo("hey server"));
		} catch (Exception e) {

		}
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