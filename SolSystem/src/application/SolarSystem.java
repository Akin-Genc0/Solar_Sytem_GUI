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
    private Planet earth;

//    private Planet Earth;
    
    /**
     * constructor for setting up solar system
     */
	public SolarSystem() {   
	
		earth = new Planet("Earth", "earth.png", 0.05, 0.3, 1);
	
	    sun = new Image(getClass().getResourceAsStream("sun.png"));
	    sunX = 0.5;
	    sunY = 0.5;
	    sunSize = 0.2;
	}

	/**
	 * Calculate the position of each object in system
	 * @param angle	indication of time/angle
	 */
	public void updateSystem (double angle) {
	} 
	
	/**
	 * draw the system into the given viewer
	 * @param s
	 */
	public void drawSystem(MyCanvas mc) {
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
		  return "Earth " + earth;
		}

}