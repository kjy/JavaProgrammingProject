
/**
 * RemoteControlTest runs a test to convert a string value, which has several Kiva commands as characters such as "FFFTRFD", to string elements of enum values 
 * of such commands such as [FORWARD, FORWARD, FORWARD, TAKE, TURN_RIGHT, FORWARD].  The method .converToKivaCommands() is used to make the conversion.  
 * A failure of conversion results in an illegal argument exception.
 * 
 * @author Karen Yang 
 * @version 10/16/2021
 */


public class RemoteControlTest {
    public void testConvertCommands() {
    RemoteControl remoteControl = new RemoteControl();
    //remoteControl.convertToKivaCommands("FFFTRFD");  // test 1 output: [FORWARD, FORWARD, FORWARD, TAKE, TURN_RIGHT, FORWARD]
    remoteControl.convertToKivaCommands("B");      // test 2 output: IllegalArgumentException
    }
    
    
    
    
}
