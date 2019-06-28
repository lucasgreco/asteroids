/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetoasteroids;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import jplay.GameObject;
import jplay.Sprite;
import static projetoasteroids.NavePosicao.Angulo_90;


/**
 *
 * @author Lucas
 */
public class Nave extends entidade{
    
    private static final double DEFAULT_ROTATION = -Math.PI / 2.0;
	
	/**
	 * The magnitude of our ship's thrust.
	 */
	private static final double THRUST_MAGNITUDE = 0.0385;
	
	/**
	 * The maximum speed at which our ship can travel.
	 */
	private static final double MAX_VELOCITY_MAGNITUDE = 6.5;
	
	/**
	 * The speed at which the ship rotates.
	 */
	private static final double ROTATION_SPEED = 0.05;
	
	/**
	 * The factor at which our ship slows down.
	 */
	private static final double SLOW_RATE = 0.995;
	
	/**
	 * The maximum number of bullets that can be fired at once.
	 */
	private static final int MAX_BULLETS = 4;
	
	/**
	 * The number of cycles that must elapse between shots.
	 */
	private static final int FIRE_RATE = 4;
	
	/**
	 * The maximum number of shots that can be fired consecutively before
	 * overheating.
	 */
	private static final int MAX_CONSECUTIVE_SHOTS = 8;
	
	/**
	 * The number of cycles that must elapse before we stop overheating.
	 */
	private static final int MAX_OVERHEAT = 30;
	private int CONTADOR_VIRAR;
	/**
	 * Whether the ship should apply thrust when it updates.
	 */
	
        //public int direcao;
        
        public boolean up_pressionado;
    
        public boolean down_pressionado;
    
        public boolean left_pressionado;
    
        public boolean right_pressionado;
        
        public NavePosicao direcao;
        
        private List<Bullet> bullets;
        
        /**
	 * Whether the ship should fire a bullet when it updates.
	 */
	private boolean firePressed;
		
	/**
	 * Whether the ship is allowed to fire a bullet.
	 */
	private boolean firingEnabled;
	
	/**
	 * The number of consecutive shots fired.
	 */
	private int consecutiveShots;
	
	/**
	 * The cooldown timer for firing.
	 */
	private int fireCooldown;
	
	/**
	 * The cooldown timer for overheating.
	 */
	private int overheatCooldown;
        
        
        //alterna tiros entre direita e esquerda
        public boolean alterna;
        
    /**
     *
     */
    public Nave() {
      //  super("sprites/playerShip1_blue.png",2,new Vector2(ProjetoAsteroids.WORLD_SIZEX / 2.0, ProjetoAsteroids.WORLD_SIZEY / 2.0), new Vector2(0.0, 0.0), 10.0, 0);
        super("sprites/navespritesheet.png",32,new Vector2(ProjetoAsteroids.WORLD_SIZEX / 2.0, ProjetoAsteroids.WORLD_SIZEY / 2.0), new Vector2(0.0, 0.0), 10, 0);
        this.bullets = new ArrayList<>();
        //       super("sprites/playerShip1_blue.png");
//        this.position.x = 645;
 //       this.position.y = 450;
        this.x = (ProjetoAsteroids.WORLD_SIZEX / 2.0) - 50;
        this.y = ProjetoAsteroids.WORLD_SIZEY / 2.0;
        //this.rotation = DEFAULT_ROTATION;
        
	this.left_pressionado = false;
	this.right_pressionado = false;
	this.firePressed = false;
	this.firingEnabled = true;
	this.fireCooldown = 0;
	this.overheatCooldown = 0;
	//this.animationFrame = 0;
        this.direcao = Angulo_90;
        //this.rotation = direcao.angulo;
        //this.frame = direcao.frame;
        //this.frame = 0;
        this.CONTADOR_VIRAR = 7;
       
    }
    
    public void atira(boolean estado){
        this.firePressed = estado;
    }
    
    /*
    public void update(ProjetoAsteroids game){
        this.update();
    }
*/
    @Override
    public void handleCollision(ProjetoAsteroids game, GameObject other) {
       if(other.getClass() == Asteroid.class) {
			game.killPlayer();
		}
    }
    
    @Override
    public void mover(ProjetoAsteroids game){
                super.mover(game);
		
		//Increment the animation frame.
		//this.animationFrame++;
		
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
		if(left_pressionado != right_pressionado) {
                    if(CONTADOR_VIRAR > 0){
                        CONTADOR_VIRAR--;
                    }else {
                      CONTADOR_VIRAR = 7;  
                                     
                    if(left_pressionado){
                        direcao = direcao.getAnterior(direcao);
                        
                    }else{
                        direcao = direcao.getProx(direcao);
                        
                    }
                    }
                    setCurrFrame(direcao.frame);
			
		}
		
		/*
		 * Apply thrust to our ship's velocity, and ensure that the ship is not
		 * going faster than the maximum magnitude.
		 */
                
		if(up_pressionado) {
			/*
			 * Here we create a new vector based on our ship's rotation, and scale
			 * it by our thrust's magnitude. Then we add that vector to our velocity.
			 */
                
			velocity.add(new Vector2(direcao.angulo).scale(THRUST_MAGNITUDE));
			
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
				
				Bullet bullet = new Bullet(this);
				bullets.add(bullet);
				game.registraEntidade(bullet);
                                alterna = !alterna;
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
    
    public void setFiringEnabled(boolean state) {
		this.firingEnabled = state;
	}
    
    public void reset() {
		this.rotation = DEFAULT_ROTATION;
		this.position.set(ProjetoAsteroids.WORLD_SIZEX / 2.0 - 50, ProjetoAsteroids.WORLD_SIZEY / 2.0);
                this.x = position.x;
                this.y = position.y;
		velocity.set(0.0, 0.0);
		bullets.clear();
	}
    
}
