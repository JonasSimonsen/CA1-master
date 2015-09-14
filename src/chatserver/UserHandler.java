package chatserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import shared.ProtocolStrings;

public class UserHandler extends Thread {

    private final Socket socket;
    private final Scanner input;
    private final PrintWriter writer;
    private final ChatServer CS;
    private String userName = "";
    private ArrayList<String> userList;

    //Declare possible states for client
    //Start the state of the client as connecting. We instiated the object
    public UserHandler(Socket socket, ChatServer CS) throws IOException {
        this.socket = socket;
        this.CS = CS;
        input = new Scanner(socket.getInputStream());
        writer = new PrintWriter(socket.getOutputStream(), true);
    }
    
    public void send(String message) {
        writer.println(message);
        Logger.getLogger(ChatServer.class.getName()).log(Level.INFO, String.format("Received the message: %1$S ", message));
    }
    
        public String getUserName() {
        return userName;
    }

    @Override
    public void run() {
        try {
            String message = input.nextLine(); //IMPORTANT blocking call
            Logger.getLogger(ChatServer.class.getName()).log(Level.INFO, String.format("Received the message: %1$S ", message));
            while (!message.equals(ProtocolStrings.STOP)) {
                String[] msgArray = message.split("#");
                if (msgArray[0].equals("USER")) {
                    userName = msgArray[1];
                    CS.sendUserList();
                    CS.userConnected(userName);
                }
                
                if(!"".equals(this.userName))
                if (msgArray[0].equals("MSG")) {
                    CS.send(msgArray[1], ProtocolStrings.MSGtoUser(userName, msgArray[2]));
                }
                
                if(msgArray[0].equals("USERLIST")){  
                    CS.sendUserList();   
                }

                try {
                    message = input.nextLine(); //IMPORTANT blocking call
                } catch (NoSuchElementException e) {    
                }
            }
            
            writer.println(ProtocolStrings.STOP);
            socket.close();
            CS.removeHandler(this);
            Logger.getLogger(ChatServer.class.getName()).log(Level.INFO, "Closed connection");
        } catch (IOException ex) {
            Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }     
}