import java.sql.Time;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class GameIO {

    public static String playerChoice(){
        boolean check = true;
        System.out.print("""
                Pick an attack:
                    1 - Cast
                    2 - Chop
                    3 - Swing
                    
                > """);
        Scanner s = new Scanner(System.in);
        String choice = "";
        while (check){
            System.out.print("Postcode: ");
            choice = s.nextLine();
            check = checkChoice(choice);
            if (check) {
                System.out.println("INVALID - TRY AGAIN");
            } else {
                System.out.println("Accepted");
            }
        }
        return choice;
    }

    private static boolean checkChoice(String choice){
        return (choice.matches(".*[a-zA-Z!@#$%^&*)(+=._-]+.*") | choice.isEmpty()); // (5)
    }

    public static int playerRoll(boolean poisoned, boolean necrosis, boolean burned ){
        System.out.print("Press enter to roll the dice");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        Random rn = new Random();
        int rollOne = rn.nextInt(10) + 1;
        int rollTwo = rn.nextInt(10) + 1;

        System.out.format("""
                
                You Rolled:
                
                ╔═══════════════╗ ╔═══════════════╗
                        %d                %d
                ╚═══════════════╝ ╚═══════════════╝ 
       
                 """, rollOne,rollTwo);
        scanner.close();
        // Check for conditions to affect dice roll;
        if (poisoned){
            System.out.println("POISON DEBUFF - TAKE LOWER ROLL");
            if (rollOne < rollTwo){
                return rollOne;
            } else {
                return rollTwo;
            }
        }
        return rollOne;
    }

    public static void main(String[] args) {
        playerRoll(true, false, false);
    }
}
