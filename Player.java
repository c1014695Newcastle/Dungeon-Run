import java.util.Arrays;
import java.util.Random;
import java.util.TreeMap;

public class Player {
    private int lives;
    private final String name;
    private int level;
    private int possibleHealth;
    private int health;
    private int chopDamage;

    private int swingDamage;

    private int castDamage;
    private int armour;
    private boolean poisoned;
    private boolean burning;
    private int debuffCounter;
    private boolean necrosis;
    private int xp;

    public Player(String name) {
        this.lives = 0;
        this.name = name;
        this.xp = 0;
        this.level = 1;
        this.possibleHealth = 100;
        this.health = 100;
        this.armour = 0;
        this.castDamage = 5;
        this.chopDamage = 25;
        this.swingDamage = 35;
        this.armour = 0;
        this.poisoned = false;
        this.burning = false;
        this.debuffCounter = 0;
        this.necrosis = false;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
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

    public int getHealth() {
        return health;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setPossibleHealth(int possibleHealth) {
        this.possibleHealth = possibleHealth;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getChopDamage() {
        return chopDamage;
    }

    public void setChopDamage(int chopDamage) {
        this.chopDamage = chopDamage;
    }

    public int getSwingDamage() {
        return swingDamage;
    }

    public void setSwingDamage(int swingDamage) {
        this.swingDamage = swingDamage;
    }

    public int getCastDamage() {
        return castDamage;
    }

    public void setCastDamage(int castDamage) {
        this.castDamage = castDamage;
    }

    public int getArmour() {
        return armour;
    }

    public void setArmour(int armour) {
        this.armour = armour;
    }

    public boolean isPoisoned() {
        return poisoned;
    }

    public void setPoisoned(boolean poisoned) {
        this.poisoned = poisoned;
    }

    public boolean isBurning() {
        return burning;
    }

    public void setBurning(boolean burning) {
        this.burning = burning;
    }

    public boolean isNecrosis() {
        return necrosis;
    }

    public void setNecrosis(boolean necrosis) {
        this.necrosis = necrosis;
    }

    public int getDebuffCounter() {
        return debuffCounter;
    }

    public void setDebuffCounter(int debuffCounter) {
        this.debuffCounter = debuffCounter;
    }

    public void removeDebuffs(){
        this.poisoned = false;
        this.burning = false;
        this.necrosis = false;
        this.debuffCounter = 0;
    }

    /**
     * Chop is an attack that focuses damage on one target only, a dice roll determines if the attack hits and whether the player does any bonus damage to the target.
     * @return the attack damage against the enemy
     */
    public int chop(){
        Random rn = new Random();
        int roll = GameIO.playerRoll(isPoisoned(), isBurning(), isNecrosis());
        return damageDecider(chopDamage, roll);
    }

    /**
     * Cast is an attack that can target two enemies at max, with three projectiles being aimed at the different enemies in the turn.
     * @return the attack damage of all three separate casts
     */
    public int[] cast(){
        Random rn = new Random();
        int roll;;
        int[] damage = {0,0,0};
        for (int x = 0; x < 3; x ++){
            roll = GameIO.playerRoll(isPoisoned(), isBurning(), isNecrosis());
            damage[x] = damageDecider(castDamage, roll);
        }
        return damage;
    }

    /**
     * Swing targets all enemies on the board with an attack that is done to all enemies equally, very powerful against large groups of enemies
     * @return the damage output of the swing
     */
    public int swing(){
        System.out.println("Not implemented yet");
        int roll = GameIO.playerRoll(isPoisoned(), isBurning(), isNecrosis());
        return damageDecider(swingDamage, roll);
    }

    private int damageDecider(int baseDamage, int roll){
        if (roll == 1){
            return 0;
        } else if (roll >= 2 && roll < 4){
            return baseDamage/2;
        } else if (roll >= 4 && roll < 6) {
            return baseDamage;
        } else if (roll >= 6 && roll < 8) {
            return baseDamage + (baseDamage * roll/10);
        } else if (roll >= 8) {
            return baseDamage * 2;
        }
        return 0;
    }

    public void checkDebuffs(){
        if (poisoned){
            setDebuffCounter(debuffCounter - 1);
        } else if (burning) {
            setDebuffCounter(debuffCounter - 1);
        } else if (necrosis) {
            setDebuffCounter(debuffCounter - 1);
        }
        if (debuffCounter == 0){
            removeDebuffs();
        }
    }

    public void debuffs(){
        if (burning){
            GameIO.burningDamage();
            setHealth(getHealth() - 5);
        }
    }

    protected void powerUp(){
        GameIO.reportStageClear();
        if (getHealth() + 50 > getPossibleHealth()){
            setHealth(getHealth());
        } else {
            setHealth(getHealth() + 50);
        }
    }


    public void levelUp(){
        int newLevel = (xp / 100) + 1;
        if (newLevel > level){
            GameIO.reportLevelUp(newLevel, newLevel - level);
            setHealth(getHealth() + ((newLevel - level) * 10));
            setPossibleHealth(getHealth());
            setCastDamage(getCastDamage() + ((newLevel - level) * 2));
            setChopDamage(getChopDamage() + ((newLevel - level) * 2));
            setSwingDamage(getSwingDamage() + ((newLevel - level) * 2));
            setLevel(newLevel);
        }
    }

    public void whetstone(){
        GameIO.reportWhetstone();
        chopDamage += 10;
        swingDamage += 10;
    }

    public void addArmour(int newArmour){
        GameIO.reportArmour(newArmour);
        armour += newArmour;
    }

    public void monsterHeart(){
        GameIO.reportMonsterHeart();
        possibleHealth += 25;
    }

    public void rune(){
        GameIO.reportRune();
        castDamage += 5;
    }

    protected void takeDamage(int damage) {
        if (getArmour() == 0) {
            GameIO.damageReport(damage, isPoisoned(), isBurning(), isBurning());
            setHealth(getHealth() - damage);
        } else if (getArmour() >= damage) {
            GameIO.armourHit((armour - damage), isPoisoned(), isBurning(), isBurning());
            setArmour(armour - damage);
        } else {
            GameIO.armourHit((damage - armour), isPoisoned(), isBurning(), isBurning());
            setHealth(getHealth() - (damage - armour));
            setArmour(0);
        }
    }


    @Override
    public String toString(){
        String healthBar = GameIO.playerHealthBar(health, possibleHealth);
        String topBar = GameIO.topBorder(healthBar.length());
        String bottomBar = GameIO.bottomBorder(healthBar.length());
        return  topBar + name + " Level " + level + "\n" +
                "  " + healthBar + "\n  " + health + "\\" + possibleHealth + "\n" + "  XP: " + xp + "\n" +
       bottomBar;
    }


}
