import Enums.FireEnemies;
import Enums.Types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Room {
    private int ID;
    private ArrayList<Enemy> waveOne;
    private int difficulty;
    private Types type;


    public Room(int ID, int difficulty, Types type, int numOfEnemies) {
        this.ID = ID;
        this.difficulty = difficulty;
        this.type = type;
        this.waveOne = generateEnemies(numOfEnemies, type, difficulty);


    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public ArrayList<Enemy> getWaveOne() {
        return waveOne;
    }

    public void setWaveOne(ArrayList<Enemy> waveOne) {
        this.waveOne = waveOne;
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
            if (difficulty == 1) {
                switch (type) {
                    case FIRE:
                        FireEnemies name = (Arrays.stream(FireEnemies.values()).toList()).get(rn.nextInt(FireEnemies.values().length));
                        switch (name) {
                            case FIRE_DRAUGR:
                                attacks = new String[]{"Chop", "Swing", "Bash"};
                                Enemy fireDraugr = new Enemy(x + 1, level, 15 + level * 5, 15 + level * 5, level, name.toString(), attacks, level * 10);
                                enemies.add(fireDraugr);
                                break;
                            case FIRE_DEMON:
                                attacks = new String[]{"Spit", "Bite", "Blind"};
                                Enemy fireDemon = new Enemy(x + 1, level, 15 + level * 5, 15 + level * 5, level, name.toString(), attacks, level * 10);
                                enemies.add(fireDemon);
                                break;
                            case SMOKESTACK:
                                attacks = new String[]{"Smoke", "Crush", "Burn"};
                                Enemy smokestack = new Enemy(x + 1, level, 15 + level * 5, 15 + level * 5, level, name.toString(), attacks, level * 10);
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
        }
        return enemies;
    }

    /**
     * Method to start and run the encounter in the room with the player, runs until the list of enemies is empty
     * @param p the player character
     */
    public void startEncounter(Player p){
        while (!waveOne.isEmpty() && p.getHealth() > 0){
            System.out.println(p);
            for (Enemy e : waveOne){
                System.out.println(e);
            }
            if ((p.isPoisoned() || p.isBurning() || p.isNecrosis()) && p.getDebuffCounter() == 0){
                p.removeDebuffs();
                GameIO.reportClearing();
            }
            GameIO.sleep(1000);
            playerTurn(p);
            GameIO.sleep(1000);
            enemyTurns(p);
            GameIO.sleep(1000);
            if (p.getHealth() <= 0){
                GameIO.playerDies();
            }
        }
    }

    /**
     * Method to allow the enemies on the board to make their turn, selects the appropriate enemy attack selector based on what type the enemy is
     * @param p the player to damage
     */
    private void enemyTurns(Player p){
        int playerDamage = 0;
        for (Enemy e : waveOne){
            if (e.getName().equals(FireEnemies.FIRE_DRAUGR.toString().replace("_"," "))){
                playerDamage = e.draugrAttacks(p);
            } else  if (e.getName().equals(FireEnemies.FIRE_DEMON.toString().replace("_"," "))){
                playerDamage = e.fireDemonAttacks(p);
            } else if (e.getName().equals(FireEnemies.SMOKESTACK.toString().replace("_"," "))){
                playerDamage = e.smokestackAttacks();
            }
            GameIO.damageReport(playerDamage, p.isPoisoned(), p.isBurning(), p.isBurning());
            p.setHealth(p.getHealth() - playerDamage);
        }
    }

    public void playerTurn(Player p){
        p.debuffs();
        String choice;
        int damage;
        choice = GameIO.playerChoice();
        if (choice.equals("1")){ // Cast
            int[] castDamage = p.cast();
            for (int x : castDamage){
                if (!waveOne.isEmpty()) {
                    Enemy enemytoDamage = GameIO.damageChoice(x, waveOne);
                    assert enemytoDamage != null;
                    enemytoDamage.setHealth(enemytoDamage.getHealth() - x);
                    if (enemytoDamage.getHealth() <= 0) {
                        p.setXp(p.getXp() + enemytoDamage.getXp());
                        GameIO.enemyDies(enemytoDamage.getName(), enemytoDamage.getXp());
                        waveOne.remove(enemytoDamage);
                    }
                }
            }
        } else if (choice.equals("2")){ // Chop
            damage = p.chop();
            Enemy enemytoDamage = GameIO.damageChoice(damage, waveOne);
            assert enemytoDamage != null;
            enemytoDamage.setHealth(enemytoDamage.getHealth() - damage);
            if (enemytoDamage.getHealth() <= 0){
                p.setXp(p.getXp() + enemytoDamage.getXp());
                GameIO.enemyDies(enemytoDamage.getName(), enemytoDamage.getXp());
                waveOne.remove(enemytoDamage);
            }
        } else { // Swing
            damage = p.swing()/ waveOne.size();
            for (int x = 0; x < waveOne.size(); x++) {
                Enemy enemytoDamage = waveOne.get(x);
                GameIO.swingReport(enemytoDamage.getName(), damage);
                enemytoDamage.setHealth(enemytoDamage.getHealth() - damage);
                if (enemytoDamage.getHealth() <= 0) {
                    p.setXp(p.getXp() + enemytoDamage.getXp());
                    GameIO.enemyDies(enemytoDamage.getName(), enemytoDamage.getXp());
                    waveOne.remove(enemytoDamage);
                }
                GameIO.sleep(1000);
            }
        }
        p.checkDebuffs();
    }

    protected void endEncounter(Player p){
        p.setHealth(p.getPossibleHealth());
        p.levelUp();
        p.removeDebuffs();
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

class BossRoom extends Room {
    private Boss boss;

    public Boss getBoss() {
        return boss;
    }

    public void setBoss(Boss boss) {
        this.boss = boss;
    }

    public BossRoom(int ID, int difficulty, Types type, Boss boss) {
        super(ID, difficulty, type, 0);
        this.boss = boss;
    }

    /**
     * Code for the boss encounter, continues until either the player or the boss is dead (health at 0 or below).
     * @param p the player to fight the encounter
     */
    public void bossEncounter(Player p) {
        Random rn = new Random();
        while (boss.getHealth() > 0 && p.getHealth() > 0) {
            System.out.println(p);
            GameIO.sleep(1000);
            System.out.println(boss);
            if (getWaveOne().isEmpty()) {
                GameIO.sleep(1000);
                attackBoss(p);
                GameIO.sleep(1000);
                if (boss.getHealth() <= (boss.getPossibleHealth() * 0.75) && boss.getPhase() != 2) {
                    switch (boss.getName()){
                        case "Fafnir" -> GameIO.fafClaws();
                        case "Fenrir" -> GameIO.fenClaws();
                        case "Jormungandr" -> System.out.println("NA");
                    }
                    boss.setPhase(2);
                    GameIO.reportSupportEnemies(boss.getName());
                    phaseBreak(2, boss.getLevel());
                } else if (boss.getHealth() <= (boss.getPossibleHealth() * 0.25) && boss.getPhase() != 3) {
                    switch (boss.getName()){
                        case "Fafnir" -> GameIO.fafFlight();
                        case "Fenrir" -> GameIO.fenSnarl();
                        case "Jormungandr" -> System.out.println("NA");
                    }
                    boss.setPhase(3);
                    GameIO.reportSupportEnemies(boss.getName());
                    boss.setBaseDamage(boss.getBaseDamage() * 2);
                    phaseBreak(3, boss.getLevel());
                }
                GameIO.sleep(2000);
                if (boss.getHealth() >= 0) {
                    GameIO.bossLine(boss.getLines()[rn.nextInt(boss.getLines().length)]);
                    GameIO.sleep(2000);
                    int damage = 0;
                    switch (boss.getPhase()) {
                        case 1 -> {
                            switch (boss.getName()){
                                case "Fafnir" -> damage = boss.fafPhaseOneAttack(p);
                                case "Fenrir" -> damage = 0;
                                case "Jormungandr" -> damage = 0;
                            }
                            GameIO.damageReport(damage, p.isPoisoned(), p.isBurning(), p.isBurning());
                            p.setHealth(p.getHealth() - damage);
                        }
                        case 2 -> {
                            switch (boss.getName()){
                                case "Fafnir" -> damage = boss.fafPhaseTwoAttack(p);
                                case "Fenrir" -> damage = 0;
                                case "Jormungandr" -> damage = 0;
                            }
                            GameIO.damageReport(damage, p.isPoisoned(), p.isBurning(), p.isBurning());
                            p.setHealth(p.getHealth() - damage);
                        }
                        case 3 -> {
                            switch (boss.getName()){
                                case "Fafnir" -> damage = boss.fafPhaseThreeAttack(p);
                                case "Fenrir" -> damage = 0;
                                case "Jormungandr" -> damage = 0;
                            }
                            GameIO.damageReport(damage, p.isPoisoned(), p.isBurning(), p.isBurning());
                            p.setHealth(p.getHealth() - damage);
                        }
                    }
                    if (p.getHealth() <= 0) {
                        GameIO.playerDies();
                    }
                } else {
                    System.out.println("YOU KILLED FAFNIR");
                    endEncounter(p);
                }
            } else {
                switch (boss.getName()){
                    case "Fafnir" -> GameIO.fafPhaseBattle();
                    case "Fenrir" -> GameIO.fenPhaseBattle();
                    case "Jormungandr" -> GameIO.jorPhaseBattle();
                }
                startEncounter(p);
                p.powerUp();
                switch (boss.getName()){
                    case "Fafnir" -> GameIO.fafVulnerable();
                    case "Fenrir" -> GameIO.fenVulnerable();
                    case "Jormungandr" -> GameIO.jorVulnerable();
                }
            }
        }
    }


    /**
     * Method to allow the player to attack the boss, pick between 3 different attacks that are all aimed at the boss
     * @param p the player in the encounter
     */
    private void attackBoss(Player p) {
        Random rn = new Random();
        p.debuffs();
        if (p.getHealth() <= 0){
            GameIO.playerDies();
        }
        String choice;
        int damage;
        choice = GameIO.playerChoice();
        if (choice.equals("1")) { // Cast
            int[] castDamage = p.cast();
            for (int x : castDamage) {
                if (boss.getPhase() == 3 && rn.nextInt(5) <=3) {
                    GameIO.recordMiss(boss.getName());
                } else {
                    boss.setHealth(boss.getHealth() - x);
                    GameIO.chopReport(boss.getName(), x);
                }
            }
        } else if (choice.equals("2")) { // Chop
            damage = p.chop();
            if (boss.getPhase() == 3 && rn.nextInt(5) <=3) {
                GameIO.recordMiss(boss.getName());
            } else {
                boss.setHealth(boss.getHealth() - damage);
                GameIO.chopReport(boss.getName(), damage);
            }
        } else { // Swing
            damage = p.swing();
            if (boss.getPhase() == 3 && rn.nextInt(5) <= 3) {
                GameIO.recordMiss(boss.getName());
            } else {
                boss.setHealth(boss.getHealth() - damage);
                GameIO.chopReport(boss.getName(), damage);
            }
        }
        p.checkDebuffs();
    }

    /**
     * Method to generate the support enemies that spawn between boss phases, player must kill these before they are allowed to damage the boss again
     * @param numOfEnemies the number of support enemies that should spawn
     * @param level the level these enemies spawn at
     */
    private void phaseBreak(int numOfEnemies, int level){
        String[] attacks;
        for (int x = 0; x < numOfEnemies; x++) {
            attacks = new String[]{"Chop", "Swing", "Bash"};
            Enemy fireDraugr = new Enemy(x + 1, level, 15 + level * 5, 15 + level * 5, level, FireEnemies.FIRE_DRAUGR.toString().replace("_", " "), attacks, level * 10);
                getWaveOne().add(fireDraugr);
        }
    }

    public static void main(String[] args) {
        Boss d = new Boss(1, "Fafnir");
        Player p = new Player("LUKE");
        BossRoom b = new BossRoom(1, 2, Types.FIRE, d);
        b.bossEncounter(p);
    }

}
