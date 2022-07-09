import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;

public class GameIO {

    public static String playerChoice(){
        boolean check = true;
        System.out.print("""
                Pick an attack:
                    1 - Cast
                    2 - Chop
                    3 - Swing
                    
                > """);
        String choice = choiceIO(3);
        return choice;
    }

    public static Enemy castChoice(int damage, ArrayList<Enemy> enemies){
        System.out.println("Select an enemy to do " + damage + " damage to:");
        for (int x = 0; x < enemies.size(); x ++) {
            System.out.println((x + 1) + " " + enemies.get(x).getName() + " - " + enemies.get(x).getHealth());
        }
        String choice = choiceIO(enemies.size());
        for (int x = 0; x < enemies.size(); x++){
            if (x == Integer.parseInt(choice) - 1){
                System.out.println("You do " + damage + " to the " + enemies.get(x).getName());
                return enemies.get(x);
            }
        }
        return null;
    }

    private static String choiceIO(int numOfChoices){
        boolean check = true;
        Scanner s = new Scanner(System.in);
        String choice = "";
        while (check){
            System.out.print("> ");
            choice = s.nextLine();
            check = checkChoice(choice, numOfChoices);
            if (check) {
                System.out.println("INVALID - TRY AGAIN");
            } else {
                System.out.println("Accepted");
            }
        }
        return choice;
    }

    private static boolean checkChoice(String choice, int numOfChoices){
        if (choice.matches(".*[a-zA-Z!@#$%^&*)(+=._-]+.*") | choice.isEmpty()) {
            return true;
        } else return Integer.parseInt(choice) > numOfChoices;
    }



    public static int playerRoll(boolean poisoned, boolean necrosis, boolean burned ){
        System.out.print("Press enter to roll the dice");
        Scanner s = new Scanner(System.in);
        String place = s.nextLine();
        Random rn = new Random();
        int rollOne = rn.nextInt(10) + 1;
        int rollTwo = rn.nextInt(10) + 1;

        System.out.format("""
                
                You Rolled:
                
                ╔═══════════════╗ ╔═══════════════╗
                        %d                %d
                ╚═══════════════╝ ╚═══════════════╝ 
       
                 """, rollOne,rollTwo);
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
