package application;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class SolarViewer extends Application {
	private int canvasSize = 512;				// constant for canvas size
	private MyCanvas mc; 						// canvas in which system drawn
    private SolarSystem ourSystem;				// object for solar system
    private VBox rtPane;						// Vertical box for displaying status of planets
    private boolean animationOn = false;

	/**
	 * show where Earth is, in pane on right
	 */
	public void drawStatus() {
		rtPane.getChildren().clear();					// clear rtpane
				// now create label
		Label l = new Label(ourSystem.toString());
		rtPane.getChildren().add(l);				// add label to pane	
	}
	/**
	 * draw the sun, planets and satellites
	 */
	public void displaySystem() {
		mc.clearCanvas();
		ourSystem.drawSystem(mc);
		drawStatus();
	}
	
	 /**
	  * Function to show a message, 
	  * @param TStr		title of message block
	  * @param CStr		content of message
	  */
	private void showMessage(String TStr, String CStr) {
		    Alert alert = new Alert(AlertType.INFORMATION);
		    alert.setTitle(TStr);
		    alert.setHeaderText(null);
		    alert.setContentText(CStr);

		    alert.showAndWait();
	}
   /**
	 * function to show in a box ABout the programme
	 */
	 private void showAbout() {
		 showMessage("About", "RJM's Solar System Demonstrator");
	 }

	/**
	 * Function to set up the menu
	 */
	MenuBar setMenu() {
		MenuBar menuBar = new MenuBar();		// create menu

		Menu mHelp = new Menu("Help");			// have entry for help
				// then add sub menus for About and Help
				// add the item and then the action to perform
		MenuItem mAbout = new MenuItem("About");
		mAbout.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent actionEvent) {
           	showAbout();				// show the about message
           }	
		});
		mHelp.getItems().addAll(mAbout); 	// add submenus to Help
		
				// now add File menu, which here only has Exit
		Menu mFile = new Menu("File");
		MenuItem mExit = new MenuItem("Exit");
		mExit.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent t) {
		        System.exit(0);						// quit program
		    }
		});
		mFile.getItems().addAll(mExit);
		
		menuBar.getMenus().addAll(mFile, mHelp);	// menu has File and Help
		
		return menuBar;					// return the menu, so can be added
	}

	/**
	 * set up the buttons and return so can add to borderpane
	 * @return
	 */
    private HBox setButtons() {
    			// create button
    	Button btnAnimOn = new Button("Start Animation");
		// now add handler
    	btnAnimOn.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			animationOn = true;
    		}
    	});
    	
    	Button btnAnimOff = new Button("Stop Animation");
		// now add handler
    	btnAnimOff.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			animationOn = false;
    		}
    	});
    	
    	return new HBox(btnAnimOn, btnAnimOff);
    }

	/**
	 * main function ... sets up canvas, menu, buttons and timer
	 */
	@Override
	public void start(Stage stagePrimary) throws Exception {
		stagePrimary.setTitle("RJMs Solar System Simulator");

	    BorderPane bp = new BorderPane();			// create border pane

	    bp.setTop(setMenu());						// create menu, add to top

	    Group root = new Group();					// create group
	    Canvas canvas = new Canvas( canvasSize, canvasSize );
	    											// and canvas to draw in
	    root.getChildren().add( canvas );			// and add canvas to group
	    mc = new MyCanvas(canvas.getGraphicsContext2D(), canvasSize, canvasSize);
					// create MyCanvas passing context on canvas onto which images put
	    ourSystem = new SolarSystem();				// create object for sun, planets, etc
	    
	    bp.setCenter(root);							// put group in centre pane

	    rtPane = new VBox();						// set vBox for listing data
	    bp.setRight(rtPane);						// put in right pane

	    Scene scene = new Scene(bp, canvasSize*1.6, canvasSize*1.2);
		// create scene so bigger than canvas, 

	    final long startNanoTime = System.nanoTime();		// for animation, note start time

	    new AnimationTimer()			// create timer
	    	{
	    		public void handle(long currentNanoTime) {
	    				// define handler for what do at this time
	    			if (animationOn) {
	    				double t = (currentNanoTime - startNanoTime) / 1000000000.0; // calculate time
	    				ourSystem.updateSystem(t);    				// calculate coordinates of planets etc
	    				displaySystem();							// now clear canvas and draw system
	    			}	
	    		}
	    	}.start();					// start it

	    bp.setBottom(setButtons());					/// add button to bottom

		stagePrimary.setScene(scene);
		stagePrimary.show();
	}
	
	public static void main(String[] args) {
		Application.launch(args);			// launch the GUI
	}

}