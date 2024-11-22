package application;

import javafx.scene.image.Image;
/**
 * class for simple solar system, initially with sun and earth
 * @author shsmchlr
 *
 */
public class SimpleSolar {
	private double sunSize = 0.2;					// size sun should be drawn
	private double sunX, sunY, earthX, earthY, marsX, marsY;						// position of sun
	private double earthSize = 0.05;
	private double earthAngle , marsAngle;						// angle of earth .. for calculating its position
	private double earthOrbitSize = 0.3;			// defines relevant sizes (<1 so that positions in range -0.5 .. +0.5
	private double marsOrbitSize = 0.4;
	private double marsSize = 0.038;
	private Image earth;							// images of earth and sun
	private Image sun;
	private Image mars;	
	/**
	 * construct simple solar system
	 */
    public SimpleSolar() {
    	earth = new Image(getClass().getResourceAsStream("earth.png"));		// load image of earth
    	sun = new Image(getClass().getResourceAsStream("sun.png"));
    	mars = new Image(getClass().getResourceAsStream("mars.png"));
    	sunX = 0.5;															// set position of sun
    	sunY = 0.5;
    	earthAngle = 0.0;													// initialise earth
    }
    
	/**
	 * update position of planet(s) at specified angle 
	 * @param angle		angle (time dependent) of planet(s)
	 */
	public void updateSystem (double angle) {
		 earthAngle = angle;
		 marsAngle = earthAngle * (2.0 / 3.0);
		    // Calculate the new position of the Earth in orbit
		    earthX = earthOrbitSize * Math.cos(earthAngle); // Cosine for X coordinate
		    earthY = earthOrbitSize * Math.sin(earthAngle); // Sine for Y coordinate
		    
		    marsX = marsOrbitSize * Math.cos(marsAngle); // Cosine for X coordinate
		    marsY = marsOrbitSize * Math.sin(marsAngle); // Sine for Y coordinate
		
	}
	
	/** 
	 * set sun at position passed
	 * @param x		x position, in canvas coordinates
	 * @param y
	 */
	public void setSystem(MyCanvas mc, double x, double y) {
	     
	    sunX = x / mc.getXCanvasSize();
	    sunY = y / mc.getYCanvasSize();

	    // Update the system to recalculate the positions of Earth and Mars
	    updateSystem(earthAngle); // Use the current Earth angle or any updated logic here

	    // Redraw the entire system to reflect the new Sun position
	    drawSystem(mc);
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
		mc.drawImage (i, (x+sunX)*cs, (y+sunY)*cs, sz*cs);		// add sun's position to positions then * canvas size
	}
	
	/**
	 * draw system  into specified canvas
	 * @param mc		canvas
	 */
	public void drawSystem (MyCanvas mc) {
		mc.clearCanvas();					// first clear canvas 
		drawImage( mc, sun, 0, 0, sunSize );				// draw sun at 0,0
				// call drawImage to draw earth at position set by earth's angle and orbit size
		earthX = earthOrbitSize * Math.cos(earthAngle);
	   earthY = earthOrbitSize * Math.sin(earthAngle);

	    // Draw Earth in orbit around the Sun
	    drawImage(mc, earth, earthX, earthY, earthSize);
	    drawImage(mc, mars, marsX, marsY, marsSize);
	}

	/**
	 * return information about planets as a string
	 */
	public String toString() {
	  return "Earth " + earthAngle + "\nMars " + marsAngle;
	}
}
