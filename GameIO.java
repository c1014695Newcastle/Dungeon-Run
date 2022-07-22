import Enums.FireEnemies;

import java.util.*;

public class GameIO {

    public static void playerDies(){
        System.out.println("\n\nYOU PERISH IN THE DUNGEON - GAME OVER\n");
    }

    public static String playerChoice(){
        boolean check = true;
        System.out.print("""
                Pick an attack:
                    1 - Cast
                    2 - Chop
                    3 - Swing
                    
                > """);
        return choiceIO(3);
    }

    public static Enemy damageChoice(int damage, ArrayList<Enemy> enemies){
        System.out.println("Select an enemy to do " + damage + " damage to:");
        for (int x = 0; x < enemies.size(); x ++) {
            System.out.println("\t" + (x + 1) + " " + enemies.get(x).getName() + " - " + enemies.get(x).getHealth());
        }
        String choice = choiceIO(enemies.size());
        for (int x = 0; x < enemies.size(); x++){
            if (x == Integer.parseInt(choice) - 1){
                System.out.println("\nYou do " + damage + " to the " + enemies.get(x).getName() + "\n");
                return enemies.get(x);
            }
        }
        return null;
    }

    public static Enemy chopChoice(int damage, ArrayList<Enemy> enemies){
        System.out.println("Select an enemy to do " + damage + " damage to:");
        for (int x = 0; x < enemies.size(); x ++) {
            System.out.println("\t" + (x + 1) + " " + enemies.get(x).getName() + " - " + enemies.get(x).getHealth());
        }
        String choice = choiceIO(enemies.size());
        for (int x = 0; x < enemies.size(); x++){
            if (x == Integer.parseInt(choice) - 1){
                System.out.println("\nYou do " + damage + " to the " + enemies.get(x).getName() + "\n");
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

    public static void reportAttack(String enemy, String attack){
        System.out.println("\n" + enemy + " uses " + attack + "!\n");
    }

    public static void enemyDies(String enemyName, int xp){
        System.out.println("\nYOU KILL THE " + enemyName.toString() + "!\n GAIN " + xp + " XP!\n");
    }

    public static void damageReport(int damage, boolean poisoned){
        if (!poisoned) {
            System.out.println("YOU TAKE " + damage + "!\n\n");
        } else {
            System.out.println("YOU TAKE " + damage + " AND ARE POISONED!");
        }
    }

    public static void sleep(){
        try
        {
            Thread.sleep(1000);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }
    
    private static void reportLevelUp(int newLevel, int diff){
        System.out.println("YOU GAINED " + diff + " LEVELS\n");
        sleep();
        System.out.println("\nYOU ARE NOW LEVEL " + newLevel);
        System.out.println("\tHEALTH +" + (newLevel * 10));
        System.out.println("\tCAST DAMAGE +" + (newLevel * 5) + "\n\tCHOP DAMAGE +" + (newLevel * 5) + "\n\tSWING DAMAGE +" + (newLevel * 5) + "\n");
    }

    public static int playerRoll(boolean poisoned, boolean burning, boolean necrosis){
        System.out.print("Press enter to roll the dice");
        Scanner s = new Scanner(System.in);
        String place = s.nextLine();
        Random rn = new Random();
        int rollOne = rn.nextInt(10) + 1;
        int rollTwo = rn.nextInt(10) + 1;
        sleep();
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


    public static void reportClearing(){
        System.out.println("\nYou feel your strength returning to you - DEBUFFS CLEARED\n");
    }

    public static void main(String[] args) {
        reportLevelUp(3,2);
    }
}
