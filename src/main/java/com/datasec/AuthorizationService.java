package com.datasec;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;

public class AuthorizationService {

    ArrayList<ArrayList<String>> policyList = new ArrayList<>();
    ArrayList<String> userList = new ArrayList<>();

    public AuthorizationService(){
        BufferedReader reader = null;
        try{
        reader = new BufferedReader(new FileReader("src/main/java/com/datasec/system.txt"));

            String line;
            // Reading lines from the file until the end is reached
            while ((line = reader.readLine()) != null) {
                
                String[] policy = line.split(": ");

                if (!userList.contains(policy[0])) {
                    userList.add(policy[0]);
                    String[] theUsersoperations = line.split(", ");

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
}