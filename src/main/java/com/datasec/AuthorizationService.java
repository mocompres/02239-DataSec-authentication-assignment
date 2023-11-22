package com.datasec;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class AuthorizationService {

    ArrayList<ArrayList<String>> policyList = new ArrayList<>();
    ArrayList<String> userList = new ArrayList<>();

    public AuthorizationService(){
        BufferedReader reader = null;
        try{
        reader = new BufferedReader(new FileReader("src/main/java/com/datasec/policy.txt"));

            String line;
            // Reading lines from the file until the end is reached
            while ((line = reader.readLine()) != null) {
                
                String[] policy = line.split(": ");

                if (!userList.contains(policy[0])) {
                    userList.add(policy[0]);
                    String[] theUsersoperations = policy[1].split(", ");
                    policyList.add(new ArrayList());
                    for (int j = 0; j < theUsersoperations.length; j++) {
                        policyList.get(userList.indexOf(policy[0])).add(theUsersoperations[j]);
                    }
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void outputLists(){
        for (int i = 0; i < userList.size(); i++) {
            System.out.println("The user: " + userList.get(i));
            System.out.println("The the permissions: " + policyList.get(i));
        }
    }

    public boolean isOperationAllowed(String username, String operation){
        int i;
        if (userList.contains(username)) {
            i = userList.indexOf(username);
            if (policyList.get(i).contains(operation))
                return true;
            else
                return false;
        }else
            return false;
    }


}