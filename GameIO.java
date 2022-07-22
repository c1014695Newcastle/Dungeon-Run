import java.util.*;

public class GameIO {
    /**
     * Method to take the player's name at the start of the run for the leaderboard
     * @return the player's input
     */
    public static String playerName(){
        boolean check = true;
        Scanner s = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String name = "";
        while (check){
            name = s.nextLine();
            check = name.isBlank() || name.matches(".*[0-9]+.*");
        }
        System.out.println("WELCOME TO THE DUNGEON " + name.toUpperCase());
        return name.toUpperCase();
    }

    public static String topBorder(int length){
        return "╔═" + "═".repeat(length) + "═╗\n  ";
    }

    public static String bottomBorder(int length){
        return "╚═" + "═".repeat(length) + "═╝\n";
    }

    public static String playerHealthBar(int health, int possibleHealth){
        String healthBar = "|";
        for (int x = 0; x < health/2; x++){
            healthBar += "■";
        }
        for (int x = 0; x < (possibleHealth - health)/2; x++){
            healthBar += "□";
        }
        healthBar += "|";
        return healthBar;
    }

    /**
     * Method to report the player's death upon reaching 0 or less health
     */
    public static void playerDies(){
        System.out.println("\n\nYOU PERISH IN THE DUNGEON - GAME OVER\n");
    }

    /**
     * Method to allow the player to decide which attack they use on their next turn
     * @return the player's choice of attack
     */
    public static String playerChoice(){
        System.out.print("""
                Pick an attack:
                    1 - Cast
                    2 - Chop
                    3 - Swing
                    
                > """);
        return choiceIO(3);
    }

    /**
     * Method to allow the player to choose which enemy they damage on their turn
     * @param damage the damage that attack will do to the selected enemy
     * @param enemies the list of enemies in the room.
     * @return the enemy the player wishes to damage
     */
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

    /**
     * Standard method to allow the user to pick from a list of set choices
     * @param numOfChoices the number of options the player has to choose from
     * @return the number of the choice the player has picked
     */
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

    /**
     * Vallidation method to check if the user input falls within acceptable parameters, such as not containing any characters that are not numbers or goes beyond the limit of the choice scope.
     * @param choice the player's choice input
     * @param numOfChoices the number of choices
     * @return true or false depending on whether the player input is acceptable or not, if true then the player will be asked to re-enter their choice.
     */
    private static boolean checkChoice(String choice, int numOfChoices){
        if (choice.matches(".*[a-zA-Z!@#$%^&*)(+=._'-]+.*") | choice.isEmpty()) {
            return true;
        } else return Integer.parseInt(choice) > numOfChoices;
    }

    /**
     * Method to report the attack against the player
     * @param enemy the name of the enemy
     * @param attack the attack they use
     */
    public static void reportAttack(String enemy, String attack){
        System.out.println("\n" + enemy + " uses " + attack + "!\n");
    }

    public static void enemyDies(String enemyName, int xp){
        System.out.println("\nYOU KILL THE " + enemyName + "!\n GAIN " + xp + " XP!\n");
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
    
    public static void reportLevelUp(int newLevel, int diff){
        System.out.println("\nYOU GAINED " + diff + " LEVELS\n");
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

    public static Room pickRoom(Room[] rooms){
        System.out.println("Pick a room to continue");
        int x = 0;
        if (rooms[0] != null){
            System.out.println("\t " + (x+1) + " - Right" + rooms[0]);
            x++;
        }
        if (rooms[1] != null){
            System.out.println("\t" + (x+1) + " - Left" + rooms[1]);
            rooms[x] = rooms[1];
            x++;
        }
        if (rooms[2] != null){
            System.out.println("\t" + (x+1) + " - Up" + rooms[2]);
            rooms[x] = rooms[2];
            x++;
        }
        if (rooms[3] != null){
            System.out.println("\t" + (x+1) + " - Down" + rooms[3]);
            rooms[x] = rooms[3];
            x++;
        }
        Scanner s = new Scanner(System.in);
        String choice = GameIO.choiceIO((x+1));
        return rooms[Integer.parseInt(choice)-1];
    }

    public static void main(String[] args) {
        reportLevelUp(3,2);
    }
}
