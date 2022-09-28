
/**
 * Kiva class "holds the state and logic of a robot: where it is, which way it's facing, whether it's
 * carrying a pod right now, and whether it has successfully dropped the pod.  A Kiva always starts facing UP,
 * not carrying a pod, and not having successfully dropped the pod."
 * 
 * source:  https://w.amazon.com/bin/view/Amazon_Technical_Academy/Self_Service_Courses/Course4/JavaProgrammingPostDuke/project/KivaRobotRemoteControl
 * 
 * @author Karen Yang
 * @version 10/16/2021
 */

import edu.duke.Point;

/**
 * Kiva class. 
 */

public class Kiva {
    
    /**
    * Instance Variables. 
    */
    private int x;  // x-coordinate of Point(x,y) 
    private int y;  // y-coordinate  of Point (x,y)
    private Point currentLocation = new Point(2,4);
    private FacingDirection direction = FacingDirection.UP; 
    private boolean carryingPod = false;
    private boolean successfullyDropped = false;
    private long motorLifetime = 0L;  
    private String inputMap;
    private FloorMap floorMap;
    private KivaCommand command;
    private FloorMapObject floorMapObject;
    

    /**
     * Constructor.
     * 
     * @param String inputMap  the floor map.
     */
    //FileResource fr = new FileResource();  //Initialize map as a FileResource then convert it as a string.
    //String inputMap = fr.asString();
    public Kiva(String inputMap) {  
        this.inputMap = inputMap;
        FloorMap floorMap = new FloorMap(inputMap);  //Pass the FileResource String in the FloorMap
    }
    
    /**
     * Constructor.
     * 
     * @param String inputMap  the floor map.
     * @param Point(x,y)  the currentLocation  of the Kiva robot.
     */
    public Kiva(String inputMap, Point currentLocation){
         this.inputMap = inputMap;
         FloorMap floorMap = new FloorMap(inputMap);
         //sets the location of the Kiva robot from a Point (remember to import edu.duke.Point)
         this.currentLocation = currentLocation;
    }
    
    /**
     * Getter Method.
     * @return Point(x,y) the initial location of the Kiva robot from the floor map.
     */
    public Point getInitialLocation() {
        return floorMap.getInitialKivaLocation();
    }
    
    /**
     * Getter Method.
     * @return Point(x,y) the current location of the Kiva robot.
     */
    public Point getCurrentLocation() {
         return this.currentLocation;
    }
    
    /**
     * Getter Method.
     * @return enum the facing direction of the Kiva robot.
     */
    public FacingDirection getDirectionFacing() {
        return this.direction;
    }
    /**
     * Getter Method.
     * @return boolean whether the Kiva robot is carrying a pod or not.
     */
    public boolean isCarryingPod() { 
        return this.carryingPod;
        
    }
    
    /**
     * Getter Method.
     * @return boolean  whether the Kiva robot has successfully dropped a pod or not.
     */
    public boolean isSuccessfullyDropped() { 
        return this.successfullyDropped;
    }
        /**
     * Getter Method.
     * 
     * @return long int milliseconds.    20000 hours max motor life converts to 72000000000 ms.
     * 
     * When a Kiva robot moves forward, turns left, or turns right, the motor lifetime increments by 1000 milliseconds.
     */
    
    public long getMotorLifetime() {
        return this.motorLifetime;
    }

    /**
     * Method.
     * Kiva robot move method.
     * calls helper functions, based on Kiva Commands (FORWARD, TURN_LEFT, TURN_RIGHT, TAKE, DROP).
     */
    
    public void move(KivaCommand command) {
        if (command.equals(KivaCommand.FORWARD)) {  
            this.moveForward(); 
        }
        else if (command.equals(KivaCommand.TURN_LEFT)) {// Turning only changes facing direction
            this.turnLeft();
        }
        else if (command.equals(KivaCommand.TURN_RIGHT)) {// Turning only changes facing direction
            this.turnRight();
        }    
        else if (command.equals(KivaCommand.TAKE)) { 
            this.moveTake();
        }
        else if (command.equals(KivaCommand.DROP)) {
            this.moveDrop();
        }
    }
        
    // FORWARD   
     /**
     * Method. Helper function.
     * 
     * Changes current location by setting new value for Point(x,y).
     * Updates direction, carryingPod, and successfullyDropped by calling getters.
     * Updates motorLifetime by incrementing by 1000L for changing current location.
     * 
     * @IllegalMoveException called when current location has x or y values equal to zero.
     * @IllegalMoveException called out of boundary of the map.
     * @IllegalMoveException called for obstacle at a location on the map.
     * @IllegalMoveException called for pod collision.
     * 
     */
    private void moveForward() {
        // initialize class object for FloorMap
        FloorMap defaultMap = new FloorMap(inputMap);
        //FloorMapObject object = defaultMap.getObjectAtLocation(new Point(currentLocation.getX(), currentLocation.getY()));
        int minRow = defaultMap.getMinRowNum(); // 0
        int maxRow = defaultMap.getMaxRowNum(); // 6
        int minCol = defaultMap.getMinColNum(); // 0
        int maxCol = defaultMap.getMaxColNum(); // 12
        
         
        if (direction.equals(FacingDirection.UP)) {
            // location of next move
            Point nextMoveLocation1 = new Point(currentLocation.getX(), currentLocation.getY()-1);
            
            // check if new location is within the boundaries
            // If the new location is less than 0 in any dimension, or greater than the corresponding dimension of the floor map
            // then throw an IllegalMoveException
            if (nextMoveLocation1.getY() > maxRow || nextMoveLocation1.getY() > maxCol       
               || nextMoveLocation1.getX() < minRow || nextMoveLocation1.getY() < minCol) {  
               throw new IllegalMoveException(String.format(
                "Next move will be %s. Can't make next move. Out of Bounds!", nextMoveLocation1));
            }  
            else {
                // Find floor map object at next move location
                FloorMapObject object1 = defaultMap.getObjectAtLocation(new Point(currentLocation.getX(), currentLocation.getY()-1));
                
                //check for an obstacle at new location 
                if (object1 == FloorMapObject.OBSTACLE) {  
                    throw new IllegalMoveException(String.format(
                    "Can't move onto an obstacle at %s!", nextMoveLocation1)); }
                //check for pod collision  
                else if (object1 == FloorMapObject.POD && carryingPod == true) {
                    throw new IllegalMoveException(String.format(
                    "Can't MAKE NEXT MOVE: next location %s will be %s, Pod collision!", nextMoveLocation1, object1));
                }
                // if no exceptions from above then update current Location and other variables
                else {
                    // set current location, if valid
                    this.currentLocation =  new Point(currentLocation.getX(), currentLocation.getY() - 1); 
                    this.direction = this.getDirectionFacing();
                    this.carryingPod = this.isCarryingPod();
                    this.successfullyDropped = this.isSuccessfullyDropped();
                    
                    // for each move, increment motor lifetime by long integer 1000L.
                    this.motorLifetime = motorLifetime + 1000L;
                }
            }
        }
        else if (direction.equals(FacingDirection.LEFT)) {
            // location of next move
            Point nextMoveLocation2 = new Point(currentLocation.getX() - 1, currentLocation.getY());
            
            // check if new location is within the boundaries 
            if (nextMoveLocation2.getY() > maxRow || nextMoveLocation2.getY() > maxCol       
               || nextMoveLocation2.getX() < minRow || nextMoveLocation2.getY() < minCol) {  
               throw new IllegalMoveException(String.format(
                "Next location will be %s. Can't make next move. Out of Bounds!", nextMoveLocation2));
            } 
            else { 
                // Find floor map object at next move location
                FloorMapObject object2 = defaultMap.getObjectAtLocation(new Point(currentLocation.getX() - 1, currentLocation.getY()));
                
                //check for an obstacle at new location 
                if (object2 == FloorMapObject.OBSTACLE) {  
                    throw new IllegalMoveException(String.format(
                    "Can't move onto an obstacle at %s!", nextMoveLocation2)); 
                }
                //check for pod collision  
                else if (object2 == FloorMapObject.POD && carryingPod == true) {
                    throw new IllegalMoveException(String.format(
                    "Can't MAKE MOVE: next location %s will be %s, Pod collision!", nextMoveLocation2, object2));
                }
                else { 
                    // set current location, if valid
                    this.currentLocation =  new Point(currentLocation.getX() - 1, currentLocation.getY()); 
                    this.direction = this.getDirectionFacing();
                    this.carryingPod = this.isCarryingPod();
                    this.successfullyDropped = this.isSuccessfullyDropped();
                    
                    // for each move, increment motor lifetime by long integer 1000L.
                    this.motorLifetime = motorLifetime + 1000L;
                }
            }
        }
        else if (direction.equals(FacingDirection.DOWN)) {
            // location of next move
            Point nextMoveLocation3 = new Point(currentLocation.getX(), currentLocation.getY() + 1);
            
            // check if new location is within the boundaries 
            if (nextMoveLocation3.getY() > maxRow || nextMoveLocation3.getY() > maxCol       
               || nextMoveLocation3.getX() < minRow || nextMoveLocation3.getY() < minCol) {  
               throw new IllegalMoveException(String.format(
                "Next location will be %s. Can't make next move. Out of Bounds!", nextMoveLocation3));
            } 
            else { 
                // Find floor map object at next move location
                FloorMapObject object3 = defaultMap.getObjectAtLocation(new Point(currentLocation.getX(), currentLocation.getY()+ 1));
                
                //check for an obstacle at new location 
                if (object3 == FloorMapObject.OBSTACLE) {  
                    throw new IllegalMoveException(String.format(
                    "Can't move onto an obstacle at %s!", nextMoveLocation3)); 
                }
                //check for pod collision  
                else if (object3 == FloorMapObject.POD && carryingPod == true) {
                    throw new IllegalMoveException(String.format(
                    "Can't MAKE MOVE: next location %s will be %s, Pod collision!", nextMoveLocation3, object3));
                }
                else { 
                    // set current location, if valid
                    this.currentLocation =  new Point(currentLocation.getX(), currentLocation.getY() + 1); 
                    this.direction = this.getDirectionFacing();
                    this.carryingPod = this.isCarryingPod();
                    this.successfullyDropped = this.isSuccessfullyDropped();
                    
                    // for each move, increment motor lifetime by long integer 1000L.
                    this.motorLifetime = motorLifetime + 1000L;
                }
            }
        }
        else if (direction.equals(FacingDirection.RIGHT)) {
            
            // location of next move
            Point nextMoveLocation4 = new Point(currentLocation.getX() + 1, currentLocation.getY());
            // check if new location is within the boundaries 
            if (nextMoveLocation4.getY() > maxRow || nextMoveLocation4.getY() > maxCol       
               || nextMoveLocation4.getX() < minRow || nextMoveLocation4.getY() < minCol) {  
               throw new IllegalMoveException(String.format(
                "Next location will be %s. Can't make next move. Out of Bounds!", nextMoveLocation4));
            } 
            else {
                // Find floor map object at next move location   
                FloorMapObject object4 = defaultMap.getObjectAtLocation(new Point(currentLocation.getX() + 1, currentLocation.getY()));
                
                //check for an obstacle at new location 
                if (object4 == FloorMapObject.OBSTACLE) { 
                    //System.out.println(object4);
                    throw new IllegalMoveException(String.format(
                    "Can't move onto an obstacle at %s!", nextMoveLocation4)); 
                }
                //check for pod collision  
                else if (object4 == FloorMapObject.POD && carryingPod == true) {
                    throw new IllegalMoveException(String.format(
                    "Can't MAKE MOVE: next location %s will be %s, Pod collision!", nextMoveLocation4, object4));
                }
                else {
                    // set current location, if valid
                    this.currentLocation =  new Point(currentLocation.getX() + 1, currentLocation.getY()); 
                    this.direction = this.getDirectionFacing();
                    this.carryingPod = this.isCarryingPod();
                    this.successfullyDropped = this.isSuccessfullyDropped();
                    
                    // for each move, increment motor lifetime by long integer 1000L.
                    this.motorLifetime = motorLifetime + 1000L;
                }
            }
        }
    }
    
    // TURN LEFT 
     /**
     * Method. Helper function.
     * 
     * Changes the facing direction of the Kiva robot by setting new values.
     * Calls getters for other instance variables to update state. No changes made.
     * Calls motorLifetime to increment by 1000L for turning left.
     * 
     */
    private void turnLeft() { // only changes facing direction, does not change current location
        
        //set direction, update state
        if (direction.equals(FacingDirection.UP)) { 
            this.direction = FacingDirection.LEFT;
        }
        else if (direction.equals(FacingDirection.LEFT)) {   
            this.direction = FacingDirection.DOWN; 
        }
        else if (direction.equals(FacingDirection.DOWN)) { 
            this.direction = FacingDirection.RIGHT; 
        }   
        else if (direction.equals(FacingDirection.RIGHT)) {  
            this.direction = FacingDirection.UP; 
        }
        
        this.currentLocation = this.getCurrentLocation();
        this.direction = this.getDirectionFacing();
        this.carryingPod = this.isCarryingPod();
        this.successfullyDropped = this.isSuccessfullyDropped();
        
        // for each move, increment motor lifetime by long integer 1000L.
        this.motorLifetime = motorLifetime + 1000L;
        }
 
        
    // TURN RIGHT
     /**
     * Method. Helper function.
     * 
     * Changes the facing direction of the Kiva robot by setting new values.
     * Calls getters for other instance variables to update state. No changes made.
     * Calls motorLifetime to increment by 1000L for turning right.
     * 
     */
    private void turnRight(){  // only changes facing direction, does not change current location
       
        // set the facing direction
        if (direction.equals(FacingDirection.UP)) {
            this.direction = FacingDirection.RIGHT;
        }   
        else if (direction.equals(FacingDirection.LEFT)) { 
            this.direction = FacingDirection.UP; 
        } 
        else if (direction.equals(FacingDirection.DOWN)) {  
            this.direction = FacingDirection.LEFT; 
        }
        else if (direction.equals(FacingDirection.RIGHT)) { 
            this.direction = FacingDirection.DOWN; 
        }
        
       
       // update state
       this.currentLocation = this.getCurrentLocation(); 
       this.direction = this.getDirectionFacing();
       this.carryingPod = this.isCarryingPod();
       this.successfullyDropped = this.isSuccessfullyDropped();
       
       // for each move, increment motor lifetime by long integer 1000L.
       this.motorLifetime = motorLifetime + 1000L;
        }
 
    
    // TAKE
     /**
     * Method. Helper function.
     * 
     * Changes carryingPod variable to true
     * Calls getters for other instance variables to update state. No changes made.
     * 
     * @NoPodException called when there is no pod to take from a location.
     * @IllegalMoveException called when there is a pod collision (taking a pod to a pod location).
     * 
     */
    private void moveTake() {  
        // Implement and test checking for presence of a pod in the TAKE helper method. 
        FloorMap defaultMap = new FloorMap(inputMap);
        FloorMapObject object = defaultMap.getObjectAtLocation(currentLocation);
        //Create a test that tries to take a pod on an empty space and prints a failure message if no exception is thrown.
        this.carryingPod = this.isCarryingPod();

        if (object != FloorMapObject.POD) {// If the TAKE command is issued while the current location is not a POD, throw a NoPodException.
            throw new NoPodException(String.format(
                "Can't take nonexistent pod from location %s!", currentLocation));
        }
        
        else if (carryingPod == true) { 
            throw new IllegalMoveException(String.format(
            "Can't MAKE NEXT MOVE:  Robot is carrying %s to location %s, which is a pod. Pod collision!", object, currentLocation));
        }
        
        else 
        {
            this.currentLocation = this.getCurrentLocation();
            this.direction = this.getDirectionFacing();
            this.carryingPod = true;
            this.successfullyDropped = false;  
            this.motorLifetime = this.getMotorLifetime();
        }
    }
        
    // DROP
     /**
     * Method. Helper function.
     * 
     * Changes successfullyDropped variable to true.
     * Calls getters for other instance variables to update state. No changes made.
     * 
     * @IllegalDropZoneException called when dropping a pod where it is not a drop zone location.
     * @IllegalMoveException called when robot is not carrying a pod at a current location which is a drop zone location.
     * 
     */
    private void moveDrop() {
        FloorMap defaultMap = new FloorMap(inputMap);
        FloorMapObject object = defaultMap.getObjectAtLocation(new Point(currentLocation.getX(), currentLocation.getY()));

        if (object != FloorMapObject.DROP_ZONE) {  
            throw new IllegalDropZoneException(String.format(
               "Can't just drop pods willy-nilly at %s!", currentLocation));
        }
        
        else if (this.carryingPod == false) {
            throw new IllegalMoveException(String.format(
            "Can't MAKE NEXT MOVE:  location %s, You are not carrying a pod! ", currentLocation));
        }
        
        else 
        {
            this.currentLocation = this.getCurrentLocation();
            this.direction = this.getDirectionFacing();
            this.carryingPod = false;
            this.successfullyDropped = true;
            this.motorLifetime = this.getMotorLifetime();
        }
  
    }  
}



    
    


    



        
        



