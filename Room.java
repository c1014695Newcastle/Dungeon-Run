import Enums.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Room {
    private enum Reward {
        EMPTY, HELMET, CHESTPIECE, WHETSTONE, RUNE,  MONSTER_HEART, EXTRA_LIFE
    }

    private int ID;
    private ArrayList<Enemy> waveOne;
    private Difficulty difficulty;
    private Types type;
    private final Reward reward;
    private boolean visited;


    public Room(int ID, Difficulty difficulty, Types type, int numOfEnemies) {
        this.ID = ID;
        this.difficulty = difficulty;
        this.type = type;
        this.waveOne = generateEnemies(numOfEnemies, type);
        this.reward = generateReward();
        this.visited = false;
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

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Types getType() {
        return type;
    }

    public void setType(Types type) {
        this.type = type;
    }

    public Reward getReward(){
        return reward;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    private Reward generateReward(){
        Random rn = new Random();
        int roll = rn.nextInt(11);
        if (roll < 2){
            //20% chance
            return Reward.EMPTY;
        } else if (roll == 2){
            //10% chance
            return Reward.HELMET;
        } else if (roll <= 4){
            //20% chance
            return Reward.CHESTPIECE;
        } else if (roll <= 6) {
            //20% chance
            return Reward.RUNE;
        } else if (roll == 7){
            //10% chance
            return Reward.WHETSTONE;
        } else if (roll <= 9){
            return Reward.MONSTER_HEART;
        } else {
            return Reward.EXTRA_LIFE;
        }
    }



    /**
     * Generates the enemies for the room encounter based on the set difficulty of the room, the number of enemies and the type of encounter this is
     * @param numOfEnemies the number of enemies which should spawn in this encounter
     * @param type the type of encounter, whether it be flame, poison or necrotic
     * @return the list of enemies for this encounter.
     */
    private ArrayList<Enemy> generateEnemies(int numOfEnemies, Types type) {
        Random rn = new Random();
        ArrayList<Enemy> enemies = new ArrayList<>();
        PoisonEnemies pName;
        FireEnemies fName;
        NecroticEnemies nName;
        for (int x = 0; x < numOfEnemies; x++) {
            int level = rn.nextInt(5) + 1;
            String[] attacks;
            switch (type) {
                case FIRE -> {
                    fName = (Arrays.stream(FireEnemies.values()).toList()).get(rn.nextInt(FireEnemies.values().length));
                    switch (fName) {
                        case FIRE_DRAUGR -> {
                            attacks = new String[]{"Chop", "Swing", "Bash"};
                            Enemy fireDraugr = new Enemy(x + 1, level, 15 + level * 5, 15 + level * 5, level, fName.toString(), attacks, level * 10);
                            enemies.add(fireDraugr);
                        }
                        case FIRE_DEMON -> {
                            attacks = new String[]{"Spit", "Bite", "Blind"};
                            Enemy fireDemon = new Enemy(x + 1, level, 15 + level * 5, 15 + level * 5, level, fName.toString(), attacks, level * 10);
                            enemies.add(fireDemon);
                        }
                        case SMOKESTACK -> {
                            attacks = new String[]{"Smoke", "Crush", "Burn"};
                            Enemy smokestack = new Enemy(x + 1, level, 15 + level * 5, 15 + level * 5, level, fName.toString(), attacks, level * 10);
                            enemies.add(smokestack);
                        }
                    }
                }
                case POISON -> {
                    pName = (Arrays.stream(PoisonEnemies.values()).toList()).get(rn.nextInt(PoisonEnemies.values().length));
                    switch (pName) {
                        case POISON_DRAUGR -> {
                            attacks = new String[]{"Chop", "Swing", "Bash"};
                            Enemy poisonDraugr = new Enemy(x + 1, level, 15 + level * 5, 15 + level * 5, level, pName.toString(), attacks, level * 10);
                            enemies.add(poisonDraugr);
                        }
                        case SNAKE -> {
                            attacks = new String[]{"Spit", "Bite", "Strangle"};
                            Enemy snake = new Enemy(x + 1, level, 15 + level * 5, 15 + level * 5, level, pName.toString(), attacks, level * 10);
                            enemies.add(snake);
                        }
                        case TROLL -> {
                            attacks = new String[]{"Bash", "Stomp", "Punch"};
                            Enemy troll = new Enemy(x + 1, level, 15 + level * 10, 15 + level * 10, level, pName.toString(), attacks, level * 10);
                            enemies.add(troll);
                        }
                    }
                }
                case NECROTIC -> {
                    nName = (Arrays.stream(NecroticEnemies.values()).toList()).get(rn.nextInt(NecroticEnemies.values().length));
                    switch (nName) {
                        case NECROTIC_DRAUGR -> {
                            attacks = new String[]{"Chop", "Swing", "Bash"};
                            Enemy poisonDraugr = new Enemy(x + 1, level, 15 + level * 5, 15 + level * 5, level, nName.toString(), attacks, level * 10);
                            enemies.add(poisonDraugr);
                        }
                        case WOLF -> {
                            attacks = new String[]{"Spit", "Bite", "Strangle"};
                            Enemy snake = new Enemy(x + 1, level, 15 + level * 5, 15 + level * 5, level, nName.toString(), attacks, level * 10);
                            enemies.add(snake);
                        }
                        case ELF -> {
                            attacks = new String[]{"Smoke", "Crush", "Burn"};
                            Enemy troll = new Enemy(x + 1, level, 15 + level * 10, 15 + level * 10, level, nName.toString(), attacks, level * 10);
                            enemies.add(troll);
                        }
                    }
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
        if (!visited){
            switch (reward){
                case WHETSTONE -> p.whetstone();
                case CHESTPIECE -> p.addArmour(50);
                case HELMET -> p.addArmour(20);
                case MONSTER_HEART -> p.monsterHeart();
                case RUNE -> p.rune();
                case EXTRA_LIFE -> p.extraLife();
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
            if (e.getName().equals(FireEnemies.FIRE_DRAUGR.toString().replace("_"," ")) || e.getName().equals(PoisonEnemies.POISON_DRAUGR.toString().replace("_"," ")) || e.getName().equals(NecroticEnemies.NECROTIC_DRAUGR.toString().replace("_"," "))){
                playerDamage = e.draugrAttacks(p);
            } else  if (e.getName().equals(FireEnemies.FIRE_DEMON.toString().replace("_"," "))){
                playerDamage = e.fireDemonAttacks(p);
            } else if (e.getName().equals(FireEnemies.SMOKESTACK.toString().replace("_"," "))){
                playerDamage = e.smokestackAttacks();
            } else if (e.getName().equals(PoisonEnemies.SNAKE.toString())){
                playerDamage = e.snakeAttacks(p);
            } else  if (e.getName().equals(PoisonEnemies.TROLL.toString())){
                playerDamage = e.trollAttacks(p);
            } else if (e.getName().equals(NecroticEnemies.WOLF.toString())){
                playerDamage = e.wolfAttacks(p);
            } else  if (e.getName().equals(NecroticEnemies.ELF.toString())){
                playerDamage = e.elfAttacks(p);
            }
            p.takeDamage(playerDamage);
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

    public static void main(String[] args) {
        Room r = new Room(1, Difficulty.LOW, Types.FIRE, 3);
        System.out.println(r);
    }

    public String toString(){
        return "[ " + ID + " " + type + " " + difficulty + " ]";
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

    public BossRoom(int ID, Difficulty difficulty, Types type, Boss boss) {
        super(ID, difficulty, type, 0);
        this.boss = boss;
    }

    /**
     * Code for the boss encounter, continues until either the player or the boss is dead (health at 0 or below).
     * @param p the player to fight the encounter
     */
    public void bossEncounter(Player p) {
        Random rn = new Random();
        switch (boss.getName()){
            case FAFNIR -> GameIO.fafIntro();
            case FENRIR -> GameIO.fenIntro();
            case JORMUNGANDR -> GameIO.jorIntro();
        }
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
                        case FAFNIR -> GameIO.fafClaws();
                        case FENRIR -> GameIO.fenClaws();
                        case JORMUNGANDR -> System.out.println("NA");
                    }
                    boss.setPhase(2);
                    GameIO.reportSupportEnemies(boss.getName().toString());
                    phaseBreak(2, boss.getLevel());
                } else if (boss.getHealth() <= (boss.getPossibleHealth() * 0.25) && boss.getPhase() != 3) {
                    switch (boss.getName()){
                        case FAFNIR -> GameIO.fafFlight();
                        case FENRIR -> GameIO.fenSnarl();
                        case JORMUNGANDR -> System.out.println("NA");
                    }
                    boss.setPhase(3);
                    GameIO.reportSupportEnemies(boss.getName().toString());
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
                                case FAFNIR -> damage = boss.fafPhaseOneAttack(p);
                                case FENRIR -> damage = boss.fenPhaseOneAttack(p);
                                case JORMUNGANDR -> damage = 0;
                            }
                            p.takeDamage(damage);
                        }
                        case 2 -> {
                            switch (boss.getName()){
                                case FAFNIR -> damage = boss.fafPhaseTwoAttack(p);
                                case FENRIR -> damage = boss.fenPhaseTwoAttack(p);
                                case JORMUNGANDR -> damage = 0;
                            }
                            p.takeDamage(damage);
                        }
                        case 3 -> {
                            switch (boss.getName()){
                                case FAFNIR -> damage = boss.fafPhaseThreeAttack(p);
                                case FENRIR -> damage = boss.fenPhaseThreeAttack(p);
                                case JORMUNGANDR -> damage = 0;
                            }
                            p.takeDamage(damage);
                        }
                    }
                    if (p.getHealth() <= 0) {
                        GameIO.playerDies();
                    }
                } else {
                    System.out.println("YOU KILLED " + boss.getName());
                    endEncounter(p);
                }
            } else {
                switch (boss.getName()){
                    case FAFNIR -> GameIO.fafPhaseBattle();
                    case FENRIR -> GameIO.fenPhaseBattle();
                    case JORMUNGANDR -> GameIO.jorPhaseBattle();
                }
                startEncounter(p);
                p.powerUp();
                switch (boss.getName()){
                    case FAFNIR -> GameIO.fafVulnerable();
                    case FENRIR -> GameIO.fenVulnerable();
                    case JORMUNGANDR -> GameIO.jorVulnerable();
                }
            }

        }
        //Pick a reward from the boss
        String choice = GameIO.bossReward(boss.getRewards());
        if (choice.equals("1")){
            switch (boss.getName()){
                case FAFNIR -> p.dragonHeart();
                case FENRIR -> GameIO.fenVulnerable();
                case JORMUNGANDR -> GameIO.jorVulnerable();
            }
        } else if (choice.equals("2")){
            switch (boss.getName()){
                case FAFNIR -> p.dragonScale();
                case FENRIR -> GameIO.fenVulnerable();
                case JORMUNGANDR -> GameIO.jorVulnerable();
            }
        } else {
            switch (boss.getName()){
                case FAFNIR -> p.dragonClaw();
                case FENRIR -> GameIO.fenVulnerable();
                case JORMUNGANDR -> GameIO.jorVulnerable();
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
                    GameIO.recordMiss(boss.getName().toString());
                } else {
                    boss.setHealth(boss.getHealth() - x);
                    GameIO.chopReport(boss.getName().toString(), x);
                }
            }
        } else if (choice.equals("2")) { // Chop
            damage = p.chop();
            if (boss.getPhase() == 3 && rn.nextInt(5) <=3) {
                GameIO.recordMiss(boss.getName().toString());
            } else {
                boss.setHealth(boss.getHealth() - damage);
                GameIO.chopReport(boss.getName().toString(), damage);
            }
        } else { // Swing
            damage = p.swing();
            if (boss.getPhase() == 3 && rn.nextInt(5) <= 3) {
                GameIO.recordMiss(boss.getName().toString());
            } else {
                boss.setHealth(boss.getHealth() - damage);
                GameIO.chopReport(boss.getName().toString(), damage);
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
        Boss d = new Boss(1, Boss.bossName.FAFNIR);
        Player p = new Player("LUKE");
        BossRoom b = new BossRoom(1, Difficulty.LOW, Types.FIRE, d);
        b.bossEncounter(p);
    }

}
