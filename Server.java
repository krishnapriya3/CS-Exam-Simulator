
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Server handles questions, ask student random questions from list and
 * calculate exam time
 *
 * @author
 */
public class Server {

    private ServerSocket serverSocket;
    private Socket socket;
    private DataInputStream inputFromClient;
    private DataOutputStream outputToClient;
    private ArrayList<Question> questions;
    private Random random;

    public Server() {
        initServer();
        //read file and get questions
        questions = FileHandler.readQuestions();
        random = new Random();
        try {
            boolean doExam = true;
            while (doExam) {
                Student student = simulateExam();
                //write the summary and send it to student
                FileHandler.writeStudentSummary(student);
                outputToClient.writeUTF(student.toString());
                outputToClient.writeUTF("Summary saved in file " + student.getName() + student.getTime() + ".txt");
                outputToClient.writeUTF("Would you like to solve another exam ? (Y/N)");
                if (inputFromClient.readUTF().equalsIgnoreCase("N")) {
                    doExam = false;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        closeServer();
    }

    /**
     * Initialize and open server
     */
    private void initServer() {
        try {
            // Create a server socket
            serverSocket = new ServerSocket(8000);
            // Listen for a connection request
            socket = serverSocket.accept();
            // Create data input and output streams
            inputFromClient = new DataInputStream(socket.getInputStream());
            outputToClient = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Clear and close server
     */
    private void closeServer() {
        try {
            inputFromClient.close();
            outputToClient.close();
            socket.close();
            serverSocket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Simulate student exam and return student object for summary
     *
     * @return Student
     */
    private Student simulateExam() {
        try {
            //read student name
            outputToClient.writeUTF("Please enter your name: ");
            String name = inputFromClient.readUTF();
            //send message to student and wait until he start the exam
            outputToClient.writeUTF("Please click enter to start the exam");
            inputFromClient.readUTF(); // read enter click
            //create arrays to hold questions and answers
            Question[] studentQuestions = new Question[10];
            String[] answers = new String[10];
            //set exam start time
            long examStartTime = System.currentTimeMillis();
            //get 10 questions from list
            for (int i = 0; i < studentQuestions.length; i++) {
                int questionIdx = random.nextInt(50); // random number from 0-50
                studentQuestions[i] = questions.get(questionIdx); // get question
                //write the question to student
                outputToClient.writeUTF(studentQuestions[i].getQuestion() + " T/F");
                answers[i] = inputFromClient.readUTF();
            }
            //get exam end time
            long examEndTime = System.currentTimeMillis();
            //get exam time
            long examTime = examEndTime - examStartTime;
            //initialize student and return it
            Student student = new Student(examTime, name, studentQuestions, answers);
            return student;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Start server
     *
     * @param args
     */
    public static void main(String[] args) {
        new Server();
    }
}
