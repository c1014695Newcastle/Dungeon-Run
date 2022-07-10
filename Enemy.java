import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Enemy {
    public  final static String[] fireTypes = {"Draugr", "Fire Demon", "Smokestack"};
    public  final static String[] poisonTypes = {"Draugr", "Fire Demon", "Smokestack"};
    public  final static String[] necroticTypes = {"Draugr", "Fire Demon", "Smokestack"};
    public final int ID;
    private int level;
    private int possibleHealth;
    private int health;
    private int baseDamage;
    private String name;

    public int getID() {
        return ID;
    }

    public String[] getAttacks() {
        return attacks;
    }

    private final String[] attacks;


    public Enemy(int ID, int level, int possibleHealth, int health, int baseDamage, String name, String[] attacks) {
        this.ID = ID;
        this.level = level;
        this.possibleHealth = possibleHealth;
        this.health = health;
        this.baseDamage = baseDamage;
        this.name = name;
        this. attacks = attacks;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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

    public void setName(String name) {
        this.name = name;
    }

    public int draugrAttacks(Player p) {
        Random rn = new Random();
        String[] attacks = getAttacks();
        String attack = attacks[rn.nextInt(attacks.length)];
        if (attack.equals("Chop")){
            GameIO.reportAttack("Draugr", "Chop");
            return draugrChop(p);
        } else if (attack.equals("Swing")){
            GameIO.reportAttack("Draugr", "Swing");
            return draugrSwing(p);
        } else {
            GameIO.reportAttack("Draugr", "Bash");
            return draugrBash();
        }
    }

    private int draugrChop(Player p){
        Random rn = new Random();
        int roll = rn.nextInt(10) + 1;
        if (roll == 1){
            return 0;
        } else if (roll >= 2 && roll < 4){
            return this.baseDamage/2;
        } else if (roll >= 4 && roll < 6) {
            return this.baseDamage;
        } else if (roll >= 6 && roll < 8) {
            return this.baseDamage+ (this.baseDamage * roll/10);
        } else if (roll >= 8) {
            p.setPoisoned(true);
            return this.baseDamage * 2;
        }
        return 0;
    }

    private int draugrSwing(Player p) {
        Random rn = new Random();
        int roll = rn.nextInt(10) + 1;
        if (roll == 1){
            return 0;
        } else if (roll >= 2 && roll < 4){
            return this.baseDamage;
        } else if (roll >= 4 && roll < 6) {
            return this.baseDamage + 2;
        } else if (roll >= 6 && roll < 8) {
            return this.baseDamage + 5;
        } else if (roll >= 8) {
            p.setPoisoned(true);
            return this.baseDamage + 7;
        }
        return 0;
    }

    private int draugrBash(){
        Random rn = new Random();
        int roll = rn.nextInt(10) + 1;
        if (roll == 1){
            return 0;
        } else if (roll >= 2 && roll < 4){
            return this.baseDamage;
        } else if (roll >= 4 && roll < 6) {
            return this.baseDamage + 1;
        } else if (roll >= 6 && roll < 8) {
            return this.baseDamage + 3;
        } else if (roll >= 8) {
            return this.baseDamage + 10;
        }
        return 0;
    }

    public int fireDemonAttacks() {
        Random rn = new Random();
        String[] attacks = getAttacks();
        String attack = attacks[rn.nextInt(attacks.length)];
        if (attack.equals("Spit")){
            return demonSpit();
        } else if (attack.equals("Bite")){
            return demonBite();
        } else {
            return demonBurn();
        }
    }

    private int demonSpit() {
        return 0;
    }

    private int demonBite() {
        return 0;
    }

    private int demonBurn() {
        return 0;
    }

    public int smokestackAttacks() {
        Random rn = new Random();
        String[] attacks = getAttacks();
        String attack = attacks[rn.nextInt(attacks.length)];
        if (attack.equals("Spit")){
            return 0;
        } else if (attack.equals("Bite")){
            return 0;
        } else {
            return 0;
        }
    }




    @Override
    public String toString() {

        return "╔════════════════════════╗ \n" +
                 "\tLevel " + level + " " + name + "\n"  + "\tHealth: " + health + "/" +  + possibleHealth + "\n"  + "╚════════════════════════╝ ";
    }
}


