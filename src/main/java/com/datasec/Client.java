package com.datasec;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.security.*;
import java.util.Base64;
import java.util.Scanner;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Client {

	static IPrintService service; //new PrintService();
	static String token;
	static PublicKey serverPublicKey;

	static KeyPair keyPair;

	public static void main(String[] args) {

		try {
			KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
			generator.initialize(2048);
			KeyPair pair = generator.generateKeyPair();
			keyPair = pair;
			service = (IPrintService) Naming.lookup("rmi://localhost:5099/print");
			if(login()){
				//statusCheck();
				service.print(Encryption.encrypt(token, serverPublicKey),Encryption.encrypt("TestFilename", serverPublicKey), Encryption.encrypt("TestPrinter", serverPublicKey));
				service.queue(Encryption.encrypt(token, serverPublicKey),Encryption.encrypt("TestPrinter", serverPublicKey));
				service.topQueue(Encryption.encrypt(token, serverPublicKey),Encryption.encrypt("TestPrinter", serverPublicKey), 1);
				service.start(Encryption.encrypt(token, serverPublicKey));
				service.stop(Encryption.encrypt(token, serverPublicKey));
				service.restart(Encryption.encrypt(token, serverPublicKey));
				service.status(Encryption.encrypt(token, serverPublicKey),Encryption.encrypt("Testprinter", serverPublicKey));
				service.readConfig(Encryption.encrypt(token, serverPublicKey),Encryption.encrypt("TestParameter", serverPublicKey));
				service.setConfig(Encryption.encrypt(token, serverPublicKey), Encryption.encrypt("TestParameter", serverPublicKey), Encryption.encrypt("TestValue", serverPublicKey));
			}
			
		} catch (Exception e) {
			System.err.println(e);
			System.out.println(e);
		}
    }

    public static boolean login() throws RemoteException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Scanner input = new Scanner(System.in);

		String auth;
		serverPublicKey =  service.getPublicKey();
		System.out.println("Enter your username");
		String userName = input.nextLine();  
		
		System.out.println("Enter your password");
		String password = input.nextLine();

		String usernameEncrypted = Encryption.encrypt(userName, serverPublicKey);
		String passwordEncrypted = Encryption.encrypt(password, serverPublicKey);

		auth = service.authenticateUser(usernameEncrypted, passwordEncrypted, keyPair.getPublic());auth = Encryption.decrypt(auth, keyPair.getPrivate());

		token = auth;
		//token = Encryption.decrypt(auth, keyPair.getPrivate());


		if (token != "Password was incorrect" || 
				token != "Couldn't find the user")
				return true;
			else
				return false;		
	}

	public static String getToken(){
		return token;
	}
	
	public static void statusCheck(){
		TokenGenerator tg = new TokenGenerator();

		Jws<Claims> userInfo = null;
		String user;
		Boolean verified = null;

		System.out.println("The token: " + token);

		user = tg.theUserBasedOnToken(token);
		System.out.println("The username: " + user);

		verified = tg.isTokenValid(token);
		System.out.println("Token verification status: " + verified);

		userInfo = tg.infoBasedOnToken(token);
		System.out.println("The userObject: " + userInfo);
	}
}