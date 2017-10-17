
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Read questions from file and write each student summary
 *
 * @author
 */
public class FileHandler {

    /**
     * Read questions from file 'questions.txt' and return it
     *
     * @return ArrayList<Question>
     */
    public static ArrayList<Question> readQuestions() {
        ArrayList<Question> questions = new ArrayList<>();
        BufferedReader in;
        try {
            in = new BufferedReader(new FileReader("questions.txt"));
            String question, answer;
            //read question/answer and put in list
            while ((question = in.readLine()) != null) {
                answer = in.readLine();
                questions.add(new Question(question, answer));
            }
            //close file and return questions
            in.close();
            return questions;
        } catch (IOException e) {
            System.out.println("Input file not found");
            System.exit(0);
        }
        return null;
    }

    /**
     * Create file with student name and time to write summary of student
     *
     * @param student String
     */
    public static void writeStudentSummary(Student student) {
        try {
            //create file
            File file = new File(student.getName() + student.getTime() + ".txt");
            file.createNewFile();
            //create writer and write student summary
            PrintWriter out = new PrintWriter(file);
            out.println(student.toString());
            //close file
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
