
import java.util.Scanner;

public class TriviaMain {
    public static void main(String[] args) {
        menu();
    }

    public static void menu(){
        System.out.print(
            "1) New Game\n" +
            "2) Load Game\n" +
            "3) Add Trivia to Database\n" +
            "4) Quit\n"
        );

        int menuInput = 0;
        Scanner kb = new Scanner(System.in);
        
        do {
            try{
                menuInput = Integer.parseInt(kb.nextLine());

                if(menuInput == 1){
                    newGame();
                }
                else if(menuInput == 2){
                    loadGame();
                }
                else if(menuInput == 3){
                    addTrivia();
                }
                else if(menuInput == 4){
                    System.out.println("Goodbye!");
                }
                else{
                    System.out.println("Invalid input: must be between 1 and 4");
                }
            }catch(NumberFormatException e){
                System.out.println("Invalid input: must be an integer.");
            }

        } while (menuInput < 1 || menuInput > 4);
        kb.close();
    }

    public static void newGame() {
        TriviaGame game = new TriviaGame(new Maze(4));
        game.playGame();
    }

    public static void loadGame() {
        //deserialize selected maze to load
        //pass maze to TriviaGame constructer
        //game.playGame();
    }

    public static void addTrivia() {
        //option to return to main menu
        //grab user question and answers
        //store in database
    }

}
