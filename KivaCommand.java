
/**
 * KivaCommand describes the "moves" a Kiva robot can make: turning left or right, moving forwards, taking
 * a pod, and dropping a pod.  It also includes each move's one-letter abbreviation."
 * Commands will be sent to the robot's move method in Kiva Class.
 * 
 * source: https://w.amazon.com/bin/view/Amazon_Technical_Academy/Self_Service_Courses/Course4/JavaProgrammingPostDuke/project/KivaRobotRemoteControl
 *               
 * @author Karen Yang 
 * @version 10/16/2021
 */


public enum KivaCommand {
    // actions robot can perform
    FORWARD,
    TURN_LEFT, 
    TURN_RIGHT,
    TAKE,
    DROP;
    
    /**
     * Getter Method.
     * @return char the direction key letter associated with the enum value.
     */
    public char getDirectionKey() {
        
        switch (this) {
            
            case FORWARD:
                return 'F';
                
            case TURN_LEFT:
                return 'L';
                
            case TURN_RIGHT:
                return 'R';
                
            case TAKE:
                return 'T';
                
            case DROP:
                return 'D';
                
            default:
                throw new AssertionError("Unknown Key" + this);  // if an invalid key is present
            }
        }
        
    }
    

