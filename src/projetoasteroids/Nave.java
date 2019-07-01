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
	
	//taxa de aceleracao
	private static final double THRUST_MAGNITUDE = 0.0385;
	
	//max velocidade
	private static final double MAX_VELOCITY_MAGNITUDE = 6.5;
	
	
	
	//taxa de reduçao de velocidade
	private static final double SLOW_RATE = 0.995;
	
	//maximo de tiros simultaneos
	private static final int MAX_BULLETS = 4;
	
	//tempo entre tiros
	private static final int FIRE_RATE = 4;
	
	//maximo de tiros consecultivos
	private static final int MAX_CONSECUTIVE_SHOTS = 8;
	
	//maximo de tempo de espera
	private static final int MAX_OVERHEAT = 30;
	private int CONTADOR_VIRAR;
	
	
    
        
        public boolean up_pressionado;
    
        public boolean down_pressionado;
    
        public boolean left_pressionado;
    
        public boolean right_pressionado;
        
        public NavePosicao direcao;
        
        private List<Bullet> bullets;
        
       	private boolean firePressed;
		
	
	private boolean firingEnabled;
	
	
	private int consecutiveShots;
	
	
	private int fireCooldown;
	
	
	private int overheatCooldown;
        
        
        //alterna tiros entre direita e esquerda
        public boolean alterna;
        
    
    public Nave() {
        super("sprites/navespritesheet.png",32,new Vector2(ProjetoAsteroids.WORLD_SIZEX / 2.0, ProjetoAsteroids.WORLD_SIZEY / 2.0), new Vector2(0.0, 0.0), 10, 0);
        this.bullets = new ArrayList<>();
        this.x = (ProjetoAsteroids.WORLD_SIZEX / 2.0) - 50;
        this.y = ProjetoAsteroids.WORLD_SIZEY / 2.0;
	this.left_pressionado = false;
	this.right_pressionado = false;
	this.firePressed = false;
	this.firingEnabled = true;
	this.fireCooldown = 0;
	this.overheatCooldown = 0;
        this.direcao = Angulo_90;
        this.CONTADOR_VIRAR = 7;
       
    }
    
    public void atira(boolean estado){
        this.firePressed = estado;
    }
    
   
    @Override
    public void handleCollision(ProjetoAsteroids game, GameObject other) {
       if(other.getClass() == Asteroid.class) {
			game.killPlayer();
		}
    }
    
    @Override
    public void mover(ProjetoAsteroids game){
                super.mover(game);
		
		//rotaciona nave quando aperta para os lados
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
		
		//acelera para direçao correta quando aperta up
                
		if(up_pressionado) {
			
		
                
			velocity.add(new Vector2(direcao.angulo).scale(THRUST_MAGNITUDE));
			
                
			if(velocity.getLengthSquared() >= MAX_VELOCITY_MAGNITUDE * MAX_VELOCITY_MAGNITUDE) {
				velocity.normalize().scale(MAX_VELOCITY_MAGNITUDE);
			}
		}
		
		//diminui a velocidade
                
		if(velocity.getLengthSquared() != 0.0) {
			velocity.scale(SLOW_RATE);
		}
		
		//remove as balas se necessario
                
		Iterator<Bullet> iter = bullets.iterator();
		while(iter.hasNext()) {
			Bullet bullet = iter.next();
			if(bullet.needsRemoval()) {
				iter.remove();
			}
		}
		
		//cuida do cooldown de atirar
                
		this.fireCooldown--;
		this.overheatCooldown--;
		if(firingEnabled && firePressed && fireCooldown <= 0 && overheatCooldown <= 0) {
			
                
			if(bullets.size() < MAX_BULLETS) {
				this.fireCooldown = FIRE_RATE;
				
				Bullet bullet = new Bullet(this);
				bullets.add(bullet);
				game.registraEntidade(bullet);
                                alterna = !alterna;
			}
			
			
                
			this.consecutiveShots++;
			if(consecutiveShots == MAX_CONSECUTIVE_SHOTS) {
				this.consecutiveShots = 0;
				this.overheatCooldown = MAX_OVERHEAT;
			}
		} else if(consecutiveShots > 0) {
			
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
