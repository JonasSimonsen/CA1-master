/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import shared.ProtocolStrings;

/**
 *
 * @author jonassimonsen
 */
public class ClientHandler {
    public class ClientHandler extends Thread {

    ChatServer mainServer;
    public Socket s;
    Scanner input;
    PrintWriter writer;
    public String clientUsername;

    //declare possible states for client
    //start the state of the client as connecting. We just instiated the object
    public ClientHandler(Socket socket, ChatServer mainServer) throws IOException {
        this.mainServer = mainServer;
        s = socket;
        input = new Scanner(s.getInputStream());
        writer = new PrintWriter(s.getOutputStream(), true);

        clientUsername = "";
    }
    
    public synchronized void handleIncomingProtocolCommand(String message) {
        ServerCommandParser cp = new ServerCommandParser(this, mainServer);
        cp.analyseCommand(message);//ASK TEACHER: make this a start() so we can return to execution? or this will break the
        //syncronised block?
    }

    @Override
    public void run() {
        String message = input.nextLine();
        //Logger is thread-safe, thank god

        while (!message.equals(ProtocolStrings.STOP)) {
            chatLogger.log(Level.INFO, this.getName() + " received the message: " + message);
            handleIncomingProtocolCommand(message);
            message = input.nextLine();

        }
        chatLogger.log(Level.INFO, this.getName() + " received the message: " + message);
        handleIncomingProtocolCommand(message);

        try {
            writer.close();
            s.close();
        } catch (IOException ex) {
            chatLogger.log(Level.SEVERE, null, ex);
        }
        chatLogger.log(Level.INFO, this.getName() + "closed a Connection");
    }

    public void send(String msg) {
        writer.println(msg);
        chatLogger.log(Level.INFO, this.getName() + "client received: " + msg);
    }
}
}
