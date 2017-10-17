
/**
 * Simulate a student
 *
 * @author
 */
public class Student {

    private long time;
    private String name;
    private Question[] questions;
    private String[] studentsAnswers;

    /**
     * Initialize Student data members
     *
     * @param time long
     * @param name String
     * @param questions Question[]
     * @param studentsAnswers String[]
     */
    public Student(long time, String name, Question[] questions, String[] studentsAnswers) {
        this.time = time;
        this.name = name;
        this.questions = questions;
        this.studentsAnswers = studentsAnswers;
    }

    /**
     * Get student answers
     *
     * @return String[]
     */
    public String[] getStudentsAnswers() {
        return studentsAnswers;
    }

    /**
     * Get student name
     *
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Get questions
     *
     * @return Question[]
     */
    public Question[] getQuestions() {
        return questions;
    }

    /**
     * Get exam time
     *
     * @return long
     */
    public long getTime() {
        return time;
    }

    /**
     * Return Summary of student
     *
     * @return String
     */
    @Override
    public String toString() {
        //write question/student answer/correct answer
        String questionsOutput = "Questions:\n";
        int score = 0;
        for (int i = 0; i < questions.length; i++) {
            questionsOutput += questions[i].getQuestion() + "\nYour Answer: "
                    + studentsAnswers[i] + ", Correct Answer: "
                    + questions[i].getAnswer() + "\n";
            if (studentsAnswers[i].equals(questions[i].getAnswer()))
                score++;
        }
        //write time is minute/second
        long min = time / 1000 / 60;
        long sec = time / 1000 % 60;
        String timeS = min + " minutes and " + sec + " seconds";
        //return result
        return String.format("Student Name: %s\n%sScore: %d/10\nTime: %s",
                name, questionsOutput, score, timeS);
    }

}
