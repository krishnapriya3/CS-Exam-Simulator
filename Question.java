
/**
 * Simulate a true/false question
 *
 * @author
 */
public class Question {

    private String question;
    private String answer;

    /**
     * Initialize class fields
     *
     * @param question String
     * @param answer String
     */
    public Question(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    /**
     * Get question
     *
     * @return String
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Get answer
     *
     * @return String
     */
    public String getAnswer() {
        return answer;
    }

}
