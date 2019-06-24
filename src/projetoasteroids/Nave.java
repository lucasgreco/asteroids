/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetoasteroids;

import java.util.Iterator;
import jplay.GameObject;
import jplay.Sprite;

/**
 *
 * @author Lucas
 */
public class Nave extends entidade{
    
    /**
     *
     */
    public Nave() {
        super("sprites/playerShip1_blue.png");
        this.x = 345;
        this.y = 450;
        
    }
    
    public void atira(boolean estado){
        
    }
    
    
    public void update(ProjetoAsteroids game){
        this.update();
    }

    @Override
    public void handleCollision(ProjetoAsteroids game, GameObject other) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void mover(ProjetoAsteroids game){
                super.mover(game);
		
		//Increment the animation frame.
		this.animationFrame++;
		
		/*
		 * Rotate the ship if only one of the rotation flags are true, as doing
		 * one rotation will cancel the effect of doing the other.
		 * 
		 * The conditional statement can alternatively be written like this:
		 * 
		 * if(rotateLeftPressed) {
		 *     rotate(-ROTATION_SPEED);
		 * } else {
		 *     rotate(ROTATION_SPEED);
		 * }
		 */
		if(rotateLeftPressed != rotateRightPressed) {
			rotate(rotateLeftPressed ? -ROTATION_SPEED : ROTATION_SPEED);
		}
		
		/*
		 * Apply thrust to our ship's velocity, and ensure that the ship is not
		 * going faster than the maximum magnitude.
		 */
		if(thrustPressed) {
			/*
			 * Here we create a new vector based on our ship's rotation, and scale
			 * it by our thrust's magnitude. Then we add that vector to our velocity.
			 */
			velocity.add(new Vector2(rotation).scale(THRUST_MAGNITUDE));
			
			/*
			 * Here we determine whether our ship is going faster than is
			 * allowed. Like when checking for collisions, we check the squared
			 * magnitude because it is quicker to square a value than it is to
			 * take the square root.
			 * 
			 * If our velocity exceeds our maximum allowed velocity, we normalize
			 * it (giving it a magnitude of 1.0), and scale it to be he maximum.
			 */
			if(velocity.getLengthSquared() >= MAX_VELOCITY_MAGNITUDE * MAX_VELOCITY_MAGNITUDE) {
				velocity.normalize().scale(MAX_VELOCITY_MAGNITUDE);
			}
		}
		
		/*
		 * If our ship is moving, slow it down slightly, which causes the ship
		 * to some to a gradual stop.
		 */
		if(velocity.getLengthSquared() != 0.0) {
			velocity.scale(SLOW_RATE);
		}
		
		/*
		 * Loop through each bullet and remove it from the list if necessary.
		 */
		Iterator<Bullet> iter = bullets.iterator();
		while(iter.hasNext()) {
			Bullet bullet = iter.next();
			if(bullet.needsRemoval()) {
				iter.remove();
			}
		}
		
		/*
		 * Decrement the fire and overheat cooldowns, and determine if we can fire another
		 * bullet.
		 */
		this.fireCooldown--;
		this.overheatCooldown--;
		if(firingEnabled && firePressed && fireCooldown <= 0 && overheatCooldown <= 0) {
			/*
			 * We can only create a new bullet if we haven't yet exceeded the
			 * maximum number of bullets that we can have fired at once.
			 * 
			 * If a new bullet can be fired, we reset the fire cooldown, and
			 * register a new bullet to the game world.
			 */
			if(bullets.size() < MAX_BULLETS) {
				this.fireCooldown = FIRE_RATE;
				
				Bullet bullet = new Bullet(this, rotation);
				bullets.add(bullet);
				game.registerEntity(bullet);
			}
			
			/*
			 * Since we're attempting to fire a bullet, we increment the number
			 * of consecutive shots and determine if we should set the overheat
			 * flag.
			 * 
			 * This prevents us from being able to wipe out entire groups of
			 * asteroids in one burst if we're accurate enough, and will prevent
			 * us from firing a continuous stream of bullets until we start missing.
			 */
			this.consecutiveShots++;
			if(consecutiveShots == MAX_CONSECUTIVE_SHOTS) {
				this.consecutiveShots = 0;
				this.overheatCooldown = MAX_OVERHEAT;
			}
		} else if(consecutiveShots > 0) {
			//Decrement the number of consecutive shots, since we're not trying to fire.
			this.consecutiveShots--;
		}
                
                
                }
    
    
}
