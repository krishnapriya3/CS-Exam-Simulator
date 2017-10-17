
import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Simulate a client doing exam
 *
 * @author
 */
public class Client {

    private Socket socket;
    private DataInputStream fromServer;
    private DataOutputStream toServer;

    /**
     * Initialize client
     */
    public Client() {
        connect();
        simulateExam();
        closeConnection();
    }

    /**
     * Connect to server and get Input/Output streams
     */
    private void connect() {
        try {
            // Create a socket to connect to the server
            socket = new Socket("localhost", 8000);
            // Create data input and output stream
            fromServer = new DataInputStream(socket.getInputStream());
            toServer = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Close client connection after finishing exam
     */
    private void closeConnection() {
        try {
            fromServer.close();
            toServer.close();
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Simulate exam
     */
    private void simulateExam() {
        Scanner in = new Scanner(System.in);
        try {
            //read server question about name, answer and write it back to server
            System.out.println(fromServer.readUTF());
            String name = in.nextLine();
            toServer.writeUTF(name);
            //read question about starting exam from server and click enter to start
            System.out.println(fromServer.readUTF());
            toServer.writeUTF(in.nextLine());
            //start exam
            String question, answer;
            for (int i = 0; i < 10; i++) {
                //read question and print it
                question = fromServer.readUTF();
                System.out.println(question);
                //get answer and write to server
                answer = in.nextLine();
                toServer.writeUTF(answer);
                System.out.println(""); // do blank line
            }
            //read summary and print it
            String summary = fromServer.readUTF();
            System.out.println("");
            System.out.println("############## Summary ##############");
            System.out.println(summary);
            System.out.println("#####################################");
            System.out.println("");
            System.out.println(fromServer.readUTF());
            System.out.println("");
            //now check if want to start exam again
            System.out.println(fromServer.readUTF());
            //write reply to server
            String again = in.nextLine();
            toServer.writeUTF(again);
            //if want to do exam again, do recursive call for this method
            if (again.equals("Y")) {
                simulateExam();
            }
        } catch (IOException ex) {
            System.out.println("Error: input file not found.");
            System.exit(0);
        }
    }

    /**
     * Start client
     *
     * @param args
     */
    public static void main(String[] args) {
        new Client();
    }

}
