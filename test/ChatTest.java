import chatserver.ChatServer;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

public class ChatTest {

    public ChatTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ChatServer.main(null);
            }
        }).start();
    }

    @AfterClass
    public static void tearDownClass() {
        ChatServer.stopServer();
    }

    @Test
    public void testUSERProtocol() throws Exception {
        String address = "localhost";
        int port = 9090;

        Socket s = new Socket(address, port);
        Scanner scanner = new Scanner(s.getInputStream());
        PrintWriter printwriter = new PrintWriter(s.getOutputStream(), true);
        printwriter.println("USER#Bo");
        String result = scanner.nextLine();

        assertEquals("USERLIST#Bo", result);
        printwriter.println("STOP#");
        s.close();
    }

    @Test
    public void testMSGProtocol() throws Exception {

        String address = "localhost";
        int port = 9090;

        Socket s = new Socket(address, port);
        Scanner scanner = new Scanner(s.getInputStream());
        PrintWriter printwriter = new PrintWriter(s.getOutputStream(), true);

        printwriter.println("USER#Hans");

        String result = scanner.nextLine();

        result = scanner.nextLine();

        printwriter.println("MSG#Hans#Hej");

        result = scanner.nextLine();

        assertEquals("MSG#Hans#Hej", result);
        printwriter.println("STOP#");
        s.close();
    }
    
//    @Test (expected = IOException.class )
//    public void testSTOPProtocol() throws Exception {
//        String address = "localhost";
//        int port = 9090;
//
//        Socket s = new Socket(address, port);
//        Scanner scanner = new Scanner(s.getInputStream());
//        PrintWriter printwriter = new PrintWriter(s.getOutputStream(), true);
//        
//        printwriter.println("USER#Lise");
//        String result = scanner.nextLine();
//        System.out.println("result");
//        result = scanner.nextLine();
//    }

}
