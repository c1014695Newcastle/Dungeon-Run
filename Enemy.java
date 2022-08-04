

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

    protected int draugrFireDamageAttack(Player p, Random rn){
        int roll = rn.nextInt(10) + 1;
        if (roll <= 1){
            return 0;
        } else if (roll < 4){
            return baseDamage;
        } else if (roll < 6) {
            return baseDamage + 2;
        } else if (roll < 8) {
            return baseDamage + 5;
        } else {
            p.setBurning(true);
            p.setDebuffCounter(3);
            return baseDamage + 7;
        }
    }

    protected int draugrPoisonDamageAttack(Player p, Random rn){
        int roll = rn.nextInt(10) + 1;
        if (roll <= 1){
            return 0;
        } else if (roll < 4){
            return baseDamage;
        } else if (roll < 6) {
            return baseDamage + 2;
        } else if (roll < 8) {
            return baseDamage + 5;
        } else {
            p.setPoisoned(true);
            p.setDebuffCounter(3);
            return baseDamage + 7;
        }
    }

    protected int draugrNecroticDamageAttack(Player p, Random rn){
        int roll = rn.nextInt(10) + 1;
        if (roll <= 1){
            return 0;
        } else if (roll < 4){
            return baseDamage;
        } else if (roll < 6) {
            return baseDamage + 2;
        } else if (roll < 8) {
            return baseDamage + 5;
        } else {
            p.setNecrosis(true);
            p.setDebuffCounter(3);
            return baseDamage + 7;
        }
    }

    protected int draugrStandardDamageAttack(Random rn){
        int roll = rn.nextInt(10) + 1;
        if (roll == 1){
            return 0;
        } else if (roll < 4){
            return baseDamage;
        } else if (roll < 6) {
            return baseDamage + 1;
        } else if (roll < 8) {
            return baseDamage + 3;
        } else {
            return baseDamage + 10;
        }
    }

    public int draugrAttacks(Player p) {
        Random rn = new Random();
        String[] attacks = getAttacks();
        String attack = attacks[rn.nextInt(attacks.length)];
        if (attack.equals("Chop")){
            GameIO.reportAttack("Draugr", "Chop");
            return draugrFireDamageAttack(p, rn);
        } else if (attack.equals("Swing")){
            GameIO.reportAttack("Draugr", "Swing");
            return draugrFireDamageAttack(p, rn);
        } else {
            GameIO.reportAttack("Draugr", "Bash");
            return draugrStandardDamageAttack(rn);
        }
    }

    public int fireDemonAttacks(Player p) {
        Random rn = new Random();
        String[] attacks = getAttacks();
        String attack = attacks[rn.nextInt(attacks.length)];
        if (attack.equals("Spit")){
            return demonSpit(rn);
        } else if (attack.equals("Bite")){
            return demonBite(rn);
        } else {
            return demonBurn(rn, p);
        }
    }

    private int demonSpit(Random rn) {
        int roll = rn.nextInt(10) + 1;
        if (roll == 1){
            return 0;
        } else if (roll < 4){
            return baseDamage;
        } else if (roll < 6) {
            return baseDamage + 2;
        } else if (roll < 8) {
            return baseDamage + 5;
        } else {
            return baseDamage + 7;
        }
    }

    private int demonBite(Random rn) {
        int roll = rn.nextInt(10) + 1;
        if (roll == 1){
            return 0;
        } else if (roll < 4){
            return baseDamage;
        } else if (roll < 6) {
            return baseDamage + 2;
        } else if (roll < 8) {
            return baseDamage + 5;
        } else {
            return baseDamage + 7;
        }
    }

    private int demonBurn(Random rn, Player p) {
        int roll = rn.nextInt(10) + 1;
        if (roll == 1){
            return 0;
        } else if (roll < 4){
            return baseDamage;
        } else if (roll < 6) {
            return baseDamage + 2;
        } else if (roll < 8) {
            return baseDamage + 5;
        } else {
            p.setBurning(true);
            p.setDebuffCounter(3);
            return baseDamage + 7;
        }
    }

    public int smokestackAttacks() {
        Random rn = new Random();
        String[] attacks = getAttacks();
        String attack = attacks[rn.nextInt(attacks.length)];
        if (attack.equals("Spit")){
            return smokestackDamage(rn);
        } else if (attack.equals("Bite")){
            return smokestackDamage(rn);
        } else {
            return smokestackDamage(rn);
        }
    }

    protected int smokestackDamage(Random rn){
        int roll = rn.nextInt(10) + 1;
        if (roll == 1){
            return 0;
        } else if (roll < 4){
            return baseDamage;
        } else if (roll < 6) {
            return baseDamage + 2;
        } else if (roll < 8) {
            return baseDamage + 3;
        } else {
            return baseDamage + 4;
        }
    }

    protected int pDraugrAttacks(Player p){
        Random rn = new Random();
        String[] attacks = getAttacks();
        String attack = attacks[rn.nextInt(attacks.length)];
        if (attack.equals("Chop")){
            GameIO.reportAttack("Draugr", "Chop");
            return draugrPoisonDamageAttack(p, rn);
        } else if (attack.equals("Swing")){
            GameIO.reportAttack("Draugr", "Swing");
            return draugrPoisonDamageAttack(p, rn);
        } else {
            GameIO.reportAttack("Draugr", "Bash");
            return draugrStandardDamageAttack(rn);
        }
    }

    protected int nDraugrAttacks(Player p){
        Random rn = new Random();
        String[] attacks = getAttacks();
        String attack = attacks[rn.nextInt(attacks.length)];
        if (attack.equals("Chop")){
            GameIO.reportAttack("Draugr", "Chop");
            return draugrNecroticDamageAttack(p, rn);
        } else if (attack.equals("Swing")){
            GameIO.reportAttack("Draugr", "Swing");
            return draugrNecroticDamageAttack(p, rn);
        } else {
            GameIO.reportAttack("Draugr", "Bash");
            return draugrStandardDamageAttack(rn);
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



