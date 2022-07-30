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

    public static void burningDamage(){
        System.out.println("YOU ARE BURNING - YOU TAKE 5 DAMAGE");
    }

    public static String topBorder(int length){
        return "╔═" + "═".repeat(length) + "═╗\n  ";
    }

    public static String bottomBorder(int length){
        return "╚═" + "═".repeat(length) + "═╝\n";
    }

    public static String playerHealthBar(int health, int possibleHealth){
        StringBuilder healthBar = new StringBuilder("|");
        for (int x = 0; x < health/2; x++){
            healthBar.append("■");
        }
        for (int x = 0; x < (possibleHealth - health)/2; x++){
            healthBar.append("□");
        }
        healthBar.append("|");
        return healthBar.toString();
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
            System.out.println("\t" + (x + 1) + " " + enemies.get(x).getName().replace("_"," ") + " - " + enemies.get(x).getHealth());
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

    public static void castReport(String name, int damage){
        System.out.println("\nYou do " + damage + " to " + name + "\n");
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
     * Validation method to check if the user input falls within acceptable parameters, such as not containing any characters that are not numbers or goes beyond the limit of the choice scope.
     * @param choice the player's choice input
     * @param numOfChoices the number of choices
     * @return true or false depending on whether the player input is acceptable or not, if true then the player will be asked to re-enter their choice.
     */
    private static boolean checkChoice(String choice, int numOfChoices){
        if (choice.matches(".*[a-zA-Z!@#$%^&*)(+=._'`-]+.*") | choice.isEmpty()) {
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

    public static void swingReport(String enemy, int damage){
        System.out.println("\n You swing against " + enemy + " and cause " + damage + " damage!\n");
    }

    public static void chopReport(String enemy, int damage){
        System.out.println("\n You raise your sword and strike " + enemy + " and cause " + damage + " damage!\n");
    }


    public static void enemyDies(String enemyName, int xp){
        System.out.println("\nYOU KILL THE " + enemyName + "!\n GAIN " + xp + " XP!\n");
    }

    /**
     * Method to report the start of the Jormungandr encounter
     * @param name the boss name.
     */
    public static void reportEncounterStart(String name){
        System.out.println("\nYou enter a large cavernous room, seemingly empty....\n");
        sleep(1000);
        System.out.println("\nYou hear a slinking on the far side of the cavern\n");
        sleep(1000);
        System.out.format("\n%s approaches\n", name);
    }

    /**
     * Method to report any damage done against the player and if it triggered any debuffs
     * @param damage the damage against the player
     * @param poisoned if the player has been poisoned by the attack
     * @param burning if the player has been set on fire by the attack
     * @param necrosis if the player has turned necrotic as a result of the last attack
     */
    public static void damageReport(int damage, boolean poisoned, boolean burning, boolean necrosis){
        if (!poisoned) {
            System.out.println("YOU TAKE " + damage + "!\n\n");
        } else if (burning){
            System.out.println("YOU TAKE " + damage + " AND ARE ON FIRE!");
        } else if (necrosis) {
            System.out.println("YOU TAKE " + damage + " AND ARE NECROTIC!");
        }else {
            System.out.println("YOU TAKE " + damage + " AND ARE POISONED!");
        }
    }

    /**\
     * Method to record the missed attack of a player against a boss
     * @param name the boss' name
     */
    public static void recordMiss(String name){
        if (name.equals("Fafnir")){
            System.out.println("Fafnir takes flight and dodges your attack, you miss");
        } else if (name.equals("Fenrir")){
            System.out.println("Fenrir jumps out of the way of your attack, you miss");
        } else {
            System.out.println("Jormungandr slithers his way out of the path of your attack, you miss");
        }
    }


    public static void sleep(int time){
        try
        {
            Thread.sleep(time);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Method to report the player levelling up after an encounter, tells them how much their stats have increased by
     * @param newLevel the player's new level
     * @param diff how many levels above their old level the player is now
     */
    public static void reportLevelUp(int newLevel, int diff){
        System.out.println("\nYOU GAINED " + diff + " LEVELS\n");
        sleep(1000);
        System.out.println("\nYOU ARE NOW LEVEL " + newLevel);
        System.out.println("\tHEALTH +" + (newLevel * 10));
        System.out.println("\tCAST DAMAGE +" + (newLevel * 2) + "\n\tCHOP DAMAGE +" + (newLevel * 2) + "\n\tSWING DAMAGE +" + (newLevel * 2) + "\n");
    }

    /**
     * Method to allow the player to roll a dice to determine their attack damage, player rolls two dice and will always take the first unless disadvantage debuffs are in play, which means the player must take the lower of the two rolls
     * @param poisoned the player's poison debuff - true means player takes lesser roll
     * @param burning the player's burning debuff - true means player takes lesser roll
     * @param necrosis the player's necrosis debuff - true means player takes lesser roll
     * @return the player's dice roll
     */
    public static int playerRoll(boolean poisoned, boolean burning, boolean necrosis){
        System.out.print("Press enter to roll the dice");
        Scanner s = new Scanner(System.in);
        String place = s.nextLine();
        Random rn = new Random();
        int rollOne = rn.nextInt(10) + 1;
        int rollTwo = rn.nextInt(10) + 1;
        sleep(1000);
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

    /**
     * Method to allow the player to pick a valid room to enter from the list of possible valid rooms
     * @param rooms the valid rooms a player has to pick from
     * @return the player's room choice
     */
    public static Room pickRoom(Room[] rooms){
        System.out.println("Pick a room to continue");
        int x = 0;
        if (rooms[0] != null){
            System.out.println("\t" + (x+1) + " - Down " + rooms[0]);
            x++;
        }
        if (rooms[1] != null){
            System.out.println("\t" + (x+1) + " - Up " + rooms[1]);
            rooms[x] = rooms[1];
            x++;
        }
        if (rooms[2] != null){
            System.out.println("\t" + (x+1) + " - Right " + rooms[2]);
            rooms[x] = rooms[2];
            x++;
        }
        if (rooms[3] != null){
            System.out.println("\t" + (x+1) + " - Left " + rooms[3]);
            rooms[x] = rooms[3];
            x++;
        }
        Scanner s = new Scanner(System.in);
        String choice = GameIO.choiceIO((x+1));
        return rooms[Integer.parseInt(choice)-1];
    }

    public static void fafVulnerable(){
        System.out.println("Fafnir returns to the fighting plane, he is vulnerable again!");
    }

    public static void fafPhaseBattle(){
        System.out.println("Fafnir drags his claws through the stone and summons Draugr to fight you!");
    }

    public static void fafClaws(){
        System.out.println("Fafnir digs his claws into the stone and raises them to you!");
    }

    public static void fafFlight(){
        System.out.println("Fafnir spreads his wings and takes flight, he will be much harder to hit now!");
    }

    public static void fenVulnerable(){
        System.out.println("Fafnir returns to the fighting plane, he is vulnerable again!");
    }

    public static void fenPhaseBattle(){
        System.out.println("Fafnir drags his claws through the stone and summons Draugr to fight you!");
    }

    public static void fenClaws(){
        System.out.println("Fafnir digs his claws into the stone and raises them to you!");
    }

    public static void fenSnarl(){
        System.out.println("Fafnir spreads his wings and takes flight, he will be much harder to hit now!");
    }

    public static void jorVulnerable(){
        System.out.println("Fafnir returns to the fighting plane, he is vulnerable again!");
    }

    public static void jorPhaseBattle(){
        System.out.println("Fafnir drags his claws through the stone and summons Draugr to fight you!");
    }

    public static void bossLine(String s){
        System.out.format("\n\033[3;1m%s\033[0m\n",s);
    }

    public static void reportSupportEnemies(String name){
        System.out.println((name + " falls back and summons Draugr to fight you!").toUpperCase());

    }

    public static void reportStageClear(){
        System.out.println("You feel your strength begin to return\n\t+50 Health");
        System.out.println("FAFNIR IS VULNERABLE AGAIN");
    }

    public static void main(String[] args) {
        reportLevelUp(3,2);
    }
}
