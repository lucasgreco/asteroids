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
	//sprite / raio / valor de pontos / vida
	
	Small("sprites/meteorBrown_small2.png",30, 100,1),
			
	
	Medium("sprites/meteorBrown_med1.png",45 ,50,2),
	
	Large("sprites/meteorBrown_big1.png",100, 20,3);
	
	
	public final String sprite;    
        public final double raio;
	public final int killValue;
	public final int vida;
        
	
	private AsteroidSize(String sprite,int raio, int value, int vida) {
		this.raio = raio;
		this.sprite = sprite;
		this.killValue = value;
                this.vida = vida;
	}
	
	
    
}
