/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jonassimonsen
 */
public class ChatClient extends Observable implements Runnable {
    
Socket socket;
    private int port;
    private InetAddress serverAddress;
    private Scanner input;
    private PrintWriter output;
    static EchoGUI GUI;
    
    public ChatClient(EchoGUI gui) {
        GUI = gui;
    }

    public ChatClient() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void connect(String address, int port) throws UnknownHostException, IOException {
        this.port = port;
        serverAddress = InetAddress.getByName(address);
        socket = new Socket(serverAddress, port);
        input = new Scanner(socket.getInputStream());
        output = new PrintWriter(socket.getOutputStream(), true);  //Set to true, to get auto flush behaviour
        System.out.println("Connect started");
        start();
    }

    public void send(String msg) {
        System.out.println("sent");
        output.println(msg);
    }

    public void stopCl() throws IOException {
        output.println(ProtocolStrings.STOP);
    }

    @Override
    public void run() {
        while (true){
        System.out.println("Run started");
        
        String msg = input.nextLine();
        System.out.println("input over");
        System.out.println(msg);
        System.out.println("Received: " + msg); //Important Blocking call
        GUI.messageArrived(msg);
        if (msg.equals(ProtocolStrings.STOP)) {
            try {
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(CharClint.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        }
    }

    public static void main(String[] args) {
        int port = 9090;
        String ip = "localhost";
        if (args.length == 2) {
            port = Integer.parseInt(args[0]);
            ip = args[1];
        }
        try {
            Chatclient tester = new CharClient(GUI);
            tester.connect(ip, port);
            System.out.println("Sending 'Hello world'");
            tester.send("Hello World");
            System.out.println("Waiting for a reply");
            
            tester.stopCl();
            //System.in.read();      
        } catch (UnknownHostException ex) {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
