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
			/*service.print(Encryption.encrypt(token, serverPublicKey),Encryption.encrypt("TestFilename", serverPublicKey), Encryption.encrypt("TestPrinter", serverPublicKey));
			service.queue(Encryption.encrypt(token, serverPublicKey),Encryption.encrypt("TestPrinter", serverPublicKey));
			service.topQueue(Encryption.encrypt(token, serverPublicKey),Encryption.encrypt("TestPrinter", serverPublicKey), 1);
			service.start(Encryption.encrypt(token, serverPublicKey));
			service.stop(Encryption.encrypt(token, serverPublicKey));
			service.restart(Encryption.encrypt(token, serverPublicKey));
			service.status(Encryption.encrypt(token, serverPublicKey),Encryption.encrypt("Testprinter", serverPublicKey));
			service.readConfig(Encryption.encrypt(token, serverPublicKey),Encryption.encrypt("TestParameter", serverPublicKey));
			service.setConfig(Encryption.encrypt(token, serverPublicKey), Encryption.encrypt("TestParameter", serverPublicKey), Encryption.encrypt("TestValue", serverPublicKey));
			*/
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

		token = auth;
		System.out.println("The token: " + token);

		user = tg.theUserBasedOnToken(token);
		System.out.println("The username:" + user);
		

		verified = tg.isTokenValid(token);
		System.out.println("Token verification status:" + verified);

		userInfo = tg.infoBasedOnToken(token);
		System.out.println("The userObject:" + userInfo);
		

		userExecution(user);
		

		input.close();
		return true;
	}

	public static void executeOperations(String operation){		
		try{
            service = (IPrintService) Naming.lookup("rmi://localhost:5099/print");
            String tokenEncrypted = Encryption.encrypt(token, serverPublicKey);

            switch (operation) {
                case "print":
                    service.print(tokenEncrypted ,Encryption.encrypt("TestFilename", serverPublicKey), Encryption.encrypt("TestPrinter", serverPublicKey));
                    break;
                case "queue":
		        	service.queue(tokenEncrypted,Encryption.encrypt("TestPrinter", serverPublicKey));
                    break;
                case "topQueue":
			        service.topQueue(tokenEncrypted,Encryption.encrypt("TestPrinter", serverPublicKey), 1);
                    break;
                case "start":
			        service.start(tokenEncrypted);
                    break;
                case "stop":
			        service.stop(tokenEncrypted);
                    break;
                case "restart":
			        service.restart(tokenEncrypted);
                    break;
                case "status":
			        service.status(tokenEncrypted,Encryption.encrypt("Testprinter", serverPublicKey));
                    break;
                case "readConfig":
			        service.readConfig(tokenEncrypted,Encryption.encrypt("TestParameter", serverPublicKey));
                    break;
                case "setConfig":
			        service.setConfig(tokenEncrypted, Encryption.encrypt("TestParameter", serverPublicKey), Encryption.encrypt("TestValue", serverPublicKey));
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
			System.err.println(e);
			System.out.println(e);
		}
	}

	public static void userExecution(String userName){
		AuthorizationService authorizationService = new AuthorizationService();

        System.out.println("Type the operation you want to run: ");
        Scanner input = new Scanner(System.in);
        String operation = input.nextLine();

        if (authorizationService.isOperationAllowed(userName, operation)) {
            executeOperations(operation);
        }else
            System.out.println("You dont have permission");
    }
}