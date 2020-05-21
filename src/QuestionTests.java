
import static org.junit.Assert.*;
import org.junit.Test;

public class QuestionTests {
    
    @Test
    public void questionCheckAnswerTrue() {
        Question question = new Question("Test question", "Test answer");
        assertTrue(question.checkAnswer("Test answer"));
    }

    @Test
    public void questionCheckAnswerFalse() {
        Question question = new Question("Test question", "Test answer");
        assertFalse(question.checkAnswer("Wrong answer"));
    }

}
