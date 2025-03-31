import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
public class App{
    public static void main(String args[]){
        BufferedReader personalReader, completedReader, businessReader, urgentReader;
        File completedFile = new File("completed.txt");
        File personalFile = new File("personal.txt");
        File businessFile = new File("business.txt");
        File urgentFile = new File("urgent.txt");
        if(completedFile.exists() && personalFile.exists() && businessFile.exists() && urgentFile.exists())
        {
            System.out.println("Loading data...");
            try
            {
                personalReader = new BufferedReader(new FileReader("personal.txt"));
                completedReader = new BufferedReader(new FileReader("completed.txt"));
                businessReader = new BufferedReader(new FileReader("business.txt"));
                urgentReader = new BufferedReader(new FileReader("urgent.txt"));
                
                                
            }catch(IOException e){
                System.out.println("An error occurred while creating the file.");
                e.printStackTrace();
                System.exit(-1);
            }
        }
        else{
            try
            {
                if(completedFile.createNewFile() && personalFile.createNewFile() && businessFile.createNewFile() && urgentFile.createNewFile())
                {
                    System.out.println("Creating data files...");
                }
            }catch(IOException e){
                System.out.println("An error occurred while creating the file.");
                e.printStackTrace();
                System.exit(-1);
            }
        }
        Scanner sc = new Scanner(System.in);
        
        System.out.println(
    "==============================\n" +
    "||     TO DO LIST APP      ||\n" +
    "||        Welcome!         ||\n" +
    "==============================\n"
    );
    int choice = 0;
    while(choice != 5)
        {
            System.out.println(
            "==============================\n" +
            "|| 1. Add Task             ||\n" +
            "|| 2. View Tasks           ||\n" +
            "|| 3. Delete Task          ||\n" +
            "|| 4. Mark as Completed    ||\n" +
            "|| 5. Exit                 ||\n" +
            "==============================");
            System.out.println("Enter your choice: ");
            choice = sc.nextInt();
            switch(choice){
                case 1:  Data.foo(); break;
                case 2: break;
                case 3: break;
                case 4: break;
                case 5: break;
                default: System.out.println("Invalid choice. Try again."); break;
            }
        }
    }
}