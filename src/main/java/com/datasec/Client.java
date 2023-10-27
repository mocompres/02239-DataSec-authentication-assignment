package com.datasec;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Scanner;

//import com.datasec;

public class Client {
    public static void main(String[] args) {
		try {        
			login();
		HelloService servce = (HelloService) Naming.lookup("rmi://localhost:5099/hello");
        System.out.println("---"+ servce.echo("hey server"));

		} catch (Exception e) {

		}
    }
    
    public static boolean login() throws RemoteException {
		Scanner input = new Scanner(System.in);

		PrintService service = new PrintService();
		String auth = "";
		
		while (!auth.equals("Login succesful!")) {	
		    System.out.println("Enter your username");
			String userName = input.nextLine();  
			
		    System.out.println("Enter your password");
			String password = input.nextLine();  
			
			auth = service.authenticateUser(userName, password);		
			
			System.out.println(auth);
		}
		input.close();
		return true;
	}
}