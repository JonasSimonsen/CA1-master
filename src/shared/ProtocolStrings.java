/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shared;

/**
 *
 * @author jonassimonsen
 */

public class ProtocolStrings {

    public static String STOP = "STOP#";

    public static String connectUser(String name) {
        return "USER#" + name;
    }

    public static String MSGfromUser(String recievers, String msg) {
        return "MSG#" + recievers + "#" + msg;
    }

    public static String MSGtoUser(String sender, String msg) {
        return "MSG#" + sender + "#" + msg;
    }

    public static String userList(String users) {
        return "USERLIST#" + users;
    }

}
