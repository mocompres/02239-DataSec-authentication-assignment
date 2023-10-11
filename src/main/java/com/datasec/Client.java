package com.datasec;

import java.rmi.Naming;

public class Client {
    public static void main(String[] args) {
        HelloService servce = (HelloService) Naming.lookup("rmi://localhost:5099/hello");
        System.out.println("---"+ servce.echo("hey server"));
    }
}