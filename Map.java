import java.util.Random;

public class Map {
    private Room[][] rooms;

    public Room[][] getRooms() {
        return rooms;
    }

    public void setRooms(Room[][] rooms) {
        this.rooms = rooms;
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
                        Room r = new Room(0, "Fire");
                        rooms[x][y] = r;
                    } else if (randNum == 1){
                        Room r = new Room(4, "Fire");
                        rooms[x][y] = r;
                    } else {
                        Room r = new Room(1, "Fire");
                        rooms[x][y] = r;
                    }
                } else if (y == 2) {
                    if (randNum == 3 || randNum == 4){
                        Room r = new Room(0, "Poison");
                        rooms[x][y] = r;
                    } else if (randNum == 1){
                        Room r = new Room(4, "Fire");
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
                        Room r = new Room(4, "Fire");
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
        Map m = new Map();
        Room[][] r = m.getRooms();
        System.out.println(m);
        

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

