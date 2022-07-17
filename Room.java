import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Room {
    private ArrayList<Enemy> enemies;
    private int difficulty;
    private String type;


    public Room(int difficulty, String type) {
        this.difficulty = difficulty;
        this.type = type;
        this.enemies = generateEnemies(3, type, difficulty);
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    /**
     * Generates the enemies for the room encounter based on the set difficulty of the room, the number of enemies and the type of encounter this is
     * @param numOfEnemies the number of enemies which should spawn in this encounter
     * @param type the type of encounter, whether it be flame, poison or necrotic
     * @param difficulty the difficulty of this room as a set number, 1 - easy, 2 - med, 3 - hard
     * @return the list of enemies for this encounter.
     */
    private ArrayList<Enemy> generateEnemies(int numOfEnemies, String type, int difficulty) {
        Random rn = new Random();
        ArrayList<Enemy> enemies = new ArrayList<>();
        for (int x = 0; x < numOfEnemies; x++) {
            if (type.equals("Flame")) {
                String name = Enemy.fireTypes[rn.nextInt(Enemy.fireTypes.length)];
                int level = rn.nextInt(5) + 1;
                if (name.equals("Draugr")) {
                    String[] attacks = {"Chop", "Swing", "Bash"};
                    Enemy enemy = new Enemy(x + 1, level, 15 + level * 10, 15 +level * 10, level * 5, name, attacks, level * 10);
                    enemies.add(enemy);
                } else if (name.equals("Fire Demon")){
                    String[] attacks = {"Spit", "Bite", "Blind"};
                    Enemy enemy = new Enemy(x + 1, level, 15 + level * 5, 15 + level * 5, level * 10, name, attacks, level * 10);
                    enemies.add(enemy);
                } else {
                    String[] attacks = {"Smoke", "Crush", "Burn"};
                    Enemy enemy = new Enemy(x + 1, level, 15 + level * 10, 15 + level * 10, level * 10, name, attacks, level * 10);
                    enemies.add(enemy);
                }
            }
        }
        return enemies;
    }

    /**
     * Method to start and run the encounter in the room with the player, runs until the list of enemies is empty
     * @param p the player character
     */
    public void startEncounter(Player p){
        String choice;
        int damage;
        while (!enemies.isEmpty() && p.getHealth() > 0){
            System.out.println(p);
            for (Enemy e : enemies){
                System.out.println(e);
            }
            if ((p.isPoisoned() || p.isBurning() || p.isNecrosis()) && p.getDebuffCounter() == 0){
                p.removeDebuffs();
                GameIO.reportClearing();
            }
            GameIO.sleep();
            playerTurn(p);
            GameIO.sleep();
            enemyTurns(p);
            GameIO.sleep();
            if (p.getHealth() <= 0){
                GameIO.playerDies();
            }
        }
        System.out.println("ENCOUNTER CLEARED");

        //endEncounter(p);
    }

    private void enemyTurns(Player p){
        int playerDamage = 0;
        for (Enemy e : enemies){
            if (e.getName().equals("Draugr")){
                playerDamage = e.draugrAttacks(p);
            } else  if (e.getName().equals("Fire Demon")){
                playerDamage = e.fireDemonAttacks();
            } else {
                playerDamage = e.smokestackAttacks();
            }
            GameIO.damageReport(playerDamage, p.isPoisoned());
            p.setHealth(p.getHealth() - playerDamage);
        }
    }

    private void playerTurn(Player p){
        String choice;
        int damage;
        choice = GameIO.playerChoice();
        if (choice.equals("1")){ // Cast
            int[] castDamage = p.cast();
            for (int x : castDamage){
                Enemy enemytoDamage = GameIO.castChoice(x, getEnemies());
                assert enemytoDamage != null;
                enemytoDamage.setHealth(enemytoDamage.getHealth() - x);
                if (enemytoDamage.getHealth() <= 0){
                    p.setXp(p.getXp() + enemytoDamage.getXp());
                    GameIO.enemyDies(enemytoDamage.getName(), enemytoDamage.getXp());
                    this.getEnemies().remove(enemytoDamage);
                }
            }
        } else if (choice.equals("2")){ // Chop
            damage = p.chop();

        } else { // Swing
            damage = p.swing();
        }
    }

    private String translateDifficulty(int difficulty){
        String diff = "";
        if (difficulty == 0){
            diff = "Empty";
        } else if (difficulty == 1){
            diff = "Easy ";
        } else if (difficulty == 2) {
            diff = "Med  ";
        } else if (difficulty == 3){
            diff = "Hard ";
        } else {
            diff = "BLOCK";
        }
        return diff;
    }
    public String toString(){
        return "[ " + type + " " + translateDifficulty(difficulty) + " ]";
    }


}
