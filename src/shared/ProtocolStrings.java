/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shared;

import chatserver.ChatServer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author jonassimonsen
 */
public class ProtocolStrings {
    //Register as new user
    //USER#{NAME}
    public static String USER = "USER#";
    public static String NAME;
    
    //Send message to another user
    //MSG#{reciever}#{message}
    public static String MSG = "MSG#";
    public static String RECIEVER;
    public static String MESSAGE;
    
    
    //Disconnect from chat server
    //STOP#
    public static String STOP = "STOP#";
    
    //User list containing all online users. Should be sent when a new Client connect or disconnects
    //USERLIST#
    public static String USERLIST = "USERLIST#";
    
    
    
    
    
    
}
