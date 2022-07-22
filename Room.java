import Enums.FireEnemies;
import Enums.PoisonEnemies;
import Enums.Types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Room {
    private int ID;
    private ArrayList<Enemy> enemies;
    private int difficulty;
    private Types type;


    public Room(int ID, int difficulty, Types type, int numOfEnemies) {
        this.ID = ID;
        this.difficulty = difficulty;
        this.type = type;
        this.enemies = generateEnemies(3, type, difficulty);
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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

    public Types getType() {
        return type;
    }

    public void setType(Types type) {
        this.type = type;
    }


    /**
     * Generates the enemies for the room encounter based on the set difficulty of the room, the number of enemies and the type of encounter this is
     * @param numOfEnemies the number of enemies which should spawn in this encounter
     * @param type the type of encounter, whether it be flame, poison or necrotic
     * @param difficulty the difficulty of this room as a set number, 1 - easy, 2 - med, 3 - hard
     * @return the list of enemies for this encounter.
     */
    private ArrayList<Enemy> generateEnemies(int numOfEnemies, Types type, int difficulty) {
        Random rn = new Random();
        ArrayList<Enemy> enemies = new ArrayList<>();
        for (int x = 0; x < numOfEnemies; x++) {
            int level = rn.nextInt(5) + 1;
            String[] attacks;
            switch(type){
                case FIRE:
                    FireEnemies name = (Arrays.stream(FireEnemies.values()).toList()).get(rn.nextInt(FireEnemies.values().length));
                    switch (name) {
                        case FIRE_DRAUGR:
                            attacks = new String[]{"Chop", "Swing", "Bash"};
                            Enemy fireDraugr = new Enemy(x + 1, level, 15 + level * 10, 15 + level * 10, level * 5, name.toString(), attacks, level * 10);
                            enemies.add(fireDraugr);
                            break;
                        case FIRE_DEMON:
                            attacks = new String[]{"Spit", "Bite", "Blind"};
                            Enemy fireDemon = new Enemy(x + 1, level, 15 + level * 5, 15 + level * 5, level * 5, name.toString(), attacks, level * 10);
                            enemies.add(fireDemon);
                            break;
                        case SMOKESTACK:
                            attacks = new String[]{"Smoke", "Crush", "Burn"};
                            Enemy smokestack = new Enemy(x + 1, level, 15 + level * 10, 15 + level * 10, level * 5, name.toString(), attacks, level * 10);
                            enemies.add(smokestack);
                            break;
                    }
                    break;
                case POISON:

                    break;
                case NECROTIC:
                    break;
            }
        }
        return enemies;
    }

    /**
     * Method to start and run the encounter in the room with the player, runs until the list of enemies is empty
     * @param p the player character
     */
    public void startEncounter(Player p){
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
        if (p.getHealth() > 0) {
            System.out.println("ENCOUNTER CLEARED");
            endEncounter(p);
        }
    }

    /**
     * Method to allow the enemies on the board to make their turn, selects the appropriate enemy attack selector based on what type the enemy is
     * @param p the player to damage
     */
    private void enemyTurns(Player p){
        int playerDamage = 0;
        for (Enemy e : enemies){
            if (e.getName().equals(FireEnemies.FIRE_DRAUGR.toString())){
                playerDamage = e.draugrAttacks(p);
            } else  if (e.getName().equals(FireEnemies.FIRE_DEMON.toString())){
                playerDamage = e.fireDemonAttacks();
            } else if (e.getName().equals(FireEnemies.SMOKESTACK.toString())){
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
                Enemy enemytoDamage = GameIO.damageChoice(x, enemies);
                assert enemytoDamage != null;
                enemytoDamage.setHealth(enemytoDamage.getHealth() - x);
                if (enemytoDamage.getHealth() <= 0){
                    p.setXp(p.getXp() + enemytoDamage.getXp());
                    GameIO.enemyDies(enemytoDamage.getName(), enemytoDamage.getXp());
                    enemies.remove(enemytoDamage);
                }
            }
        } else if (choice.equals("2")){ // Chop
            damage = p.chop();
            Enemy enemytoDamage = GameIO.damageChoice(damage, enemies);
            assert enemytoDamage != null;
            enemytoDamage.setHealth(enemytoDamage.getHealth() - damage);
            if (enemytoDamage.getHealth() <= 0){
                p.setXp(p.getXp() + enemytoDamage.getXp());
                GameIO.enemyDies(enemytoDamage.getName(), enemytoDamage.getXp());
                enemies.remove(enemytoDamage);
            }
        } else { // Swing
            damage = p.swing();
            Enemy enemytoDamage = GameIO.damageChoice(damage, enemies);
            assert enemytoDamage != null;
            enemytoDamage.setHealth(enemytoDamage.getHealth() - damage);
            if (enemytoDamage.getHealth() <= 0){
                p.setXp(p.getXp() + enemytoDamage.getXp());
                GameIO.enemyDies(enemytoDamage.getName(), enemytoDamage.getXp());
                enemies.remove(enemytoDamage);
            }
        }
    }

    private void endEncounter(Player p){
        p.setHealth(p.getPossibleHealth());
        p.levelUp();
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
        return "[ " + ID + " " + type + " " + translateDifficulty(difficulty) + " ]";
    }


}
