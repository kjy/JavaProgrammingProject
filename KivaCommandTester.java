
/**
 * The KivaCommandTester runs a series of tests to print out Kiva Commands as enum values, such as "FORWARD", 
 * along with its associated character value, known as a direction key, such as "F". These commands are used
 * as inputs to give instructions to the Kiva robot as to what moves to make.
 * 
 * @author Karen Yang
 * @version 10/16/2021
 */

public class KivaCommandTester {
    
    
    public void testForward() {
        KivaCommand command = KivaCommand.FORWARD;
        System.out.println(command);
        System.out.println(command.getDirectionKey());
    }

    
    public void testTurnLeft() {
        KivaCommand command = KivaCommand.TURN_LEFT;
        System.out.println(command);
        System.out.println(command.getDirectionKey());
    }
    
    
    public void testTurnRight() {
        KivaCommand command = KivaCommand.TURN_RIGHT;
        System.out.println(command);
        System.out.println(command.getDirectionKey());
    }
    
    
    public void testTake() {
        KivaCommand command = KivaCommand.TAKE;
        System.out.println(command);
        System.out.println(command.getDirectionKey());
    }
    
    
    public void testDrop() {
        KivaCommand command = KivaCommand.DROP;
        System.out.println(command);
        System.out.println(command.getDirectionKey());
    }

}
