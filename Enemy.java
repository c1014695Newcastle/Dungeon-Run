

import Enums.FireEnemies;

import java.util.Random;

public class Enemy {
    public final int ID;
    private int level;
    private int possibleHealth;
    private int health;
    private int baseDamage;
    private String name;
    private int xp;

    public int getID() {
        return ID;
    }

    public String[] getAttacks() {
        return attacks;
    }

    private final String[] attacks;


    public Enemy(int ID, int level, int possibleHealth, int health, int baseDamage, String name, String[] attacks, int xp) {
        this.ID = ID;
        this.level = level;
        this.possibleHealth = possibleHealth;
        this.health = health;
        this.baseDamage = baseDamage;
        this.name = name;
        this. attacks = attacks;
        this.xp = xp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }


    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
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

    protected int necroticDamageAttack(Player p, Random rn, int medBonusDamage, int highBonusDamage, int critDamage){
        int roll = rn.nextInt(10) + 1;
        if (roll == 1){
            return 0;
        } else if (roll < 4){
            return baseDamage;
        } else if (roll < 6) {
            return baseDamage + medBonusDamage;
        } else if (roll < 8) {
            return baseDamage + highBonusDamage;
        } else {
            p.setNecrosis(true);
            p.setDebuffCounter(3);
            return baseDamage + critDamage;
        }
    }

    protected int fireDamageAttack(Player p, Random rn, int medBonusDamage, int highBonusDamage, int critDamage){
        int roll = rn.nextInt(10) + 1;
        if (roll == 1){
            return 0;
        } else if (roll < 4){
            return baseDamage;
        } else if (roll < 6) {
            return baseDamage + medBonusDamage;
        } else if (roll < 8) {
            return baseDamage + highBonusDamage;
        } else {
            p.setBurning(true);
            p.setDebuffCounter(3);
            return baseDamage + critDamage;
        }
    }

    protected int poisonDamageAttack(Random rn, Player p, int medBonusDamage, int highBonusDamage, int critDamage){
        int roll = rn.nextInt(10) + 1;
        if (roll == 1){
            return 0;
        } else if (roll < 4){
            return baseDamage;
        } else if (roll < 6) {
            return baseDamage + medBonusDamage;
        } else if (roll < 8) {
            return baseDamage + highBonusDamage;
        } else {
            p.setPoisoned(true);
            p.setDebuffCounter(3);
            return baseDamage + critDamage;
        }
    }

    protected int standardDamageAttack(Random rn, int medBonusDamage, int highBonusDamage, int critDamage){
        int roll = rn.nextInt(10) + 1;
        if (roll == 1){
            return 0;
        } else if (roll < 4){
            return baseDamage;
        } else if (roll < 6) {
            return baseDamage + medBonusDamage;
        } else if (roll < 8) {
            return baseDamage + highBonusDamage;
        } else {
            return baseDamage + critDamage;
        }
    }

    public int draugrAttacks(Player p) {
        Random rn = new Random();
        String[] attacks = getAttacks();
        String attack = attacks[rn.nextInt(attacks.length)];
        if (attack.equals("Chop")){
            GameIO.reportAttack("Draugr", "Chop");
            if ("FIRE_DRAUGR".equals(getName())) {
                return fireDamageAttack(p, rn, baseDamage / 4, baseDamage / 2, baseDamage);
            } else if ("POISON_DRAUGR".equals(getName())){
                return poisonDamageAttack(rn, p, baseDamage / 4, baseDamage / 2, baseDamage);
            } else {
                return necroticDamageAttack(p, rn, baseDamage / 4, baseDamage / 2, baseDamage);
            }
        } else if (attack.equals("Swing")){
            GameIO.reportAttack("Draugr", "Swing");
            return fireDamageAttack(p, rn, baseDamage/4, baseDamage/2, baseDamage);
        } else {
            GameIO.reportAttack("Draugr", "Bash");
            return standardDamageAttack(rn, baseDamage/4, baseDamage/2, baseDamage);
        }
    }

    public int fireDemonAttacks(Player p) {
        Random rn = new Random();
        String[] attacks = getAttacks();
        String attack = attacks[rn.nextInt(attacks.length)];
        if (attack.equals("Spit")){
            GameIO.reportAttack("Draugr", "Spit");
            return standardDamageAttack(rn, baseDamage/4, baseDamage/3, baseDamage/2);
        } else if (attack.equals("Bite")){
            GameIO.reportAttack("Draugr", "Bite");
            return standardDamageAttack(rn, baseDamage/4, baseDamage/3, baseDamage/2);
        } else {
            GameIO.reportAttack("Draugr", "Burn");
            return fireDamageAttack(p, rn, baseDamage/4, baseDamage/3, baseDamage/2);
        }
    }

    public int smokestackAttacks() {
        Random rn = new Random();
        String[] attacks = getAttacks();
        String attack = attacks[rn.nextInt(attacks.length)];
        if (attack.equals("Spit")) {
            GameIO.reportAttack("Smokestack", "Spit");
            return standardDamageAttack(rn, baseDamage / 4, baseDamage / 2, baseDamage);
        } else if (attack.equals("Bite")) {
            GameIO.reportAttack("Smokestack", "Bite");
            return standardDamageAttack(rn, baseDamage / 4, baseDamage / 2, baseDamage);
        } else {
            GameIO.reportAttack("Smokestack", "Crush");
            return standardDamageAttack(rn, baseDamage / 4, baseDamage / 2, baseDamage);
        }
    }

    protected int snakeAttacks(Player p) {
        Random rn = new Random();
        String attack = attacks[rn.nextInt(attacks.length)];
        if (attack.equals("Spit")){
            GameIO.reportAttack(name, "Spit");
            return poisonDamageAttack(rn, p, baseDamage / 4, baseDamage / 2, baseDamage);
        } else if (attack.equals("Bite")){
            GameIO.reportAttack(name, "Bite");
            return poisonDamageAttack(rn, p, baseDamage / 4, baseDamage / 2, baseDamage);
        } else {
            GameIO.reportAttack(name, "Strangle");
            return standardDamageAttack(rn, baseDamage / 4, baseDamage / 2, baseDamage);
        }
    }

    // TODO: IMPLEMENT TROLL ATTACKS
    protected int trollAttacks(Player p){
        Random rn = new Random();
        String[] attacks = getAttacks();
        String attack = attacks[rn.nextInt(attacks.length)];
        if (attack.equals("Spit")){
            GameIO.reportAttack("Draugr", "Spit");
            return standardDamageAttack(rn, baseDamage/4, baseDamage/3, baseDamage/2);
        } else if (attack.equals("Bite")){
            GameIO.reportAttack("Draugr", "Bite");
            return standardDamageAttack(rn, baseDamage/4, baseDamage/3, baseDamage/2);
        } else {
            GameIO.reportAttack("Draugr", "Burn");
            return necroticDamageAttack(p, rn, baseDamage/4, baseDamage/3, baseDamage/2);
        }
    }

    protected int wolfAttacks(Player p){
        Random rn = new Random();
        String[] attacks = getAttacks();
        String attack = attacks[rn.nextInt(attacks.length)];
        if (attack.equals("Scratch")){
            GameIO.reportAttack("Wolf", "Scratch");
            return necroticDamageAttack(p, rn, baseDamage/4, baseDamage/3, baseDamage/2);
        } else if (attack.equals("Charge")){
            GameIO.reportAttack("Wolf", "Charge");
            return standardDamageAttack(rn, baseDamage/4, baseDamage/3, baseDamage/2);
        } else {
            GameIO.reportAttack("Wolf", "Bite");
            return necroticDamageAttack(p, rn, baseDamage/4, baseDamage/3, baseDamage/2);
        }
    }

    protected int elfAttacks(Player p){
        Random rn = new Random();
        String[] attacks = getAttacks();
        String attack = attacks[rn.nextInt(attacks.length)];
        if (attack.equals("Fire Cast")){
            GameIO.reportAttack("Elf", "Fire Cast");
            return fireDamageAttack(p, rn, baseDamage/4, baseDamage/3, baseDamage/2);
        } else if (attack.equals("Spear")){
            GameIO.reportAttack("Elf", "Spear");
            return standardDamageAttack(rn, baseDamage/4, baseDamage/3, baseDamage/2);
        } else {
            GameIO.reportAttack("Elf", "Necrotic Cast");
            return necroticDamageAttack(p, rn, baseDamage/4, baseDamage/3, baseDamage/2);
        }
    }

    @Override
    public String toString() {
        String healthBar = GameIO.playerHealthBar(health, possibleHealth);
        String topBar = GameIO.topBorder(healthBar.length()*2);
        String bottomBar = GameIO.bottomBorder(healthBar.length()*2);
        return topBar +
                 "\tLevel " + level + " " + name.replace("_"," ") + "\n"  + "\tHealth: " + healthBar + "\n    "+ health + "\\" + possibleHealth + "\n"  +bottomBar;
    }


}



