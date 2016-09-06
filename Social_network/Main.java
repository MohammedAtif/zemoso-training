/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.Scanner;

/**
 *
 * @author zemoso
 */



public class Main {

    /**
     * @param args the command line arguments
     */
    
    
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner inputReader = new Scanner(System.in);
        System.out.println("Choose how to log in(Facebook,Google,Linkedin,Twitter):");
        String x=inputReader.next();
        System.out.println(x.toLowerCase());
        obj o=new obj();
        o.login(x.toLowerCase());  
    }
        
}
