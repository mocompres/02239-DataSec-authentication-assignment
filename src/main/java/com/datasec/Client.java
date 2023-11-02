package com.datasec;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

//import com.datasec;

public class Client {

	static IPrintService service; //new PrintService();
	static String token;

	public static void main(String[] args) {
		try {        
			service = (IPrintService) Naming.lookup("rmi://localhost:5099/print");
			login();
		//HelloService servce = (HelloService) Naming.lookup("rmi://localhost:5099/hello");
        //System.out.println("---"+ servce.echo("hey server"));
			service.print(token,"TestFilename", "TestPrinter");
			service.queue(token,"TestPrinter");
			service.topQueue(token,"TestPrinter", 1);
			service.start(token);
			service.stop(token);
			service.restart(token);
			service.status(token,"Testprinter");
			service.readConfig(token,"TestParameter");
			service.setConfig(token, "TestParameter", "TestValue");

		} catch (Exception e) {
			System.err.println(e);
			System.out.println(e);
		}
    }
    
    public static boolean login() throws RemoteException {
		Scanner input = new Scanner(System.in);

		TokenGenerator tg = new TokenGenerator();
		String auth = "";
		Jws<Claims> userInfo = null;
		String user;
		Boolean verified = null;
		
		System.out.println("Enter your username");
		String userName = input.nextLine();  
		
		System.out.println("Enter your password");
		String password = input.nextLine();  
		
		auth = service.authenticateUser(userName, password);

		System.out.println(auth);

		token = auth;

		user = tg.theUserBasedOnToken(token);
		System.out.println("The username:" + user);
		

		verified = tg.isTokenValid(token);
		System.out.println("Token verification status:" + verified);

		userInfo = tg.infoBasedOnToken(token);
		System.out.println("The userObject:" + userInfo);

		input.close();
		return true;
	}
}