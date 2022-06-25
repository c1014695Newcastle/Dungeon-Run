import java.sql.Time;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class GameIO {
    public static int playerRoll(boolean poisoned, boolean necrosis, boolean burned ){
        System.out.println("Press enter to roll the dice");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        Random rn = new Random();
        int rollOne = rn.nextInt(10) + 1;
        int rollTwo = rn.nextInt(10) + 1;
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.format("""
                
                You Rolled:
                
                ╔═══════════════╗ ╔═══════════════╗
                        %d                %d
                ╚═══════════════╝ ╚═══════════════╝ 
       
                 """, rollOne,rollTwo);
        scanner.close();
        // Check for conditions to affect dice roll;
        if (poisoned){
            System.out.println("POISON DEBUFF - TAKE LOWER ROLL");
            if (rollOne < rollTwo){
                return rollOne;
            } else {
                return rollTwo;
            }
        }
        return rollOne;
    }

    public static void main(String[] args) {
        playerRoll(true, false, false);
    }
}
