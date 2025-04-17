import java.util.Scanner;
public class Switch_Case {
    public static void main (String[] args){
        Scanner sc = new Scanner (System.in);
        // Switch Case
        for (;;){
        System.out.print("Enter Your Name : ");
        String name = sc.next();

        switch (name){
            case "Abhinav":
                System.out.println("You are Abhinav :)");
                break;
            case "Ishaan":
                System.out.println("You are Ishaan");
                break;
            case "Pappu":
                System.out.println("You are Pappu");
                break;
    }
}

    }
}
