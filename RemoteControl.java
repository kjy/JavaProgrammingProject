import edu.duke.FileResource;

/**
 * RemoteControl class initializes a new keyboard resource and executes the run method that makes the robot move on the map to 
 * deliver the pod while avoiding obstacles. To start, the user selects a map and then inputs a string of commands for the 
 * Kiva robot to follow. The  The method convertToKivaCommands() takes the string of commands and converts it to 
 * string representation of Kiva commands of enum values.  The goal is for the robot is to move
 * to the pod location to pick it up and then to carry it to the drop zone location where the pod
 * should be successfully dropped. The robot will have to move in such a way to avoid illegal moves that
 * are out of boundary, that avoids obstacles, and that are appropriate such as moving onto empty spaces.
 * 
 * source: https://w.amazon.com/bin/view/Amazon_Technical_Academy/Self_Service_Courses/Course4/JavaProgrammingPostDuke/project/KivaRobotRemoteControl
 * 
 */

import edu.duke.*;
import edu.duke.Point; 
import java.util.Arrays;
import java.util.HashMap;

public class RemoteControl {
    KeyboardResource keyboardResource;
    

    /**
     * Build a new RemoteControl.
     */
    public RemoteControl() {
        keyboardResource = new KeyboardResource();
    }
    
    
    /**
    * Method to convert multiple Kiva Commands in a String into the enum names into String
    * @param  String     "FFFTRF"
    * @return  Array of Strings  [FORWARD, FORWARD, FORWARD, TAKE, TURN_RIGHT, FORWARD]
    * @IllegalArgumentException called when character key is not one of the options available
    */
    public String[] convertToKivaCommands(String strCommands) {
        // Use hash map to put key-value pair for example "F":"FORWARD"
        HashMap<String, String> dictKeyCommand = new HashMap <String, String> ();
        // iterate over array of Kiva commands, use .values()
        for(KivaCommand k : KivaCommand.values()) {
            dictKeyCommand.put(Character.toString(k.getDirectionKey()), k.name()); 
        }
         
        //System.out.println(dictKeyCommand);
        String [] result = strCommands.split("");  
        //System.out.println(Arrays.toString(result));
        int size = result.length;
        // initialize array with a size
        String[] commands = new String[size]; 
        for (int i = 0; i <= result.length-1; i++) {
            for (int j = 0; j <= dictKeyCommand.size(); j++) {
                if (dictKeyCommand.containsKey(result[i])){
                    commands[i] = dictKeyCommand.get(result[i]);
                    break;
                }
                else if (!dictKeyCommand.containsKey(result[i])){
                    // continue;   skip iteration if key not found, like "B" 
                    //System.out.println(result[i]);
                    throw new IllegalArgumentException("Character " + result[i] + " does not correspond to a command!");
                }
            }
        }
        //System.out.println(Arrays.toString(commands));
        return commands;
    }

    /**
     * The controller that directs Kiva's activity. Prompts the user for the floor map
     * to load, displays the map, and asks the user for the commands for Kiva to execute.
     *
     */
    public void run() {
        System.out.println("Please select a map file.");
        //FileResource fileResource = null;  
        FileResource fileResource = new FileResource();
        String inputMap = fileResource.asString();
        
        FloorMap floorMap = new FloorMap(inputMap);
        System.out.println(floorMap);
        
        FacingDirection direction = FacingDirection.UP;
        
        Point currentLocation = floorMap.getInitialKivaLocation();
        System.out.println("Current Kiva Robot location:  " + currentLocation);
        
        Point podLocation = floorMap.getPodLocation();
        //System.out.println("Current pod location:  " + podLocation);
        
        Point dropLocation = floorMap.getDropZoneLocation();
        //System.out.println("Current drop location:  " + dropLocation);
        
        System.out.println("Facing: UP");
        
        Kiva kiva = new Kiva(inputMap,currentLocation); 

        System.out.println("Please enter the directions for the Kiva Robot to take.");
        String directions = keyboardResource.getLine();
        System.out.println("Directions that you typed in: " + directions);
        
        String[] commands = this.convertToKivaCommands(directions);
        
        for (int i=0; i < commands.length; i++) { 
            KivaCommand enumCommand = KivaCommand.valueOf(commands[i]);
            kiva.move(enumCommand);
            //System.out.println(kiva.getCurrentLocation());
        }
        
        if (kiva.isSuccessfullyDropped() == true && commands[commands.length - 1].equals("DROP")) {
            System.out.println("Successfully picked up the pod and dropped it off. Thank you!"); 
        }
        else {System.out.println("I'm sorry. The Kiva Robot did not pick up the pod and then drop it off in the right place."); 
        }
            
    }
    
}
/**
// Execute the run() method with the following inputs. Validate that you see the expected output.

// Inputs: Floor map file: sample_floor_map1.txt, Command string: “RFFFFFTFFFFFFFD”  TEST PASS
// Output: “Successfully picked up the pod and dropped it off. Thank you!” 

// Inputs: Floor map file: sample_floor_map2.txt, Command string: “RFFFFFFLFFFTRFFRFFFD”  TEST PASS
// Output: “Successfully picked up the pod and dropped it off. Thank you!”

// Inputs: Floor map file: sample_floor_map3.txt, Command string: “RRFFFLFFFFLFFFRFTFRFFFLFFFFFFFFFFLFFFD”  TEST PASS
// Output: “Successfully picked up the pod and dropped it off. Thank you!”

// Inputs: Floor map file: sample_floor_map2.txt, Command string: “RFFFFFFLFFFTRFFRFFFDR”  
// Output: “I'm sorry. The Kiva Robot did not pick up the pod then drop it off in the right place.”          TEST PASS
// (Notice these are the same commands as the successful test case above except you turn after dropping the pod.
//  If dropping the pod in the right place is not the last command of your Kiva then this is a test case failure).

// Inputs: Floor map file: sample_floor_map3.txt, Command string: “R"
// Output: “I'm sorry. The Kiva Robot did not pick up the pod then drop it off in the right place.”   TEST PASS

// Inputs: Floor map file: sample_floor_map3.txt, Command string: “RRFFFLFFFFLFFFRFT"
// Output: “I'm sorry. The Kiva Robot did not pick up the pod then drop it off in the right place.”   TEST PASS

// Inputs: Floor map file: sample_floor_map2.txt, Command string: “RFFFFFLFFFTRFFRFFFD”
// Output: “NoPodException: Can't take nonexistent pod from location (8,1)!”              TEST PASS

// Inputs: Floor map file: sample_floor_map2.txt, Command string: “RFFFFFFLFFFTRFFRFFD”
// Output: “IllegalDropZoneException: Can't just drop pods willy-nilly at (11,3)!”           TEST PASS     

// Inputs: Floor map file: sample_floor_map1.txt, Command string: “F”
// Output: “IllegalMoveException: Can't move onto an obstacle at (1,0)!”    TEST PASS
*/
