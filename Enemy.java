import java.util.ArrayList;
import java.util.Arrays;

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

    @Override
    public String toString() {

        return "╔════════════════════════╗ \n" +
                 "\tLevel " + level + " " + name + "\n"  + "\tHealth: " + health + "/" +  + possibleHealth + "\n"  + "╚════════════════════════╝ ";
    }
}


