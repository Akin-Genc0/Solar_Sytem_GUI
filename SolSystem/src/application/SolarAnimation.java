package application;


import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

public class SolarAnimation extends Application {
	private int canvasSize = 512;				// size of canvas
    private MyCanvas mc; 						// canvas in which system drawn
    private SimpleSolar ourSystem;				// model of system

	/**
	 * main function ... sets up canvas, menu, buttons and timer
	 */
	@Override
	public void start(Stage stagePrimary) throws Exception {
		stagePrimary.setTitle("Akin Solar System Animation");
		
	    Group root = new Group();					// for group of what is shown
	    Scene scene = new Scene( root );			// put it in a scene
	    stagePrimary.setScene( scene );				// apply the scene to the stage
	 
	    Canvas canvas = new Canvas( canvasSize, canvasSize );
	    							// create canvas onto which animation shown
	    root.getChildren().add( canvas );			// add to root and hence stage
	 
	    mc = new MyCanvas(canvas.getGraphicsContext2D(), canvasSize, canvasSize);
	    								// create MyCanvas passing context on canvas onto which images put
	    ourSystem = new SimpleSolar();				// create object for sun, planets, etc
		
	    final long startNanoTime = System.nanoTime();
		// for animation, note start time

	    new AnimationTimer()			// create timer
	    	{
	    		public void handle(long currentNanoTime) {
	    				// define handle for what do at this time
	    			double t = (currentNanoTime - startNanoTime) / 1000000000.0; 			// calculate time
	    			ourSystem.updateSystem(t);			// use time as an angle for calculating position of earth
	    			ourSystem.drawSystem(mc);
	    		}
	    	}.start();					// start it
	    
		stagePrimary.show();
	}
	
	public static void main(String[] args) {
		Application.launch(args);			// launch the GUI
	}

}