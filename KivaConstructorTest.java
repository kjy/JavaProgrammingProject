
/**
 * KivaConstructorTest checks both the single and double argument constructor for the point, representing the initial robot location on a map. 
 * The test checks whether the initial point location matches its expected location. 
 * 
 * @author Karen Yang 
 * @version 10/16/2021
 */
import edu.duke.Point; 

public class KivaConstructorTest { 
    String defaultLayout = "" 
                            + "-------------\n" 
                            + "        P   *\n"
                            + "   **       *\n"
                            + "   **       *\n"
                            + "  K       D *\n"
                            + " * * * * * **\n"
                            + "-------------\n";

    

    public void testSingleArgumentConstructor() { 
        // GIVEN 
        // The default map we defined earlier 
        FloorMap defaultMap = new FloorMap(defaultLayout);

        // WHEN 
        // We create a Kiva with the single-argument constructor         
        Kiva kiva = new Kiva(defaultLayout); 

        // THEN 
        // The initial Kiva location is (2, 4) 
        // Instruction:  set current location from the provided floormap
        Point initialLocation = defaultMap.getInitialKivaLocation();
        Point expectedLocation = new Point(2, 4);
        
        if (sameLocation(initialLocation, expectedLocation)) { 
            System.out.println("testSingleArgumentConstructor SUCCESS"); 
        } else { 
            System.out.println(String.format( "testSingleArgumentConstructor FAIL: %s != (2,4)!", initialLocation)); 
        }
    }
    
    private boolean sameLocation(Point a, Point b) { 
        return (a.getX() == b.getX() && a.getY() == b.getY()); 
    
    }
     

    // For you: create a test for the constructor taking two arguments. 
    
    public void testTwoArgumentConstructor() {
        // GIVEN -- the default map we defined earlier
        FloorMap defaultMap = new FloorMap(defaultLayout);
        // set current Location of Kiva
        Point currentLocation = new Point(5,6);
        
        // WHEN -- We create a Kiva with the two-argument constructor
        Kiva kiva = new Kiva(defaultLayout, currentLocation);
        // sets location from a Point
        currentLocation = kiva.getCurrentLocation();
        // expected result
        Point expectedLocation = new Point(5, 6);
        
        if (sameLocation(currentLocation, expectedLocation)) { 
            System.out.println("testTwoArgumentConstructor SUCCESS"); 
        } else { 
            System.out.println(String.format( "testTwoArgumentConstructor FAIL: %s != (5,6)!", currentLocation)); 
        }

    
    }  
}
