public class Main {
    public static void main(String[] args) {
        Player p = new Player();
        Map m = new Map();
        boolean legalMap = false;
        while (!legalMap){
            legalMap = m.checkMap(0,0);
            System.out.println(legalMap);
            if (!legalMap){
                m.setRooms();
            }
        }
        System.out.println(m);
        Room r = m.getRooms()[0][0];
        r.startEncounter(p);
    }
}
