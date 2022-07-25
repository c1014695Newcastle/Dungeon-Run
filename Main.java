public class Main {
    public static void main(String[] args) {
        Player p = new Player(GameIO.playerName());
        Map m = new Map();
        boolean legalMap = false;
        while (!legalMap){
            legalMap = m.checkMap(0,0);
            if (!legalMap){
                m.setRooms();
            }
        }
        System.out.println(m.toString(0));
        m.mapProgression(p);
    }
}
