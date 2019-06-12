/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetoasteroids;



/**
 *
 * @author Lucas
 */

public enum AsteroidSize {
	
	/**
	 * Small Asteroids have a radius of 15, and are worth 100 points.
	 */
	Small("sprite/meteorBrown_small2.png", 100),
			
	/**
	 * Medium asteroids have a radius of 25, and are worth 50 points.
	 */
	Medium("sprite/meteorBrown_med1.png", 50),
	
	/**
	 * Large asteroids have a radius of 40, and are worth 20 points.
	 */
	Large("sprite/meteorBrown_big1.png", 20);
	
	
	public final String sprite;
	
	/**
	 * The number of points earned for killing this type of Asteroid.
	 */
	public final int killValue;
	
	/**
	 * Creates a new type of Asteroid.
	 * @param radius The radius.
	 * @param value The kill value.
	 */
	private AsteroidSize(String sprite, int value) {
		//this.polygon = generatePolygon(radius);
		this.sprite = sprite;
		this.killValue = value;
	}
	
	
    
}
