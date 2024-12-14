package application;

import java.util.ArrayList;

import javafx.scene.image.Image;

/**
 * @author shsmchlr
 *
 */
public class SolarSystem {
	private double sunX, sunY, sunSize;				// positions of sun
    private Image sun;								// imnge of sun
    private ArrayList<Planet> planets;

//    private Planet Earth;
    
    /**
     * constructor for setting up solar system
     */
    

	public SolarSystem() {   
	
	    
		 sun = new Image(getClass().getResourceAsStream("sunn.png"));
		    sunX = 0.5;
		    sunY = 0.5;
		    sunSize = 0.2;
		
		
		planets = new ArrayList<>(); // Initialise the ArrayList

        // Create Earth and Mars as Planet objects
        Planet earth = new Planet("Earth", "earth.png", 0.05, 0.35, 1);
        Planet mars = new Planet("Mars", "mars.png", 0.039, 0.4, 0.75);
        Planet venus = new Planet("Venus", "venus.png", 0.045, 0.25, 0.9);
        Planet merc = new Planet("merc", "merc.jpeg", 0.03, 0.17, 1.1);
        
        
        // Create the Moon as a Satellite orbiting Earth
        Satellite moon = new Satellite("Moon", "moon.png", 0.025, 0.05, 5, earth);
        Satellite dem = new Satellite("Moon", "deimos.png", 0.015, 0.05, 5, mars);
        Satellite pho = new Satellite("Moon", "phob.png", 0.01, 0.08 , 8, mars);
        // Add Earth, Mars, and the Moon to the ArrayList
        planets.add(earth);
        planets.add(mars);
        planets.add(moon);
        planets.add(venus);
        planets.add(merc);
        planets.add(dem);
        planets.add(pho);
        
	    
	   
	    
	    
	    
	}

	/**
	 * Calculate the position of each object in system
	 * @param angle	indication of time/angle
	 */
	public void updateSystem (double angle) {
		for(Planet planet : planets) {
			planet.updatePosition(angle);
		}
		
	} 
	
	/**
	 * draw the system into the given viewer
	 * @param s
	 */
	public void drawSystem(MyCanvas mc) {
		for(Planet planet : planets) {
			planet.drawPlanet(this, mc);
		}
		drawImage(mc, sun, 0, 0, sunSize);			// draw Sun,

	}
	
	
	
	/**
	 * drawImage into canvas, at position x,y relative to sun, but scale the x,y and sz before drawing
	 * @param mc	canvas
	 * @param i		image
	 * @param x		x position		in range -0.5..0.5
	 * @param y		y position
	 * @param sz	size
	 */
	public void drawImage (MyCanvas mc, Image i, double x, double y, double sz) {
		int cs = mc.getXCanvasSize();
		mc.drawImage (i, (x+sunX)*cs, (y+sunY)*cs, sz*cs);		// add 0.5 to positions then * canvas size
	}

	/**
	 * return String with info of planet(s) in system
	 */
	public String toString() {
		  StringBuilder sb = new StringBuilder("Solar System:\n");
	        for (Planet planet : planets) {
	            sb.append(planet.toString()).append("\n");
	        }
	        return sb.toString();
	    }
}