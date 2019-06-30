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
	Small("sprites/meteorBrown_small2.png",30, 100,1),
			
	/**
	 * Medium asteroids have a radius of 25, and are worth 50 points.
	 */
	Medium("sprites/meteorBrown_med1.png",45 ,50,2),
	
	/**
	 * Large asteroids have a radius of 40, and are worth 20 points.
	 */
	Large("sprites/meteorBrown_big1.png",100, 20,3);
	
	
	public final String sprite;
        
        public final double raio;
	
	/**
	 * The number of points earned for killing this type of Asteroid.
	 */
	public final int killValue;
	public final int vida;
	/**
	 * Creates a new type of Asteroid.
	 * @param radius The radius.
	 * @param value The kill value.
	 */
	private AsteroidSize(String sprite,int raio, int value, int vida) {
		this.raio = raio;
		this.sprite = sprite;
		this.killValue = value;
                this.vida = vida;
	}
	
	
    
}
