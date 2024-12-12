package application;

import javafx.scene.image.Image; 
public class Planet {

	
	private String name;             
    private double size;             
    private double orbitSize;        
    private double orbitSpeed;       
    private double angle;            
    private Image image;             
	
    
    
    public Planet(String name, String imagePath, double size, double orbitSize, double orbitSpeed) {
        this.name = name;
        this.size = size;
        this.orbitSize = orbitSize;
        this.orbitSpeed = orbitSpeed;
        this.angle = 0; 
        this.image = new Image(getClass().getResourceAsStream(imagePath)); // Load image
    }
    
   public void updatePosition (double tAng) {
	   
	   angle += tAng * orbitSpeed;
	     
   } 
    

   public double getXPos() {
	   return size * Math.cos(angle);
	   
   }
   
	public double getYPos() {
		 return size * Math.sin(angle);
	   }
    
	
	public void drawPlanet(SolarSystem s, MyCanvas mc) {
		s.drawImage(mc, image, getXPos(), getYPos(), size);
	}
	
	public String toString() {
		return "Planet " + name + "angle=" + angle + ", orbitSize=" + orbitSize + ", speed=" + orbitSpeed;

		}
	
	
	
	
	
}
