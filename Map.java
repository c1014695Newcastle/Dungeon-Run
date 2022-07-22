import Enums.Types;

import java.text.SimpleDateFormat;
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
        int ID = 0;
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                int randNum = ran.nextInt(4) + 1;
                if (y == 0 || y == 1) {
                    if (randNum == 3 || randNum == 4) {
                        Room r = new Room(ID, 0, Types.FIRE);
                        rooms[x][y] = r;
                        ID++;
                    } else if (randNum == 1) {
                        Room r = new Room(ID, 4, Types.FIRE);
                        rooms[x][y] = r;
                        ID++;
                    } else {
                        Room r = new Room(ID, 1, Types.FIRE);
                        rooms[x][y] = r;
                        ID++;
                    }
                } else if (y == 2) {
                    if (randNum == 3 || randNum == 4) {
                        Room r = new Room(ID, 0, Types.POISON);
                        rooms[x][y] = r;
                        ID++;
                    } else if (randNum == 1) {
                        Room r = new Room(ID, 4, Types.POISON);
                        rooms[x][y] = r;
                        ID++;
                    } else {
                        Room r = new Room(ID, 2, Types.POISON);
                        rooms[x][y] = r;
                        ID++;
                    }
                } else {
                    if (randNum == 3 || randNum == 4) {
                        Room r = new Room(ID, 0, Types.NECROTIC);
                        rooms[x][y] = r;
                        ID++;
                    } else if (randNum == 1) {
                        Room r = new Room(ID, 4, Types.NECROTIC);
                        rooms[x][y] = r;
                        ID++;
                    } else {
                        Room r = new Room(ID, 3, Types.NECROTIC);
                        rooms[x][y] = r;
                        ID++;
                    }
                }
            }
        }
        return rooms;
    }

    public static void main(String[] args) {
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd - HH:mm.ss").format(Calendar.getInstance().getTime());
        System.out.println("MAP CHECK TESTS " + timeStamp);
        System.out.println("\n\n");
        Map m = new Map();
        System.out.println(m);
        Room[][] r = m.getRooms();
        System.out.println(m.checkMap(0,0));
        System.out.println(m.checkValidRooms(m.getRooms()[0][0]));
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

   public void mapProgression(Player p){
        int x = 0, y = 0;
        while (x != 4){
            Room r = getRooms()[x][y];
            r.startEncounter(p);
            Room[] validRooms = checkValidRooms(r);
        }
   }

   private Room[] checkValidRooms(Room r) {
        int ID = 0;
       Room[] validRooms = new Room[4];
       for (int x = 0; x < 4; x++) {
           for (int y = 0; y < 4; y++) {
               if (r.getID() == rooms[x][y].getID()) {
                   if (isLegal(x + 1, y)) {
                       validRooms[ID] = rooms[x + 1][y];
                       ID++;
                   } else if (isLegal(x - 1, y)) {
                       validRooms[ID] = rooms[x - 1][y];
                       ID++;
                   } else if (isLegal(x, y + 1)) {
                       validRooms[ID] = rooms[x][y + 1];
                       ID++;
                   } else if (isLegal(x, y - 1)) {
                       validRooms[ID] = rooms[x][y - 1];
                       ID++;
                   }
               }
           }
       }
       System.out.println(validRooms);
       return validRooms;
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

