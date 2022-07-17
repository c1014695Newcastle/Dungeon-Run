import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class Map {
    private Room[][] rooms;

    public Room[][] getRooms() {
        return rooms;
    }

    public void setRooms() {
        this.rooms = generateMap();
    }

    public Map(){
        rooms = generateMap();
    }

    private Room[][] generateMap(){
        Random ran = new Random();
        Room[][] rooms = new Room[5][5];
        for (int x = 0; x < 5; x++){
            for (int y = 0; y < 5; y++){
                int randNum = ran.nextInt(4) + 1;
                if (y == 0 || y == 1) {
                    if (randNum == 3 || randNum == 4){
                        Room r = new Room(0, "Flame");
                        rooms[x][y] = r;
                    } else if (randNum == 1){
                        Room r = new Room(4, "Flame");
                        rooms[x][y] = r;
                    } else {
                        Room r = new Room(1, "Flame");
                        rooms[x][y] = r;
                    }
                } else if (y == 2) {
                    if (randNum == 3 || randNum == 4){
                        Room r = new Room(0, "Poison");
                        rooms[x][y] = r;
                    } else if (randNum == 1){
                        Room r = new Room(4, "Poison");
                        rooms[x][y] = r;
                    } else {
                        Room r = new Room(2, "Poison");
                        rooms[x][y] = r;
                    }
                } else {
                    if (randNum == 3 || randNum == 4){
                        Room r = new Room(0, "Necrosis");
                        rooms[x][y] = r;
                    } else if (randNum == 1){
                        Room r = new Room(4, "Necrosis");
                        rooms[x][y] = r;
                    } else {
                        Room r = new Room(3, "Necrosis");
                        rooms[x][y] = r;
                    }
                }

            }
        }
        return rooms;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i ++) {
            String timeStamp = new SimpleDateFormat("yyyy/MM/dd - HH:mm.ss").format(Calendar.getInstance().getTime());
            System.out.println("MAP CHECK TESTS " + timeStamp);
            System.out.println(i + "\n\n");
            Map m = new Map();
            System.out.println(m);
            Room[][] r = m.getRooms();
            System.out.println(m.checkMap(0,0));
        }
    }

    private boolean isLegal(int x, int y){
        return (x <=4 && x >=0) && (y <=4 && y >=0);
    }

    public boolean checkMap(int x, int y){
        boolean path = false;
        while (!path) {
                if (x == 4) {
                    return true;
                } else if (isLegal(x + 1, y) && rooms[x + 1][y].getDifficulty() != 4) {
                    path = checkMap(x + 1, y);
                } else if (isLegal(x - 1, y) && rooms[x - 1][y].getDifficulty() != 4) {
                    path = checkMap(x + 1, y);
                } else if (isLegal(x, y + 1) && rooms[x][y + 1].getDifficulty() != 4) {
                    path = checkMap(x + 1, y);
                } else if (isLegal(x, y - 1) && rooms[x][y - 1].getDifficulty() != 4) {
                    path = checkMap(x + 1, y);
                } else {
                    return false;
                }
        }
        return true;
    }


    public String toString() {
        String s = "+--------------------------------------------------------------------------------------+\n";
            for (int k = 0; k < 5; k++) {
                for (int j = 0; j < 5; j++) {
                    if (j == 0) {
                        s += "| " + rooms[k][j] + " ";
                    }
                    if (j == 4) {
                        s += rooms[k][j] + " |\n";
                    }
                    if (j != 0 && j != 4) {
                        s += rooms[k][j] + " ";
                    }
                }
            }
        s += "+--------------------------------------------------------------------------------------+";
        return s;
    }
}

