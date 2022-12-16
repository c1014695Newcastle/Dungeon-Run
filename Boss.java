import Enums.Difficulty;
import Enums.Types;

import java.util.ArrayList;
import java.util.Random;
import java.util.TreeMap;

/**
 * faf - Fafnir
 * fen - Fenrir
 * jor - Jormungandr
 * lok - Loki
 */

public class Boss {

    enum bossName {
        FAFNIR, JORMUNGANDR, FENRIR, LOKI
    }
    private final int level;
    private int possibleHealth;
    private int health;
    private int baseDamage;
    private final bossName name;
    private final int xp;
    private int phase;
    private final String[] lines;
    private final TreeMap<String, String> rewards;

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

    public bossName getName() {
        return name;
    }

    public int getXp() {
        return xp;
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

    public TreeMap<String, String> getRewards() {
        return rewards;
    }

    public Boss(int level, bossName name) {
        this.level = level;
        this.possibleHealth = 200 + (level * 50);
        this.health = possibleHealth;
        this.baseDamage = 10 + level * 5;
        this.name = name;
        this.xp = level * 50;
        this.phase = 1;
        this.lines = setLines(name);
        this.rewards = setRewards(name);
    }

    private String[] setLines(bossName name) {
        switch (name){
            case FAFNIR-> {
                return new String[]{"You shall not escape.", "You dare challenge me.", "You shall burn.", "None shall take my treasure", "He will have you."};
            }
            case FENRIR -> {
                return new String[]{"You shall not escape.", "You dare challenge me.", "You shall burn.", "None shall take my treasure", "He will have you."};
            }
            case JORMUNGANDR -> {
                return new String[]{"You shall not escape.", "You dare challenge me.", "You shall burn.", "None shall take my treasure", "He will have you."};
            }
        }
        return null;
    }

    private TreeMap<String, String> setRewards(bossName name){
        TreeMap<String, String> rewards = new TreeMap<>();
        switch (name) {
            case FAFNIR -> {
                rewards.put("Dragon Heart", "The calloused heart of an ancient beast, it teems with dark power and godlike strength.\n\t\t+50 Health");
                rewards.put("Dragon Scale", "The seasoned hide of a powerful dragon.\n\t\tStart each encounter with at least 25 armour");
                rewards.put("Dragon Claw", "A sharp instrument of war, plucked from Fafnir's corpse.\n\t\t+10% damage to all bladed attacks, +25% on critical roll attacks");
            }
            case JORMUNGANDR -> {
                rewards.put("Rune of Poison", "A rune that flashed into your head upon defeating your foe\n\t\t+10 damage to all rune attacks, deal bonus poison damage for 1 turn");
                rewards.put("Snake Cloak", "A cloak fashioned from the scales of the world serpent\n\t\t +20 to all armour");
                rewards.put("Snake Dagger", "A dagger fashioned from the fang of Jormungandr\n\t\t +25% damage to all bladed attacks");
            }
            case FENRIR -> {
                rewards.put("REWARD A", "+10 damage to all rune attacks, deal bonus poison damage for 1 turn");
                rewards.put("REWARD B", "");
                rewards.put("REWARD C", "");
            }
        }
        return rewards;
    }

    protected int fafPhaseOneAttack(Player p){
        Random rn = new Random();
        int roll = rn.nextInt(2) + 1;
        if (roll == 1){
            GameIO.reportAttack(name.toString(), "Bite");
            return standardDamage(rn);
        } else {
            GameIO.reportAttack( name.toString(), "Fire");
            return fireDamage(p, rn);
        }
    }

    protected int fafPhaseTwoAttack(Player p){
        Random rn = new Random();
        int roll = rn.nextInt(3) + 1;
        if (roll == 1){
            GameIO.reportAttack(name.toString(), "Bite");
            return standardDamage(rn);
        } else if (roll == 2) {
            GameIO.reportAttack( name.toString(), "Fire");
            return fireDamage(p, rn);
        } else {
            GameIO.reportAttack( name.toString(), "Dive");
            return standardDamage(rn) + 10;
        }
    }

    protected int fafPhaseThreeAttack(Player p){
        Random rn = new Random();
        int roll = rn.nextInt(4) + 1;
        if (roll == 1){
            GameIO.reportAttack(name.toString(), "Bite");
            return standardDamage(rn);
        } else if (roll == 2) {
            GameIO.reportAttack( name.toString(), "Fire");
            return fireDamage(p, rn);
        } else if (roll == 3){
            GameIO.reportAttack( name.toString(), "Dive");
            return standardDamage(rn) + 10;
        } else {
            GameIO.reportAttack(name.toString(), "Talon Swipe");
            return  fireDamage(p, rn) + 10;
        }
    }


    protected int fenPhaseOneAttack(Player p){
        Random rn = new Random();
        int roll = rn.nextInt(2) + 1;
        if (roll == 1){
            GameIO.reportAttack(name.toString(), "Bite");
            return standardDamage(rn);
        } else {
            GameIO.reportAttack( name.toString(), "Fire");
            return necroticDamage(p, rn);
        }
    }

    protected int fenPhaseTwoAttack(Player p){
        Random rn = new Random();
        int roll = rn.nextInt(3) + 1;
        if (roll == 1){
            GameIO.reportAttack(name.toString(), "Bite");
            return standardDamage(rn);
        } else if (roll == 2) {
            GameIO.reportAttack( name.toString(), "Fire");
            return necroticDamage(p, rn);
        } else {
            GameIO.reportAttack( name.toString(), "Dive");
            return standardDamage(rn) + 10;
        }
    }

    protected int fenPhaseThreeAttack(Player p){
        Random rn = new Random();
        int roll = rn.nextInt(4) + 1;
        if (roll == 1){
            GameIO.reportAttack(name.toString(), "Bite");
            return standardDamage(rn);
        } else if (roll == 2) {
            GameIO.reportAttack( name.toString(), "Fire");
            return necroticDamage(p, rn);
        } else if (roll == 3){
            GameIO.reportAttack( name.toString(), "Dive");
            return standardDamage(rn) + 10;
        } else {
            GameIO.reportAttack(name.toString(), "Talon Swipe");
            return  necroticDamage(p, rn) + 10;
        }
    }

    private int necroticDamage(Player p, Random rn){
        int roll = rn.nextInt(6) + 1;
        if (roll < 2){
            return 0;
        } else if (roll < 4){
            p.setNecrosis(true);
            p.setDebuffCounter(3);
            return baseDamage;
        } else {
            p.setNecrosis(true);
            p.setDebuffCounter(5);
            return baseDamage * 2;
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
        Boss d = new Boss(1, bossName.FAFNIR);
        Player p = new Player("Luke");
        BossRoom b = new BossRoom(1, Difficulty.LOW, Types.FIRE, d);
        b.bossEncounter(p);
    }

    @Override
    public String toString(){
        String healthBar = GameIO.playerHealthBar(health, possibleHealth);
        String topBar = GameIO.topBorder(healthBar.length() + 12);
        String bottomBar = GameIO.bottomBorder(healthBar.length() + 12);
        return topBar +
                "\t" + name.toString().replace("_"," ") + "\n"  + "\tHealth: " + healthBar + "\n    "+ health + "\\" + possibleHealth + "\n"  +bottomBar;
    }

}
