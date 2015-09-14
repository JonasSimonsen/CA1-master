package chatserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import shared.ProtocolStrings;
import utils.Utils;

public class ChatServer {

    private static boolean keepRunning = true;
    private static ServerSocket serverSocket;
    private static final Properties properties = Utils.initProperties("server.properties");
    private final ArrayList<UserHandler> users = new ArrayList<>();

    public static void main(String[] args) {
        String logFile = properties.getProperty("logFile");
        Utils.setLogFile(logFile, ChatServer.class.getName());

        new ChatServer().runServer();
        Utils.closeLogger(ChatServer.class.getName());
    }

    public static void stopServer() {
        keepRunning = false;
    }

    public void userConnected(String ClientName) {
        for (UserHandler uh : users) {
            uh.send(ProtocolStrings.MSGtoUser("SERVER", ClientName + " has connected!"));
        }
    }

    public void userDisconnected(String ClientName) {
        for (UserHandler uh : users) {
            uh.send(ProtocolStrings.MSGtoUser("SERVER", ClientName + " has disconnected!"));
        }
    }

    private void runServer() {
        int port = Integer.parseInt(properties.getProperty("port"));
        String ip = properties.getProperty("serverIp");

        Logger.getLogger(ChatServer.class.getName()).log(Level.INFO, "Sever started. Listening on: " + port + ", bound to: " + ip);
        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(ip, port));
            do {
                Socket socket = serverSocket.accept(); //Important Blocking call
                Logger.getLogger(ChatServer.class.getName()).log(Level.INFO, "Client connected");
                UserHandler UH = new UserHandler(socket, this);
                UH.start();
                users.add(UH);
                System.out.println("User added");
//                UH.send("Welcome!"); //Temporary solution to fix telnet invisible first line in CMD
            } while (keepRunning);
        } catch (IOException ex) {
            Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendUserList() {
        String msg = "";
        for (UserHandler uh : users) {
            msg += uh.getUserName() + ",";
        }
        //For at fjerner den sidste komma i userList.
        if (msg.endsWith(",")) {
            msg = msg.substring(0, msg.length() - 1);
        }

        for (UserHandler uh : users) {
            uh.send(ProtocolStrings.userList(msg));
        }
    }

    public void send(String receiveres, String msg) {
        if (receiveres.equals("*")) {
            for (UserHandler uh : users) {
                uh.send(msg);
            }
        } else {
            String[] receiveresArr = receiveres.split(",");
            for (String receiver : receiveresArr) {
                for (UserHandler uh : users) {
                    if (receiver.equals(uh.getUserName())) {
                        uh.send(msg);
                    }
                }
            }
        }
    }

    public void removeHandler(UserHandler uh) {
        users.remove(uh);
        System.out.println("Client removed");
        sendUserList();
    }
}
