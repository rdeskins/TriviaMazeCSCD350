/*
* Name: Chris Dobbins
* Description: Question class that contains a question and answer
*/

package TriviaGame;

public class Question {
    private String question, answer;
    
    public Question(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return this.question;
    }

    public String getAnswer() {
        return this.answer;
    }

}
