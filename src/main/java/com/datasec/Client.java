package com.datasec;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Scanner;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

//import com.datasec;

public class Client {
    public static void main(String[] args) {
		try {        
			login();
		//HelloService servce = (HelloService) Naming.lookup("rmi://localhost:5099/hello");
        //System.out.println("---"+ servce.echo("hey server"));

		} catch (Exception e) {
			System.err.println(e);
			System.out.println(e);
		}
    }
    
    public static boolean login() throws RemoteException {
		Scanner input = new Scanner(System.in);

		PrintService service = new PrintService();
		TokenGenerator tg = new TokenGenerator();
		String auth = "";
		Jws<Claims> userInfo = null;
		Boolean verified = null;
		
		System.out.println("Enter your username");
		String userName = input.nextLine();  
		
		System.out.println("Enter your password");
		String password = input.nextLine();  
		
		auth = service.authenticateUser(userName, password);		
		
		System.out.println(auth);

		System.out.println("Enter your token");
		String token = input.nextLine();  
		userInfo = tg.theUserBasedOnToken(token);
		System.out.println(userInfo);
		

		verified = tg.isTokenValid(token);
		System.out.println(verified);

		input.close();
		return true;
	}
}