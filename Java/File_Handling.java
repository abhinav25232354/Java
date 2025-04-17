import java.io.File;
import java.io.FileWriter;

class File_Handling{
    public static void main(String[] args){
        // File Generation
        // File file = new File("File Handling.txt");
        // try{
        //     file.createNewFile();
        // } 
        // catch (Exception e){
        //     System.out.println("Unable to create the file");
        //     e.printStackTrace();
        // }

        // File Writer
        try{
            FileWriter filewriter = new FileWriter("test.txt");
            filewriter.write("This is first ever written file from java by me");
            filewriter.close();
        } catch (Exception e){
            System.out.println("Unable to find File");
            e.printStackTrace();
        }

        // File Reading
    }
}