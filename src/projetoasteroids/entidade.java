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
    
    protected double rotation;
    
    protected Vector2 position;
    
    protected double radius;
    
    public boolean needsRemoval;
    
    public int killScore;
    
    public double frame;

    public entidade(String fileName,int frames, Vector2 position, Vector2 velocity, double radius, int killScore) {
        super(fileName, frames);
        this.position = position;
        this.x = this.position.x;
        this.y = this.position.y;
	this.velocity = velocity;
	this.radius = radius;
	this.rotation = 0.0f;
	this.killScore = killScore;
	this.needsRemoval = false;
    }
    
    
    public abstract void handleCollision(ProjetoAsteroids game, GameObject other);
    
    public void mover(ProjetoAsteroids game){
                this.position.x = this.x;
                this.position.y = this.y;
                position.add(velocity);
		if(position.x < 0.0f) {
			position.x += game.WORLD_SIZEX;
		}
		if(position.y < 0.0f) {
			position.y += game.WORLD_SIZEY;
		}
		position.x %= game.WORLD_SIZEX;
		position.y %= game.WORLD_SIZEY;
                this.x = position.x;
                this.y = position.y;
    }

    public boolean needsRemoval() {
		return needsRemoval;
	}
    public void rotate(double amount) {
		this.rotation += amount;
		this.rotation %= Math.PI * 2;
	}
}
