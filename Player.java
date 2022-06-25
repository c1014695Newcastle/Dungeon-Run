import java.util.Random;

public class Player {
    private int level;
    private int possibleHealth;
    private int health;
    private int chopDamage;

    private int swingDamage;

    private int castDamage;
    private int armour;
    private boolean poisoned;
    private boolean burning;
    private boolean necrosis;

    public Player() {
        this.level = 1;
        this.possibleHealth = 50;
        this.health = 50;
        this.armour = 0;
        this.castDamage = 5;
        this.chopDamage = 15;
        this.swingDamage = 25;
    }

    public int getLevel() {
        return level;
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

    /**
     * Chop is an attack that focuses damage on one target only, a dice roll determines if the attack hits and whether the player does any bonus damage to the target.
     * @return the attack damage against the enemy
     */
    public int chop(){
        Random rn = new Random();
        int roll = rn.nextInt(10) + 1;
        if (roll == 1){
            return 0;
        } else if (roll >= 2 && roll < 4){
            return this.chopDamage/2;
        } else if (roll >= 4 && roll < 6) {
            return this.chopDamage;
        } else if (roll >= 6 && roll < 8) {
            return this.chopDamage + (this.chopDamage * roll/10);
        } else if (roll >= 8) {
            return this.chopDamage * 2;
        }
        return 0;
    }

    /**
     * Cast is an attack that can target two enemies at max, with three projectiles being aimed at the different enemies in the turn.
     * @return the attack damage of all three separate casts
     */
    public int[] cast(){
        Random rn = new Random();
        int roll;
        int[] damage = {0,0,0};
        for (int x : damage){
            roll = 1;
            if (roll == 1){
                damage[x] = 0;
            } else if (roll >= 2 && roll < 4){
                damage[x] = this.castDamage/2;
            } else if (roll >= 4 && roll < 6) {
                damage[x] = this.castDamage;
            } else if (roll >= 6 && roll < 8) {
                damage[x] = this.castDamage + (this.castDamage * roll/10);
            } else if (roll >= 8) {
                damage[x] = this.castDamage * 2;
            }
        }
        return damage;
    }

    /**
     * Swing targets all enemies on the board with an attack that is done to all enemies equally, very powerful against large groups of enemies
     * @return the damage output of the swing
     */
    public int swing(){
        System.out.println("Not implemented yet");
        return 0;
    }


}
