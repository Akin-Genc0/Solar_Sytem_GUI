package application;

public class Satellite extends Planet { 
	
	private Planet orbitingPlanet;
	
	public Satellite( String newName, String newImagePath, double newSize, double newOrbitSize, double newOrbitSpeed, Planet orbitingPlanet) {
		
		super(newName, newImagePath, newSize, newOrbitSize, newOrbitSpeed);
		this.orbitingPlanet = orbitingPlanet;
		
		// TODO Auto-generated constructor stub
	}

	public double getXPos() {
        return orbitingPlanet.getXPos() + orbitSize * Math.cos(angle);
        
    }

    // Get the Y position of the planet
    public double getYPos() {
        return orbitingPlanet.getYPos() + orbitSize * Math.sin(angle);
        
    }
     


	

}
