import java.util.Random;

public class Room {
    private Enemy[] enemies;
    private int difficulty;
    private String type;


    public Room(int difficulty, String type) {
        this.difficulty = difficulty;
        this.type = type;
        this.enemies = generateEnemies(3, type, difficulty);
    }

    public Enemy[] getEnemies() {
        return enemies;
    }

    public void setEnemies(Enemy[] enemies) {
        this.enemies = enemies;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    private Enemy[] generateEnemies(int numOfEnemies, String type, int difficulty) {
        Random rn = new Random();
        Enemy enemies[] = {};
        for (int x = 0; x < numOfEnemies; x++) {
            if (type.equals("Flame")) {
                String name = Enemy.fireTypes[rn.nextInt(Enemy.fireTypes.length)];
                int level = rn.nextInt(5) +  1;
                Enemy enemy = new Enemy(level, level*10, level*10, level*5,  name);
                enemies[x] = enemy;
            }
        }
        return enemies;
    }
}
