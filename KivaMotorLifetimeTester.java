
/**
 * The motor on each Kiva robot is rated for 20,000 hours. 
 * After this point the robot needs to have its motor replaced or be retired. 
 * We want to track how many hours a Kiva robot's motor has been running and 
 * use a field called motorLifetime to store the motor lifetime in milliseconds. 
 * When a Kiva robot moves forward, turns left, or turns right, the motor lifetime 
 * increments by 1000 milliseconds (1 second).
 * 
 * 20000 h = 72000000000 ms
 * The long data type can store whole numbers from -9223372036854775808 to 9223372036854775807.
 * 
 * @author Karen Yang 
 * @version 10/16/2021
 */

import edu.duke.Point;


public class KivaMotorLifetimeTester {
    
    
    String defaultLayoutMotor = ""
                         +"-----\n"
                         +"|K D|\n"
                         +"| P |\n"
                         +"|* *|\n"
                         +"-----\n";
                                              
                         
    public void testMotorIncrement() {
        // GIVEN 
        // The default map we defined earlier 
        FloorMap defaultMapMotor = new FloorMap(defaultLayoutMotor);

        // WHEN 
        // We create a Kiva with the single-argument constructor         
        Kiva kiva = new Kiva(defaultLayoutMotor); 
        
        System.out.println(kiva.getMotorLifetime());// It should start at 0. Long integer
        kiva.move(KivaCommand.TURN_RIGHT);
        System.out.println(kiva.getMotorLifetime()); // It should be 1000. 
        kiva.move(KivaCommand.FORWARD);  
        System.out.println(kiva.getMotorLifetime()); // It should be 2000.
        kiva.move(KivaCommand.TURN_RIGHT); 
        System.out.println(kiva.getMotorLifetime()); // It should be 3000.
        kiva.move(KivaCommand.FORWARD); 
        System.out.println(kiva.getMotorLifetime()); // It should be 4000.
        kiva.move(KivaCommand.TAKE); 
        System.out.println(kiva.getMotorLifetime()); // It should be 4000.
        
       }
}
