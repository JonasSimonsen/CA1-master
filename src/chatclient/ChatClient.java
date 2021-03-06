package chatclient;

import chatserver.ChatServer;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import shared.ProtocolStrings;

public class ChatClient extends Observable implements Runnable {

    private Socket socket;
    private int port;
    private InetAddress serverAddress;
    private Scanner input;
    private PrintWriter output;
    private String userName;
    private ArrayList<String> userList;
    private ChatServer CS;
    
    public static void main(String[] args) {

        int port = 9090;
        String ip = "VMAHL.cloudapp.net";
        if (2 == args.length) {
            port = Integer.parseInt(args[0]);
            ip = args[1];
        }
        try {
            ChatClient tester = new ChatClient();
            Observer Obs = new Observer() {

                @Override
                public void update(Observable o, Object arg) {
                    System.out.println("Received: " + arg); //Important Blocking call
                }
            };
            tester.addObserver(Obs);
            tester.connect(ip, port);
            System.out.println("Sending 'Hello world'");
            tester.send("Hello World");
            System.out.println("Waiting for a reply");
            tester.stop();

        } catch (UnknownHostException ex) {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run() {
        while (true) {

            String msg = input.nextLine();
            String[] msgArray;
            msgArray = msg.split("#");
            if (msgArray[0].equals("USERLIST")) {
                userList.clear();
                userList.addAll(Arrays.asList(msgArray[1].split(",")));
            }
            //CS.sendUserList();
            setChanged();
            notifyObservers(msg);
            if (msg.equals(ProtocolStrings.STOP)) {
                try {
                    socket.close();
                    break;
                } catch (IOException ex) {
                    Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public ChatClient(String userName) {
        this.userName = userName;
        userList = new ArrayList<>();
    }

    public ChatClient() {
        Random rand = new Random();
        userName = "client " + rand.nextInt(1000);
        userList = new ArrayList<>();
    }

    public String getUserName() {
        return userName;
    }

    public Socket getSocket() {
        return socket;
    }

    public void register() {
        output.println(ProtocolStrings.connectUser(userName));
    }

    public void connect(String address, int port) throws UnknownHostException, IOException {
        this.port = port;
        serverAddress = InetAddress.getByName(address);
        socket = new Socket(serverAddress, port);
        input = new Scanner(socket.getInputStream());
        output = new PrintWriter(socket.getOutputStream(), true);  
        System.out.println("Connect started");
        register();
        new Thread(this).start();
    }

    public void send(String receivers, String msg) {
        output.println(ProtocolStrings.MSGfromUser(receivers + "," + userName, msg));
    }

    public void send(String msg) {
        output.println(ProtocolStrings.MSGfromUser("*", msg));
    }

    public void stop() throws IOException {
        output.println(ProtocolStrings.STOP);
    }
}
