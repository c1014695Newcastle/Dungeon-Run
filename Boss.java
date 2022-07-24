import java.util.ArrayList;
import java.util.Random;

/**
 * faf - Fafnir
 * fen - Fenrir
 * jor - Jormungandr
 */

public class Boss {
    private final int level;
    private int possibleHealth;
    private int health;
    private int baseDamage;
    private final String name;
    private final int xp;
    private ArrayList<Enemy> enemies;
    private int phase;
    private final String[] lines;

    public int getLevel() {
        return level;
    }

    public int getPossibleHealth() {
        return possibleHealth;
    }

    public void setPossibleHealth(int possibleHealth) {
        this.possibleHealth = possibleHealth;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getBaseDamage() {
        return baseDamage;
    }

    public void setBaseDamage(int baseDamage) {
        this.baseDamage = baseDamage;
    }

    public String getName() {
        return name;
    }

    public int getXp() {
        return xp;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }

    public int getPhase() {
        return phase;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public String[] getLines() {
        return lines;
    }

    public Boss(int level, String name) {
        this.level = level;
        this.possibleHealth = 200 + (level * 50);
        this.health = possibleHealth;
        this.baseDamage = level * 20;
        this.name = name;
        this.xp = level * 50;
        this.enemies = null;
        this.phase = 1;
        this.lines = setLines(name);
    }

    private String[] setLines(String name) {
        switch (name){
            case "Fafnir" -> {
                return new String[]{"You shall not escape.", "You dare challenge me.", "You shall burn.", "None shall take my treasure", "He will have you."};
            }
            case "Fenrir" -> {
                return null;
            }
            case "Jormungandr" -> {
                return null;
            }
        }
        return null;
    }


    protected int fafPhaseOneAttack(Player p){
        Random rn = new Random();
        int roll = rn.nextInt(2) + 1;
        if (roll == 1){
            GameIO.reportAttack(name, "Bite");
            return standardDamage(rn);
        } else {
            GameIO.reportAttack( name, "Fire");
            return fireDamage(p, rn);
        }
    }

    protected int fafPhaseTwoAttack(Player p){
        Random rn = new Random();
        int roll = rn.nextInt(3) + 1;
        if (roll == 1){
            GameIO.reportAttack(name, "Bite");
            return standardDamage(rn);
        } else if (roll == 2) {
            GameIO.reportAttack( name, "Fire");
            return fireDamage(p, rn);
        } else {
            GameIO.reportAttack( name, "Dive");
            return standardDamage(rn) + 10;
        }
    }

    protected int fafPhaseThreeAttack(Player p){
        Random rn = new Random();
        int roll = rn.nextInt(4) + 1;
        if (roll == 1){
            GameIO.reportAttack(name, "Bite");
            return standardDamage(rn);
        } else if (roll == 2) {
            GameIO.reportAttack( name, "Fire");
            return fireDamage(p, rn);
        } else if (roll == 3){
            GameIO.reportAttack( name, "Dive");
            return standardDamage(rn) + 10;
        } else {
            GameIO.reportAttack(name, "Talon Swipe");
            return  fireDamage(p, rn) + 10;
        }
    }




    private int standardDamage(Random rn){
        int roll = rn.nextInt(6) + 1;
        if (roll < 2){
            return 0;
        } else if (roll < 5){
            return baseDamage;
        } else {
            return baseDamage * 2;
        }
    }

    private int fireDamage(Player p, Random rn){
        int roll = rn.nextInt(6) + 1;
        if (roll < 2){
            return 0;
        } else if (roll < 4){
            p.setBurning(true);
            p.setDebuffCounter(3);
            return baseDamage;
        } else {
            p.setBurning(true);
            p.setDebuffCounter(5);
            return baseDamage * 2;
        }
    }

    public static void main(String[] args) {
        Boss d = new Boss(1, "Fafnir");
        Player p = new Player("Luke");
    }

    @Override
    public String toString(){
        String healthBar = GameIO.playerHealthBar(health, possibleHealth);
        String topBar = GameIO.topBorder(healthBar.length() + 12);
        String bottomBar = GameIO.bottomBorder(healthBar.length() + 12);
        return topBar +
                "\t" + name.replace("_"," ") + "\n"  + "\tHealth: " + healthBar + "\n    "+ health + "\\" + possibleHealth + "\n"  +bottomBar;
    }

}
