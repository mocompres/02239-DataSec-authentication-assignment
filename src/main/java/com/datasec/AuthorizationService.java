package com.datasec;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;

public class AuthorizationService {
    private class Role{
        protected ArrayList<String> userList = new ArrayList<>();
        protected ArrayList<String> policyList = new ArrayList<>();

        protected String name = null;
    }
    ArrayList<Role> roleList = new ArrayList<>();

    public AuthorizationService(){
        BufferedReader reader = null;
        ArrayList<String> roleIndexList = new ArrayList<>();
        try{
        reader = new BufferedReader(new FileReader("src/main/java/com/datasec/policy.txt"));

            String line;
            // Reading lines from the file until the end is reached
            while ((line = reader.readLine()) != null) {
                
                String[] policy = line.split(": ");

                if (!roleIndexList.contains(policy[0])) {
                    roleIndexList.add(policy[0]);
                    roleList.add(new Role());
                    roleList.get(roleIndexList.indexOf(policy[0])).name = policy[0];
                    String[] theRoleoperations = policy[1].split(", ");
                    for (int j = 0; j < theRoleoperations.length; j++) {
                        roleList.get(roleIndexList.indexOf(policy[0])).policyList.add(theRoleoperations[j]);
                    }
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        reader = null;
        try{
            reader = new BufferedReader(new FileReader("src/main/java/com/datasec/roles.txt"));

            String line;
            // Reading lines from the file until the end is reached
            while ((line = reader.readLine()) != null) {

                String[] users = line.split(": ");

                if (roleIndexList.contains(users[0])) {
                    String[] theRoleusers = users[1].split(", ");
                    for (int j = 0; j < theRoleusers.length; j++) {
                        roleList.get(roleIndexList.indexOf(users[0])).userList.add(theRoleusers[j]);
                    }
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void outputLists(){
        for (Role r : roleList) {
            System.out.println("The role: " + r.name);
            System.out.println("The the permissions: " + r.policyList);
            System.out.println("The the users: " + r.userList);
        }
    }

    public boolean isOperationAllowed(String username, String operation){
        int i;
        boolean allowed = false;
        for (Role r: roleList) {
            if(r.userList.contains(username) && (r.policyList.contains(operation) || r.policyList.contains("*"))){
                allowed = true;
                break;
            }
        }
        return allowed;
    }

}