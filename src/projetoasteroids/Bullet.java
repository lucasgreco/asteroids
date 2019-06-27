/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetoasteroids;

import jplay.GameObject;

/**
 *
 * @author UFASA
 */
public class Bullet extends entidade{
    
    
        private static final double VELOCITY_MAGNITUDE = 6.75;
	
	/**
	 * The maximum number of cycles that a Bullet can exist.
	 */
	private static final int MAX_LIFESPAN = 60;
	
	/**
	 * The number of cycles this Bullet has existed.
	 */
	private int lifespan;

        public NavePosicao direcao;
        
    public Bullet(Nave origem) {   
        super("sprites/lasespritesheet.png", 32, origem.position,new Vector2(origem.direcao.angulo).scale(VELOCITY_MAGNITUDE).add(origem.velocity), 2.0,0);
        this.direcao = origem.direcao;
        this.position.add(new Vector2(origem.direcao.angulo).scale(40));
        if(origem.alterna){
            this.position.add(new Vector2(origem.direcao.angulo + Math.PI/2).scale(17));
        }
        //this.position.y += 10* Math.sin(direcao.angulo - Math.PI/2);
        this.lifespan = MAX_LIFESPAN;
        this.x = position.x;
        this.y = position.y;
    }
    
    @Override
	public void mover(ProjetoAsteroids game) {
		super.mover(game);
		setCurrFrame(direcao.frame);
		//Decrement the lifespan of the bullet, and remove it if needed.
		this.lifespan--;
		if(lifespan <= 0) {
			flagForRemoval();
		}
	}

	@Override
	public void handleCollision(ProjetoAsteroids game, GameObject other) {
		if(other.getClass() != Nave.class) {
			flagForRemoval();
		}
	}
    

    
    
}
