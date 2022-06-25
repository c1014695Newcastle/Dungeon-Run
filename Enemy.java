public class Enemy {
    public int ID;

    public static String[] fireTypes = {"Draugr", "Fire Demon", "Smokestack"};
    private int level;
    private int possibleHealth;
    private int health;
    private int baseDamage;
    private String name;


    public Enemy(int ID, int level, int possibleHealth, int health, int baseDamage, String name) {
        this.ID = ID;
        this.level = level;
        this.possibleHealth = possibleHealth;
        this.health = health;
        this.baseDamage = baseDamage;
        this.name = name;

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
}


