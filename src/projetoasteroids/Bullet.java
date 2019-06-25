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
        super("sprites/laserBlue16.png", 1, origem.position,new Vector2(origem.direcao.angulo).scale(VELOCITY_MAGNITUDE), 2.0,0);
        this.direcao = origem.direcao;
        if(origem.alterna){
            this.position.add(new Vector2(this.direcao.angulo));
            //this.x = position.scale(this.direcao.angulo).x;
            //this.y = position.scale(this.direcao.angulo).y;
        }else{
            this.position.x +=1;
            this.x = position.scale(this.direcao.angulo).x;
            //this.y = position.scale(this.direcao.angulo).y;
        }
        this.lifespan = MAX_LIFESPAN;
    }
    
    @Override
	public void mover(ProjetoAsteroids game) {
		super.mover(game);
		
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
