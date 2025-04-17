public class Printing_Methods {
    public static void main(String[] args){
        // Print which end with new line
        System.out.println("Hello");
        System.out.println("world");

        // Print with no new line
        System.out.print("Hello");
        System.out.print(" world");

        // Print like c language using FORMAT SPECIFIERS
        String name = "Abhinav";
        int age = 20;
        System.out.printf("Hello my name is %s and my age is %d", name, age);
    }
}
