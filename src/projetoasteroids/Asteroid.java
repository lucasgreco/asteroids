/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetoasteroids;

import java.util.Random;
import jplay.GameObject;
/**
 *
 * @author PHILLIPE SIMOES
 */
public class Asteroid extends entidade{
    private static final double MIN_ROTATION = 0.0075;
	
	//rotacao do asteroid
    private static final double MAX_ROTATION = 0.0175;
	
	//variacao da rotacao do asteroid
    private static final double ROTATION_VARIANCE = MAX_ROTATION - MIN_ROTATION;
    
    private static final double MIN_VELOCITY = 0.75;
    
    private static final double MAX_VELOCITY = 1.65;

    private static final double VELOCITY_VARIANCE = MAX_VELOCITY - MIN_VELOCITY;
    
    private static final double MIN_DISTANCE = 200.0;
    
    private static final double MAX_DISTANCE = ProjetoAsteroids.WORLD_SIZEY / 2.0;
    
    private static final double DISTANCE_VARIANCE = MAX_DISTANCE - MIN_DISTANCE;
    
    private static final float SPAWN_UPDATES = 10;
    
    private AsteroidSize size;
    
    private double rotationSpeed;
    
    protected double rotation;
    private int vida;

    
    public Asteroid(Random random) {
		super(AsteroidSize.Large.sprite,1,calculatePosition(random), calculateVelocity(random), AsteroidSize.Large.raio, AsteroidSize.Large.killValue);
		this.rotationSpeed = -MIN_ROTATION + (random.nextDouble() * ROTATION_VARIANCE);
		this.size = AsteroidSize.Large;
                this.x = calculatePosition(random).x;
                this.y = calculatePosition(random).y;
                this.height = (int)size.raio;
                this.width = (int)size.raio;
                this.velocity = calculateVelocity(random);
                this.vida = this.size.vida;
                
	}
    
    /**
	 * Creates a new Asteroid from a parent Asteroid.
	 * @param parent The parent.
	 * @param size The size.
	 * @param random The Random instance.
	 */
	public Asteroid(Asteroid parent, AsteroidSize size, Random random) {
                super(size.sprite,1,parent.position, calculateVelocity(random), size.raio, size.killValue);
                this.size = size;
                this.vida = this.size.vida;
                this.velocity = calculateVelocity(random).add(parent.velocity);
		this.rotationSpeed = MIN_ROTATION + (random.nextDouble() * ROTATION_VARIANCE);
                this.height = (int)size.raio;
                this.width = (int)size.raio;
		
		/*
		 * While not necessary, calling the update method here makes the asteroid
		 * appear to have a different starting position than it's parent or sibling.
		 */
		for(int i = 0; i < SPAWN_UPDATES; i++) {
			this.mover(null);
		}
	}
	
	/**
	 * Calculates a random valid spawn point for an Asteroid.
	 * @param random The random instance.
	 * @return The spawn point.
	 */
	private static Vector2 calculatePosition(Random random) {
		Vector2 vec = new Vector2(ProjetoAsteroids.WORLD_SIZEX, ProjetoAsteroids.WORLD_SIZEY);
                
		return vec.add(new Vector2(random.nextDouble() * Math.PI * 2).scale(MIN_DISTANCE + random.nextDouble() * DISTANCE_VARIANCE));
	}
	
	/**
	 * Calculates a random valid velocity for an Asteroid.
	 * @param random The random instance.
	 * @return The velocity.
	 */
	private static Vector2 calculateVelocity(Random random) {
		return new Vector2(random.nextDouble() * Math.PI * 2).scale(MIN_VELOCITY + random.nextDouble() * VELOCITY_VARIANCE);
	}
	
	
        @Override
	public void mover(ProjetoAsteroids game) {
                position.x = this.x;
                position.y = this.y;
                position.add(velocity);
		if(position.x < 0.0f) {
			position.x += ProjetoAsteroids.WORLD_SIZEX;
                        this.x += ProjetoAsteroids.WORLD_SIZEX;
		}
		if(position.y < 0.0f) {
			position.y += ProjetoAsteroids.WORLD_SIZEY;
                        this.y += ProjetoAsteroids.WORLD_SIZEY;
		}
		position.x %= ProjetoAsteroids.WORLD_SIZEX;
                this.x %= ProjetoAsteroids.WORLD_SIZEX;
		position.y %= ProjetoAsteroids.WORLD_SIZEY;
                this.y %= ProjetoAsteroids.WORLD_SIZEY;
		rotate(rotationSpeed); //Rotate the image each frame.
                
                this.x = position.x;
                this.y = position.y;
            
	}
        
        public void rotate(double amount) {
		this.rotation += amount;
		this.rotation %= Math.PI * 2;
	}
        
	public int getKillScore() {
		return this.size.killValue;
	}
       
        @Override
	public void handleCollision(ProjetoAsteroids game, GameObject other) {
		//nao colide com outros asteroids
		if(other.getClass() != Asteroid.class) {
			//só cria asteroids filhos de asteroids grandes
                        if (this.vida == 1){
                            if(size != AsteroidSize.Small) {
                                    //determina tamanho do filho
                                    AsteroidSize spawnSize = AsteroidSize.values()[size.ordinal() -1];
				
                            		//cria os filhos.
                                    for(int i = 0; i < 2; i++) {
                                        
                                    	game.registraEntidade(new Asteroid(this, spawnSize, game.getRandom()));
                                    }
                            }
			
                            //deleta o asteroid
                            flagForRemoval();
			
                            //da a pontuacao para o jogador
                            game.addScore(getKillScore());
                        }else{
                            this.vida--;
                        }
		}
	}

}
