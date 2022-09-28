
/**
 * KivaMoveTest tests code that instruct the Kiva robot to move and to have certain states such as whether it is carrying a pod
 * or not, if it has dropped a pod or not, and its facing direction, given left or right turns. It also includes some exceptions tests that
 * check whether a robot's location is valid or not, depending upon whether it's within the boundaries of a map, whether the location has
 * an obstacle or not, and whether the robot is at a correct location in order to take or drop a pod.
 * 
 * source: https://w.amazon.com/bin/view/Amazon_Technical_Academy/Self_Service_Courses/Course4/JavaProgrammingPostDuke/project/KivaRobotRemoteControl
 * 
 * @author Karen Yang 
 * @version 10/16/2021
 */
import edu.duke.Point;

public class KivaMoveTest {
    // Define the FloorMap we'll use for all the tests
    String defaultLayout = ""
                           + "-------------\n"
                           + "        P   *\n"
                           + "   **       *\n"
                           + "   **       *\n"
                           + "  K       D *\n"
                           + " * * * * * **\n"
                           + "-------------\n";

    FloorMap defaultMap = new FloorMap(defaultLayout);

    public void testForwardFromUp() {
        // GIVEN
        // A Kiva built with the default map we defined earlier
        FloorMap defaultMap = new FloorMap(defaultLayout);
        //Point actualLocation = defaultMap.getInitialKivaLocation(); // initial location is Point (2,4)
        Kiva kiva = new Kiva(defaultLayout);
        // WHEN
        // We move one space forward 
        kiva.move(KivaCommand.FORWARD); // Point (2,3) Moving southbound, down the y-axis
        // THEN
        // The Kiva has moved one space up, expected location is Point(2,3)
        verifyKivaState("testForwardFromUp", kiva, new Point(2, 3), FacingDirection.UP, false, false);
    }
    
    // For you: create all the other tests and call verifyKivaState() for each
    public void testLeftFromUp() {
        // GIVEN
        // A Kiva built with the default map we defined earlier
        FloorMap defaultMap = new FloorMap(defaultLayout);
        // initial location is Point (2,4)
        Kiva kiva = new Kiva(defaultLayout);
        // WHEN
       
        kiva.move(KivaCommand.TURN_LEFT); // no change in current location, only change facing direction

        verifyKivaState("testLeftFromUp", kiva, new Point(2, 4), FacingDirection.LEFT, false, false);
    }
    public void testLeftFromLeft() {
        // GIVEN
        // A Kiva built with the default map we defined earlier
        FloorMap defaultMap = new FloorMap(defaultLayout);
 
        Kiva kiva = new Kiva(defaultLayout);
        // WHEN
        
        kiva.move(KivaCommand.TURN_LEFT);  // no change in current location, only change facing direction
        kiva.move(KivaCommand.TURN_LEFT);
    
        verifyKivaState("testLeftFromLeft", kiva, new Point(2, 4), FacingDirection.DOWN, false, false);
    }
    public void testLeftFromDown() {
        // GIVEN
        // A Kiva built with the default map we defined earlier
        FloorMap defaultMap = new FloorMap(defaultLayout);
        Kiva kiva = new Kiva(defaultLayout);
        // WHEN
        
        // Call move three times with the TURN_LEFT command.
        kiva.move(KivaCommand.TURN_LEFT); // no change in current location, only change in facing direction
        kiva.move(KivaCommand.TURN_LEFT); // DOWN
        kiva.move(KivaCommand.TURN_LEFT); // only turn facing direction
   
        // Ensure the direction facing is now RIGHT.
        verifyKivaState("testLeftFromDown", kiva, new Point(2, 4), FacingDirection.RIGHT, false, false);
    }
    public void testLeftFromRight() {
        // GIVEN
        // A Kiva built with the default map we defined earlier
        FloorMap defaultMap = new FloorMap(defaultLayout);
        Kiva kiva = new Kiva(defaultLayout);
        // WHEN
        //Call move four times with the TURN_LEFT command. Verify the state​ of the Kiva object. 
        kiva.move(KivaCommand.TURN_LEFT); // no change in current location, only change in facing direction
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_LEFT); // RIGHT
        kiva.move(KivaCommand.TURN_LEFT);
        // THEN
        // Ensure the direction facing is now UP
        verifyKivaState("testLeftFromRight", kiva, new Point(2, 4), FacingDirection.UP, false, false);
    }
    
    public void testForwardWhileFacingLeft() {
        // GIVEN
        // A Kiva built with the default map we defined earlier
        FloorMap defaultMap = new FloorMap(defaultLayout);
        
        Kiva kiva = new Kiva(defaultLayout);
        // WHEN
        // We move one space left and one space forward   initial Point (2,4)   
        kiva.move(KivaCommand.TURN_LEFT);  // no change in current location, only facing direction                   
        kiva.move(KivaCommand.FORWARD);    // move in negative direction of x-axis, westbound, 1 step left                  
        
        // THEN
        // Ensure the direction facing is now LEFT
        verifyKivaState("testForwardWhileFacingLeft", kiva, new Point(1, 4), FacingDirection.LEFT, false, false);
    }
    public void testForwardWhileFacingDown() {
        // GIVEN
        // A Kiva built with the default map we defined earlier
        FloorMap defaultMap = new FloorMap(defaultLayout);
        
        Kiva kiva = new Kiva(defaultLayout);  //initial Point (2,4)
        // WHEN
        kiva.move(KivaCommand.TURN_LEFT); // LEFT
        //System.out.println(kiva.getDirectionFacing());  
        kiva.move(KivaCommand.TURN_LEFT);  
        //System.out.println(kiva.getDirectionFacing());  // DOWN
        kiva.move(KivaCommand.FORWARD);    // go northbound, up the y-axis
        
        // THEN
        // Ensure the direction facing is now DOWN
        verifyKivaState("testForwardWhileFacingDown", kiva, new Point(2, 5), FacingDirection.DOWN, false, false);
    }
    public void testForwardWhileFacingRight() {
        // GIVEN
        // A Kiva built with the default map we defined earlier
        FloorMap defaultMap = new FloorMap(defaultLayout);
        
        Kiva kiva = new Kiva(defaultLayout);
        // WHEN
        // move 3 spaces left and 1 forward
        kiva.move(KivaCommand.TURN_LEFT);  
        kiva.move(KivaCommand.TURN_LEFT);  
        kiva.move(KivaCommand.TURN_LEFT);  // RIGHT
        kiva.move(KivaCommand.FORWARD); // move 1 step toward right 
        
        // THEN
        // Ensure the direction facing is now RIGHT
        verifyKivaState("testForwardWhileFacingRight", kiva, new Point(3,4), FacingDirection.RIGHT, false, false);
    }
    
    public void testRightFromUp()  {
        // GIVEN
        // A Kiva built with the default map we defined earlier
        FloorMap defaultMap = new FloorMap(defaultLayout);
       
        Kiva kiva = new Kiva(defaultLayout);
        // WHEN
        // We move one space right
        kiva.move(KivaCommand.TURN_RIGHT); // no change in current location, only change facing direction
        
        // THEN
        // The Kiva has moved one space up
        verifyKivaState("testRightFromUp", kiva, new Point(2, 4), FacingDirection.RIGHT, false, false);
    }
    
    public void testRightFromLeft()  {
        // GIVEN
        // A Kiva built with the default map we defined earlier
        FloorMap defaultMap = new FloorMap(defaultLayout);
        //Point currentLocation = new Point(2, 3);
        Kiva kiva = new Kiva(defaultLayout);
        // WHEN
        // We move one space right
        // call move() with the TURN_LEFT command followed by a call to move with the TURN_RIGHT command
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_RIGHT);
        
        // THEN
        // Ensure the direction facing is now UP.
        verifyKivaState("testRightFromLeft", kiva, new Point(2, 4), FacingDirection.UP, false, false);
    }
    
    public void testRightFromDown()  {
        // GIVEN
        // A Kiva built with the default map we defined earlier
        FloorMap defaultMap = new FloorMap(defaultLayout);
        //Point currentLocation = new Point(2, 3);
        Kiva kiva = new Kiva(defaultLayout);
        // WHEN
        //call move() with the TURN_LEFT command twice followed by a call to move with the TURN_RIGHT command. 
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_RIGHT);
        
        // THEN
        //Ensure the direction facing is now LEFT.
        verifyKivaState("testRightFromDown", kiva, new Point(2, 4), FacingDirection.LEFT, false, false);
    }
    
    public void testRightFromRight()  {
        // GIVEN
        // A Kiva built with the default map we defined earlier
        FloorMap defaultMap = new FloorMap(defaultLayout);
        Kiva kiva = new Kiva(defaultLayout);

        // WHEN
        // Call move with the TURN_LEFT command three times followed by a call to move with the TURN_RIGHT command. 
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_RIGHT);
        
        // THEN
        // Ensure the direction facing is now DOWN.
        verifyKivaState("testRightFromRight", kiva, new Point(2, 4), FacingDirection.DOWN, false, false);
    }
    public void testTakeOnPod() {
        // GIVEN
        // A Kiva built with the default map we defined earlier
        FloorMap defaultMap = new FloorMap(defaultLayout);
        
        Kiva kiva = new Kiva(defaultLayout);

        // WHEN
        // Call move() to go up three times, turn right, move right six times, and take the pod. 
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.TURN_RIGHT);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        //System.out.println(kiva.getCurrentLocation());
        kiva.move(KivaCommand.TAKE);  // Point (8,1)
        //System.out.println(kiva.isCarryingPod());
        // Ensure that it is carrying the pod.
        verifyKivaState("testTakeOnPod", kiva, new Point(8, 1), FacingDirection.RIGHT, true, false);
    }
    public void testDropOnDropZone() {
        // GIVEN
        // A Kiva built with the default map we defined earlier
        FloorMap defaultMap = new FloorMap(defaultLayout);
        //Point actualLocation = defaultMap.getInitialKivaLocation();
        Kiva kiva = new Kiva(defaultLayout);

        // WHEN
        //Call move() to go up three times, turn right, move right six times, take the pod, move to the drop zone,and drop the pod.
        
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.TURN_RIGHT); 
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.TAKE);  //Point (8,1)
        //System.out.println(kiva.isCarryingPod()); // true, after TAKE command
        kiva.move(KivaCommand.FORWARD); 
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.TURN_RIGHT);
         //System.out.println(kiva.isCarryingPod());  
        //System.out.println(kiva.getDirectionFacing());
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD); 
        kiva.move(KivaCommand.FORWARD);
        //System.out.println(kiva.isCarryingPod());  
        //System.out.println(kiva.getCurrentLocation());
        kiva.move(KivaCommand.DROP);  // Point (10,4)
        // Ensure that it is not carrying the pod, and the drop was successful.
        verifyKivaState("testDropOnDropZone", kiva, new Point(10, 4), FacingDirection.DOWN, false, true);
    }
    // Handle Invalid Moves
    
    public void testMoveOutOfBounds() {
        FloorMap defaultMap = new FloorMap(defaultLayout);
        //Point actualLocation = defaultMap.getInitialKivaLocation();
        
        Kiva kiva = new Kiva(defaultLayout);
        
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        //System.out.println(kiva.getCurrentLocation()); 
        System.out.println("testMoveOutOfBounds: (expect an IllegalMoveException)");
        kiva.move(KivaCommand.FORWARD);
        
        // This only runs if no exception was thrown
        System.out.println("testMoveOutOfBounds FAIL!");
        System.out.println("Moved outside the FloorMap!");
    }

    public void testObstacleException(){
        //Create a test that moves into an obstacle and prints a failure message if no exception is thrown.
        FloorMap defaultMap = new FloorMap(defaultLayout);
        //Point actualLocation = defaultMap.getInitialKivaLocation();
        
        Kiva kiva = new Kiva(defaultLayout);
        
        kiva.move(KivaCommand.FORWARD);  
        kiva.move(KivaCommand.FORWARD);  
        kiva.move(KivaCommand.TURN_RIGHT);
        //System.out.println(defaultMap.getObjectAtLocation(kiva.getCurrentLocation())); 
        System.out.println("testObstacleException: (expect an IllegalMoveException)"); 
        kiva.move(KivaCommand.FORWARD);  
        
        // This only runs if no exception was thrown
        System.out.println("testObstacleException FAIL!");
        System.out.println("Location is an obstacle on the FloorMap!");
  
    }
    
    public void testPodCollisionException(){
        //robot takes a pod to a pod location, called pod collision
        FloorMap defaultMap = new FloorMap(defaultLayout);
        //Point actualLocation = defaultMap.getInitialKivaLocation();
        
        Kiva kiva = new Kiva(defaultLayout);
        FacingDirection direction = FacingDirection.UP;
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        //System.out.println(kiva.getDirectionFacing());
        kiva.move(KivaCommand.TURN_RIGHT);
        //System.out.println(kiva.getDirectionFacing());
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        //System.out.println(kiva.isCarryingPod()); 
        //System.out.println(defaultMap.getObjectAtLocation(kiva.getCurrentLocation()));
        //System.out.println(kiva.getCurrentLocation()); 
        kiva.move(KivaCommand.TAKE);  // Point (8,1) carrying pod
        System.out.println("testPodCollisionException: (expect an IllegalMoveException)");
        kiva.move(KivaCommand.TAKE);  // can't take a pod to a pod location, carrying a pod 
        
        // This only runs if no exception was thrown
        System.out.println("testPodCollisionException FAIL!");
        System.out.println("Robot cannot carry a pod and take it to a pod location!");   
    }
    
    public void testNoPodException() {
        // GIVEN
        // A Kiva built with the default map we defined earlier
        FloorMap defaultMap = new FloorMap(defaultLayout);
        //Point actualLocation = defaultMap.getInitialKivaLocation();
        Kiva kiva = new Kiva(defaultLayout);

        // WHEN
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.TURN_RIGHT); 
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.TAKE);
        
        System.out.println("testNoPodException: (expect a NoPodException)"); 
        
        // This only runs if no exception was thrown
        System.out.println("testNoPodException FAIL!");
        System.out.println("There is no pod at this location on the FloorMap!");
    }
    
    public void testNoDropException() {  
        // GIVEN
        // A Kiva built with the default map we defined earlier
        FloorMap defaultMap = new FloorMap(defaultLayout);
        Kiva kiva = new Kiva(defaultLayout);

        // WHEN
        //Create a test that picks up a pod, drops it in an empty space, and prints a failure 
        //message if no exception is thrown.
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.TURN_RIGHT); 
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.DROP);
        System.out.println("testNoDropException: (expect a IllegalDropZoneException)"); 
        
        System.out.println("testNoDropException FAIL!");
        System.out.println("You cannot drop. There is no drop zone at this location on the FloorMap!");
      }

    
    
    private boolean sameLocation(Point a, Point b) {
        return a.getX() == b.getX() && a.getY() == b.getY();
    }

    private void verifyKivaState(
            String testName,
            Kiva actual,
            Point expectLocation,
            FacingDirection expectDirection,
            boolean expectCarry,
            boolean expectDropped) {

        Point actualLocation = actual.getCurrentLocation();
        if (sameLocation(actualLocation, expectLocation)) {
            System.out.println(
                    String.format("%s: current location SUCCESS", testName));
        }
        else {
            System.out.println(
                    String.format("%s: current location FAIL!", testName));
            System.out.println(
                    String.format("Expected %s, got %s",
                            expectLocation, actualLocation));
        }

        FacingDirection actualDirection = actual.getDirectionFacing();
        if (actualDirection == expectDirection) {
            System.out.println(
                    String.format("%s: facing direction SUCCESS", testName));
        }
        else {
            System.out.println(
                    String.format("%s: facing direction FAIL!", testName));
            System.out.println(
                    String.format("Expected %s, got %s",
                            expectDirection, actualDirection));
        }

        boolean actualCarry = actual.isCarryingPod();
        if (actualCarry == expectCarry) {
            System.out.println(
                    String.format("%s: carrying pod SUCCESS", testName));
        }
        else {
            System.out.println(
                    String.format("%s: carrying pod FAIL!", testName));
            System.out.println(
                    String.format("Expected %s, got %s",
                            expectCarry, actualCarry));
        }

        boolean actualDropped = actual.isSuccessfullyDropped();
        if (actualDropped == expectDropped) {
            System.out.println(
                    String.format("%s: successfully dropped SUCCESS", testName));
        }
        else {
            System.out.println(
                    String.format("%s: successfully dropped FAIL!", testName));
            System.out.println(
                    String.format("Expected %s, got %s",
                            expectDropped, actualDropped));
        }
    }
}


