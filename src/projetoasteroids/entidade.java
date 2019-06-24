/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetoasteroids;

import jplay.GameObject;
import jplay.Sprite;

/**
 *
 * @author Lucas
 */

public abstract class entidade extends Sprite{
    
    protected Vector2 velocity;
    
    
    protected Vector2 position;
    
    protected double radius;
    
    private boolean needsRemoval;
    
    private int killScore;

    public entidade(String fileName) {
        super(fileName);
    }
    
    
    public abstract void handleCollision(ProjetoAsteroids game, GameObject other);
    
    public void mover(ProjetoAsteroids game){
                position.add(velocity);
		if(position.x < 0.0f) {
			position.x += ProjetoAsteroids.WORLD_SIZEX;
		}
		if(position.y < 0.0f) {
			position.y += ProjetoAsteroids.WORLD_SIZEY;
		}
		position.x %= ProjetoAsteroids.WORLD_SIZEX;
		position.y %= ProjetoAsteroids.WORLD_SIZEY;
    }
    
}
