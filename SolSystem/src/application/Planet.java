package application;

import javafx.scene.image.Image;

public class Planet {

    private String name;
    private double size;
    protected double orbitSize;
    private double orbitSpeed;
    protected double angle;
    private Image image;

    // Constructor
    public Planet(String newName, String newImagePath, double newSize, double newOrbitSize, double newOrbitSpeed) {
        name = newName;
        size = newSize;
        orbitSize = newOrbitSize;
        orbitSpeed = newOrbitSpeed;
        angle = 0; // Default starting angle
        image = new Image(getClass().getResourceAsStream(newImagePath)); // Load image
    }

    // Update the position of the planet
    public void updatePosition(double tAng) {
        angle = tAng * orbitSpeed;  
       
    }

    // Get the X position of the planet
    public double getXPos() {
        return orbitSize * Math.cos(angle);
    }

    // Get the Y position of the planet
    public double getYPos() {
        return orbitSize * Math.sin(angle);
    }

    // Draw the planet
    public void drawPlanet(SolarSystem solarSystem, MyCanvas mc) {
        solarSystem.drawImage(mc, image, getXPos(), getYPos(), size);
    }

    // Override toString to return useful information about the planet
    public String toString() {
        return "Planet " + name + ": angle=" + angle + ", orbitSize=" + orbitSize + ", speed=" + orbitSpeed;
    }
}

