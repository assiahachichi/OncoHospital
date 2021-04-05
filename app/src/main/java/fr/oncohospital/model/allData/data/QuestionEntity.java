package fr.oncohospital.model.allData.data;

/**
 * Created by Assia HACHICHI on 24/03/2021
 * @author Assia HACHICHI
 */

/**
 * This class represents a question about frequent side effects, and its answer.
 */
public class QuestionEntity {

    private String question;
    private String answer;

    /**
     * This constructor initialises the instances variables.
     * @param question represents a question abount a frequent side effect.
     * @param answer represents an answer to this given question.
     */
    public QuestionEntity(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    /**
     * This method is a getter, which returns a given question
     * @return represents a given question.
     */
    public String getQuestion() {
        return question;
    }

    /**
     * This method is a getter, which returns an answer
     * @return represents an answer.
     */
    public String getAnswer() {
        return answer;
    }

}
