package application;

import java.util.Random;

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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SolarBorderPane extends Application {
    private VBox rtPane;						// pane in which info on system listed
    private Random rgen = new Random();			// random number generator
    private MyCanvas mc;						// canvas into which system drawn
    private SimpleSolar ourSystem;				// simple model of solar system
    private int canvasSize = 512;				// size of canvas
    private boolean isAnimationOn = false; // Animation state
    private AnimationTimer animationTimer; // Single instance of AnimationTimer

    
    
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
		 showMessage("About", "Akin's BorderPane Demonstrator");
		
	 }
	 
	 private void showHelp() {
		    showMessage("Help", "cool");
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
		MenuItem help = new MenuItem("More-Help");
		
		
		mAbout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            					// show the about message
            }	
		});
		
		
		mAbout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            	showAbout();				// show the about message
            }	
		});
		
		
		help.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            	showHelp();				// show the help message
            }	
		});
		mHelp.getItems().addAll(mAbout); 	// add submenus to Help
		mHelp.getItems().addAll(help);
		
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
	 * show where Earth is, in pane on right
	 */
	public void drawStatus() {
		
		// clear rtPane
		rtPane.getChildren().clear();
		
		// get label which has information on system - use ourSystem.toString()
		   Label l = new Label(ourSystem.toString());
		
		
		// add label to rtPane
		rtPane.getChildren().add(l);
	}

	/**
	 * set up the mouse event handler, so when click on canvas, draw Earth there
	 * @param canvas
	 */
	private void setMouseEvents (Canvas canvas) {
	       canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, 
	    	       new EventHandler<MouseEvent>() {
	    	           @Override
	    	           public void handle(MouseEvent e) {
	    	        	   
	    	        	   ourSystem.setSystem(mc, e.getX(), e.getY());

	    	                // Redraw the solar system
	    	                ourSystem.drawSystem(mc);

	    	                // Update the right panel with the latest information
	    	                drawStatus();
	    	        	   
	    	           }
	    	       });
	}

	/**
	 * set up the button and return so can add to borderpane
	 * @return
	 */
	private HBox setButtons() {
	    // Create buttons
	    Button btnRandomEarth = new Button("Random Earth");
	    Button btnStart = new Button("Start");
	    Button btnPause = new Button("Pause");

	    // Initialise the AnimationTimer
	    animationTimer = new AnimationTimer() {
	        private final long startNanoTime = System.nanoTime();

	        @Override
	        public void handle(long currentNanoTime) {
	            if (isAnimationOn) { // Only update system if animation is on
	                double t = (currentNanoTime - startNanoTime) / 1_000_000_000.0; // Calculate time in seconds
	                ourSystem.updateSystem(t); // Update the solar system's state
	                ourSystem.drawSystem(mc);  // Redraw the solar system
	            }
	        }
	    };
	    animationTimer.start(); // Start the timer once; animation logic is controlled by `isAnimationOn`

	    // Start Button Handler
	 btnStart.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
	        isAnimationOn = true; // Set animation state to true
    		}
	    });

	 btnPause.setOnAction(new EventHandler<ActionEvent>() {
 		@Override
 		public void handle(ActionEvent event) {
	        isAnimationOn = false; // Set animation state to true
 		}
	    });
	    // Random Earth Button Handler
	    btnRandomEarth.setOnAction(new EventHandler<ActionEvent>() {
	 		@Override
	 		public void handle(ActionEvent event) {
	        double randAng = rgen.nextDouble(360); // Generate a random angle
	        ourSystem.updateSystem(randAng); 
	        ourSystem.drawSystem(mc); // Redraw the system
	        drawStatus(); // Update the status panel
	 		}
	    });

	    return new HBox(btnRandomEarth, btnStart, btnPause);
	}
	
	@Override
	public void start(Stage stagePrimary) throws Exception {
		stagePrimary.setTitle("Solar System BorderPane Demonstrator");

	    BorderPane bp = new BorderPane();			// create border pane

	    bp.setTop(setMenu());						// create menu, add to top

	    Group root = new Group();					// create group
	    Canvas canvas = new Canvas( canvasSize, canvasSize );
	    											// and canvas to draw in
	    root.getChildren().add( canvas );			// and add canvas to group
	    mc = new MyCanvas(canvas.getGraphicsContext2D(), canvasSize, canvasSize);
					// create MyCanvas passing context on canvas onto which images put
	    ourSystem = new SimpleSolar();				// create object for sun, planets, etc
	    
	    setMouseEvents(canvas);						// set mouse handler
	    bp.setCenter(root);							// put group in centre pane

	    rtPane = new VBox();						// set vBox for listing data
	    bp.setRight(rtPane);						// put in right pane

	    bp.setBottom(setButtons());					/// add button to bottom

	    Scene scene = new Scene(bp, canvasSize*1.5, canvasSize*1.2);
	    								// create scene so bigger than canvas, 

	    stagePrimary.setScene(scene);
	    stagePrimary.show();
	}

	public static void main(String[] args) {
	    Application.launch(args);
	}

}
