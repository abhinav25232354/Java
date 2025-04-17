import java.util.Random;
import java.util.Scanner;

public class Random_Class_Rock_paper_Scissor {
    public static void main(String[] args) {
        Random random_number = new Random();
        Scanner sc = new Scanner(System.in);

        int number = random_number.nextInt(10); // Generated Number
        System.out.println("Game Starts"); // User Input
        System.out.println("5 Chances"); // User Input
        System.out.println("Note : You have to choose higher number than CPU to win"); // User Input
        System.out.println("Note : Decimals not allowed"); // User Input
        int CPU = 0;
        int User = 0;
        for (int i = 0; i <= 10; i++) {
            System.out.print("Enter a Number : "); // User Input
            int user_input = sc.nextInt(); // Stored Input
            if (number == user_input) {
                System.out.println("Match Draw || ");
            } else if (number > user_input) {
                System.out.println("CPU Won");
                CPU++;
            } else if (number < user_input) {
                System.out.println("You Won");
                User++;
            }
            
        }
        System.out.printf("\nCPU Scored %d", CPU);
        System.out.printf("You Scored %d", User);
    }
}