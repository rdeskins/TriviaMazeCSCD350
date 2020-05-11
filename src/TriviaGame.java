
import java.util.Scanner;
public class TriviaGame {
    public static void main(String[] args) {

        menu();
        
    }

    public static void menu(){
		System.out.print(
			"1) New Game\n" +
			"2) Load Game\n" +
			"3) Add Trivia to Database\n" +
			"4) Quit"
		);

        int menuInput;
        Scanner kb = new Scanner(System.in);
		
		do {
			menuInput = kb.nextInt();

			if(menuInput == 1){
				//new game method
			}
			else if(menuInput == 2){
				//load game method
			}
			else if(menuInput == 3){
				//add trivia method
			}
			else if(menuInput == 4){
				//quit
			}
			else{
				System.out.print("Invalid Input");
			}

		} while (menuInput <1 || menuInput > 4);
		kb.close();
		
	}

}