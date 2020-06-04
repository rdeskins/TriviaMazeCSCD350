
import java.util.ArrayList;

public interface IDatabase {
    public boolean initialize();
    public boolean insertQuestion(int diffID, int typeID, Question q);
    public boolean deleteAllQuestions();
    public Question getRandomQuestion();
    public Question getRandomQuestion(int difficultyID);
    public int getQuestionTotal();
    public ArrayList<String> getQuestionTypes();
    public ArrayList<String> getDifficulties();
}