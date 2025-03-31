import java.util.Scanner;
public class App{
    public static void main(String args[]){
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