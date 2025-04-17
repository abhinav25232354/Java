public class Logical_Operators {
    public static void main(String[] args){
        // AND Operator
        int a = 10;
        int b = 10;

        if (a==10 && b==10){
            System.out.println("Both Statements are true");
            System.out.println("If Statement");
        }
        else{
            System.out.println("Both statements are not true");
            System.out.println("Else statement");
        }

        // OR Operator
        int x = 10;
        int y = 20;
        int n = 50;

        if (x<n || y<n){
            System.out.println("X is less than n and also Y");
        }
        else{
            System.out.println("X is greater than n and also Y");
        }
    }
}
