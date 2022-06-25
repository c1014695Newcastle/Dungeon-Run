import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Room {
    private ArrayList<Enemy> enemies;
    private int difficulty;
    private String type;


    public Room(int difficulty, String type) {
        this.difficulty = difficulty;
        this.type = type;
        this.enemies = generateEnemies(3, type, difficulty);
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(ArrayList<Enemy> enemies) {
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


    private ArrayList<Enemy> generateEnemies(int numOfEnemies, String type, int difficulty) {
        Random rn = new Random();
        ArrayList<Enemy> enemies = new ArrayList<>();
        for (int x = 0; x < numOfEnemies; x++) {
            if (type.equals("Flame")) {
                String name = Enemy.fireTypes[rn.nextInt(Enemy.fireTypes.length)];
                int level = rn.nextInt(5) + 1;
                if (name.equals("Draugr")) {
                    Enemy enemy = new Enemy(x + 1, level, level * 10, level * 10, level * 5, name);
                    enemies.add(enemy);
                } else if (name.equals("Fire Demon")){
                    Enemy enemy = new Enemy(x + 1, level, level * 5, level * 5, level * 10, name);
                    enemies.add(enemy);
                } else {
                    Enemy enemy = new Enemy(x + 1, level, level * 10, level * 10, level * 10, name);
                    enemies.add(enemy);
                }
            }
        }
        return enemies;
    }

    public static void main(String[] args) {
        Room roomOne = new Room(1, "Flame");
        for (Enemy e : roomOne.getEnemies()){
            System.out.println(e.getName());
        }
    }
}
