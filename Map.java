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
                    if (x == 0 || y == 0){
                        Room r = new Room(ID, 1, Types.FIRE, 3);
                        rooms[x][y] = r;
                        ID++;
                    } else {
                        if (randNum == 3 || randNum == 4) {
                            Room r = new Room(ID, 0, Types.FIRE, 0);
                            rooms[x][y] = r;
                            ID++;
                        } else if (randNum == 1) {
                            Room r = new Room(ID, 4, Types.FIRE, 0);
                            rooms[x][y] = r;
                            ID++;
                        } else {
                            Room r = new Room(ID, 1, Types.FIRE, 3);
                            rooms[x][y] = r;
                            ID++;
                        }
                    }
                } else if (y == 2) {
                    if (randNum == 3 || randNum == 4) {
                        Room r = new Room(ID, 0, Types.POISON, 0);
                        rooms[x][y] = r;
                        ID++;
                    } else if (randNum == 1) {
                        Room r = new Room(ID, 4, Types.POISON, 0);
                        rooms[x][y] = r;
                        ID++;
                    } else {
                        Room r = new Room(ID, 2, Types.POISON, 4);
                        rooms[x][y] = r;
                        ID++;
                    }
                } else {
                    if (randNum == 3 || randNum == 4) {
                        Room r = new Room(ID, 0, Types.NECROTIC, 0);
                        rooms[x][y] = r;
                        ID++;
                    } else if (randNum == 1) {
                        Room r = new Room(ID, 4, Types.NECROTIC, 0);
                        rooms[x][y] = r;
                        ID++;
                    } else {
                        Room r = new Room(ID, 3, Types.NECROTIC, 5);
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
        for (int x = 0; x <100; x++) {
            System.out.println("\n\n");
            Map m = new Map();
            System.out.println(m);
            Room[][] r = m.getRooms();
            System.out.println(m.checkMap(0, 0));
            System.out.println(m.checkValidRooms(m.getRooms()[0][0]));
        }
    }

    private boolean isLegal(int x, int y){
        return (x <=4 && x >=0) && (y <=4 && y >=0);
    }

    /**]
     * Recursive algorithm to make sure that the map the player is about to play actually has a valid path to the end. Checks the x and y coordinates of the room to see if there are any valid rooms around the calling room.
     * @param x the x coordinate of the room calling the method
     * @param y the y coordinate of the room calling the method
     * @return true if there is a valid path present from the start, otherwise false
     */
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

    /**
     * Finds the x coordinate of the room to see if the player has reached the end boundary at x = 4
     * @param r the room the player has selected
     * @return the x coordinate of the room
     */
    private int checkRoom(Room r){
        for (int x = 0; x < 4; x++){
            for (int y = 0; y < 4; y++){
                if (rooms[x][y].getID() == r.getID()){
                    return x;
                }
            }
        }
        return 0;
    }

   public void mapProgression(Player p){
        int x = 0, y = 0;
        Room r = rooms[x][y];
        while (x != 4 && p.getHealth() > 0){
            r.startEncounter(p);
            if (p.getHealth() <= 0){
                break;
            } else {
                System.out.println("ENCOUNTER CLEARED");
                r.endEncounter(p);
                x = checkRoom(r);
                Types roomtype = r.getType();
                Room[] validRooms = checkValidRooms(r);
                r = GameIO.pickRoom(validRooms);
                if (roomtype != r.getType()) {
                    BossRoom b = new BossRoom(1, 1, Types.FIRE, new Boss(p.getLevel(), "Fafnir"));
                    b.startEncounter(p);
                }
            }
        }
   }

    /**
     * Checks if a room is valid for the player to move into from their current position, must be 1 x or y coordinate move from their current room position relative to the map
     * @param r the room the player is in
     * @return the list of valid rooms
     */
   private Room[] checkValidRooms(Room r) {
       Room[] validRooms = new Room[4];
       for (int x = 0; x < 4; x++) {
           for (int y = 0; y < 4; y++) {
               if (r.getID() == rooms[x][y].getID()) {
                   if (isLegal(x + 1, y)) {
                       //Right
                       validRooms[0] = rooms[x + 1][y];
                   }
                   if (isLegal(x - 1, y)) {
                       //Left
                       validRooms[1] = rooms[x - 1][y];
                   }
                   if (isLegal(x, y + 1)) {
                       //Up
                       validRooms[2] = rooms[x][y + 1];
                   }
                   if (isLegal(x, y - 1)) {
                       //Down
                       validRooms[3] = rooms[x][y - 1];
                   }
               }
           }
       }
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

