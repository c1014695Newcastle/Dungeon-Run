import Enums.Difficulty;
import Enums.Types;

import java.text.SimpleDateFormat;
import java.util.Arrays;
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
                        Room r = new Room(ID, Difficulty.LOW, Types.FIRE, 3);
                        rooms[x][y] = r;
                        ID++;
                    } else {
                        if (randNum == 3 || randNum == 4) {
                            Room r = new Room(ID, Difficulty.EMPTY, Types.FIRE, 0);
                            rooms[x][y] = r;
                            ID++;
                        } else if (randNum == 1) {
                            Room r = new Room(ID, Difficulty.BLOCKED, Types.FIRE, 0);
                            rooms[x][y] = r;
                            ID++;
                        } else {
                            Room r = new Room(ID, Difficulty.LOW, Types.FIRE, 3);
                            rooms[x][y] = r;
                            ID++;
                        }
                    }
                } else if (y == 2) {
                    if (randNum == 3 || randNum == 4) {
                        Room r = new Room(ID, Difficulty.EMPTY, Types.POISON, 0);
                        rooms[x][y] = r;
                        ID++;
                    } else if (randNum == 1) {
                        Room r = new Room(ID, Difficulty.BLOCKED, Types.POISON, 0);
                        rooms[x][y] = r;
                        ID++;
                    } else {
                        Room r = new Room(ID, Difficulty.MEDIUM, Types.POISON, 4);
                        rooms[x][y] = r;
                        ID++;
                    }
                } else {
                    if (randNum == 3 || randNum == 4) {
                        Room r = new Room(ID, Difficulty.EMPTY, Types.NECROTIC, 0);
                        rooms[x][y] = r;
                        ID++;
                    } else if (randNum == 1) {
                        Room r = new Room(ID, Difficulty.BLOCKED, Types.NECROTIC, 0);
                        rooms[x][y] = r;
                        ID++;
                    } else {
                        Room r = new Room(ID, Difficulty.HARD, Types.NECROTIC, 5);
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
            System.out.println(m.toString(m.rooms[1][1].getID()));
            System.out.println(m.checkMap(0, 0));
            System.out.println(Arrays.toString(m.checkValidRooms(m.getRooms()[0][0])));
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
                } else if (isLegal(x + 1, y) && rooms[x + 1][y].getDifficulty() != Difficulty.BLOCKED) {
                    path = checkMap(x + 1, y);
                } else if (isLegal(x - 1, y) && rooms[x - 1][y].getDifficulty() != Difficulty.BLOCKED) {
                    path = checkMap(x + 1, y);
                } else if (isLegal(x, y + 1) && rooms[x][y + 1].getDifficulty() != Difficulty.BLOCKED) {
                    path = checkMap(x + 1, y);
                } else if (isLegal(x, y - 1) && rooms[x][y - 1].getDifficulty() != Difficulty.BLOCKED) {
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
                System.out.println(x);
                System.out.println("ENCOUNTER CLEARED");
                r.endEncounter(p);
                r.setVisited(true);
                x = checkRoom(r);
                Types roomtype = r.getType();
                System.out.println(this.toString(r.getID()));
                Room[] validRooms = checkValidRooms(r);
                r = GameIO.pickRoom(validRooms);
                if (roomtype != r.getType()) {
                    switch (roomtype){
                        case FIRE -> {
                            BossRoom b = new BossRoom(1, Difficulty.LOW, Types.FIRE, new Boss(p.getLevel(), Boss.bossName.FAFNIR));
                            b.bossEncounter(p);
                        }
                        case NECROTIC -> {
                            BossRoom b = new BossRoom(1, Difficulty.LOW, Types.FIRE, new Boss(p.getLevel(), Boss.bossName.FENRIR));
                            b.bossEncounter(p);
                        }
                        case POISON -> {
                            BossRoom b = new BossRoom(1, Difficulty.LOW, Types.FIRE, new Boss(p.getLevel(), Boss.bossName.JORMUNGANDR));
                            b.bossEncounter(p);
                        }
                    }
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
                   if (isLegal(x + 1, y) && (rooms[x+1][y].getDifficulty() != Difficulty.BLOCKED)) {
                       //Right
                       validRooms[0] = rooms[x + 1][y];
                   }
                   if (isLegal(x - 1, y)&& (rooms[x-1][y].getDifficulty() != Difficulty.BLOCKED)) {
                       //Left
                       validRooms[1] = rooms[x - 1][y];
                   }
                   if (isLegal(x, y + 1)&& (rooms[x][y+1].getDifficulty() != Difficulty.BLOCKED)) {
                       //Up
                       validRooms[2] = rooms[x][y + 1];
                   }
                   if (isLegal(x, y - 1)&& (rooms[x][y-1].getDifficulty() != Difficulty.BLOCKED)) {
                       //Down
                       validRooms[3] = rooms[x][y - 1];
                   }
               }
           }
       }
       return validRooms;
   }


    public String toString(int id) {
       int player = 0;
        StringBuilder s = new StringBuilder("+---------------------+\n");
            for (int k = 0; k < 5; k++) {
                for (int j = 0; j < 5; j++) {
                    if (j == 0) {
                        if (id == player) {
                            s.append("| " + "[x]" + " ");
                            player++;
                        } else {
                            s.append("| " + "[ ]" + " ");
                            player++;
                        }
                    }
                    if (j == 4) {
                        if (id == player) {
                            s.append("[x]" + " |\n");
                            player++;
                        } else {
                            s.append("[ ]" + " |\n");
                            player++;
                        }
                    }
                    if (j != 0 && j != 4) {
                        if (id == player) {
                            s.append("[x]" + " ");
                            player++;
                        } else {
                            s.append("[ ]" + " ");
                            player++;
                        }
                    }
                }
            }
        s.append("+---------------------+");
        return s.toString();
    }
}

