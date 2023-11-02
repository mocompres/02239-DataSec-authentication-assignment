package com.datasec;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.security.*;
import java.util.Base64;
import java.util.Scanner;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

//import com.datasec;

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
			login();
		//HelloService servce = (HelloService) Naming.lookup("rmi://localhost:5099/hello");
        //System.out.println("---"+ servce.echo("hey server"));
			service.print(Encryption.encrypt(token, serverPublicKey),Encryption.encrypt("TestFilename", serverPublicKey), Encryption.encrypt("TestPrinter", serverPublicKey));
			service.queue(Encryption.encrypt(token, serverPublicKey),Encryption.encrypt("TestPrinter", serverPublicKey));
			service.topQueue(Encryption.encrypt(token, serverPublicKey),Encryption.encrypt("TestPrinter", serverPublicKey), 1);
			service.start(Encryption.encrypt(token, serverPublicKey));
			service.stop(Encryption.encrypt(token, serverPublicKey));
			service.restart(Encryption.encrypt(token, serverPublicKey));
			service.status(Encryption.encrypt(token, serverPublicKey),Encryption.encrypt("Testprinter", serverPublicKey));
			service.readConfig(Encryption.encrypt(token, serverPublicKey),Encryption.encrypt("TestParameter", serverPublicKey));
			service.setConfig(Encryption.encrypt(token, serverPublicKey), Encryption.encrypt("TestParameter", serverPublicKey), Encryption.encrypt("TestValue", serverPublicKey));

		} catch (Exception e) {
			System.err.println(e);
			System.out.println(e);
		}
    }

    public static boolean login() throws RemoteException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Scanner input = new Scanner(System.in);

		TokenGenerator tg = new TokenGenerator();
		String auth = "";
		Jws<Claims> userInfo = null;
		String user;
		Boolean verified = null;
		serverPublicKey =  service.getPublicKey();
		System.out.println("Enter your username");
		String userName = input.nextLine();  
		
		System.out.println("Enter your password");
		String password = input.nextLine();

		auth = service.authenticateUser(Encryption.encrypt(userName, serverPublicKey), Encryption.encrypt(password, serverPublicKey), keyPair.getPublic());
		auth = Encryption.decrypt(auth, keyPair.getPrivate());
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