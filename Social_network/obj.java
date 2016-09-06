package main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author zemoso
 */

interface factory{
    public void login(String social_network_name);
}
public class obj implements factory {

    @Override
    public void login(String social_network_name) {
        
        switch(social_network_name)
        {
            case "facebook":
                System.out.println("You choose Facebook");
                break;
            case "google":
                System.out.println("You choose Google");
                break;
            case "linkdin":
                System.out.println("You choose Linkedin");
                break;
            case "twiter":
                System.out.println("You choose Twitter");
                break;
                default:
                System.out.println("Wrong choice!!");
    
        }
    }
    
}
