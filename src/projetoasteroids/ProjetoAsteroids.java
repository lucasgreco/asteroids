/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetoasteroids;

import jplay.Window;
import jplay.GameImage;
import jplay.GameObject;
import jplay.Keyboard;
import java.util.List;
import jplay.Sprite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author LUCAS GRECO
 */
public class ProjetoAsteroids {
    
    public static final int WORLD_SIZEX = 1200;
    
    public static final int WORLD_SIZEY = 750;
    
    private static final int DEATH_COOLDOWN_LIMIT = 200;
    
    private static final int RESPAWN_COOLDOWN_LIMIT = 100;
    
    private static final int INVULN_COOLDOWN_LIMIT = 0;
    
    private static final int DISPLAY_LEVEL_LIMIT = 60;
    
    //Lista de entidades na Tela
    private List<entidade> entidades;
    
    //Lista de entidades a serem add na Tela
    private List<entidade> novas_entidades;
    
    //instancia do jogo
    //private Painel jogo;
    
    //nave do jogador
    private Nave nave;

    //Ramdom
    private Random random;
    	
    //vidas
    private int vidas;
	
    //gameover
    private boolean isGameOver;
    
    //restart
    private boolean restartGame;
    
    //se o jogo esta executando
    private boolean executando;
    
    //pontuacao
    private int pontos;
    
    //nivel de dificuldade
    private int level;
    
    //tempo imune
    private int deathCooldown;
    
    private int showLevelCooldown;
    
    private int restartCooldown;
    
    public Window janela;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here   
        ProjetoAsteroids game = new ProjetoAsteroids();
        game.iniciar();
    }
    
    
    private void iniciar() {
        this.random = new Random();
        this.entidades = new LinkedList<>();
	this.novas_entidades = new ArrayList<>();
	this.nave = new Nave();
        this.executando = true;
        //reinicia as variaveis para default
        reiniciar();
        
        this.janela = new Window(WORLD_SIZEX,WORLD_SIZEY);
        Keyboard teclado = janela.getKeyboard();
        GameImage background = new GameImage("sprites/darkPurple.png");
        
        
      
        while(executando){
            
                background.draw();
                updateGame();        
                Iterator<entidade> iter = getEntidades().iterator();
		while(iter.hasNext()) {
			Sprite entidade = iter.next();
			/*
			 * We should only draw the player if it is not dead, so we need to
			 * ensure that the entity can be rendered.
			 */
			if(entidade != getPlayer() || this.canDrawPlayer()) {
				//Draw the entity at it's actual position, and reset the transformation.
				entidade.draw();
				if(entidade != getPlayer()){
                                    entidade.update();
                                }
                                
				double x = (entidade.x < entidade.width) ? entidade.x + WORLD_SIZEX
						: (entidade.x > WORLD_SIZEX - entidade.width) ? entidade.x - WORLD_SIZEX : entidade.x;
				double y = (entidade.y < entidade.height) ? entidade.y + WORLD_SIZEY
						: (entidade.y > WORLD_SIZEY - entidade.height) ? entidade.y - WORLD_SIZEY : entidade.y;
				
				//Draw the entity at it's wrapped position, and reset the transformation.
				if(x != entidade.x || y != entidade.y) {
					entidade.x = x;
                                        entidade.y = y;
                                        entidade.draw();
					
				}
			}	
		}
            janela.update();
 
            
            if (teclado.keyDown(Keyboard.SPACE_KEY) ){
                    this.nave.atira(true);
            } else{
                    this.nave.atira(false);
            }
            
            this.nave.up_pressionado = teclado.keyDown(Keyboard.UP_KEY); // nave.y -= 2;
            
            this.nave.down_pressionado = teclado.keyDown(Keyboard.DOWN_KEY); // nave.y += 2;
            
            if (teclado.keyDown(Keyboard.ESCAPE_KEY) ){
                    executando = false;
            }
            this.nave.left_pressionado = teclado.keyDown(Keyboard.LEFT_KEY); // nave.x-= 3;
           
            this.nave.right_pressionado = teclado.keyDown(Keyboard.RIGHT_KEY); // nave.x+= 3;
            
            janela.delay(5);
            
            
            
            
        }
        janela.exit();
    }
    
    private void reiniciar(){
        this.pontos = 0;
	this.level = 0;
	this.vidas = 3;
	this.deathCooldown = 0;
	this.isGameOver = false;
	this.restartGame = false;
	resetListaEntidades();
    }
    
    private void updateGame() {
        
                /*
		 * Here we add any pending entities to the world.
		 * 
		 * Two lists are required because we will frequently add entities to the
		 * world while we are iterating over them, which causes all sorts of
		 * errors.
		 */
		entidades.addAll(novas_entidades);
		novas_entidades.clear();
                
                /*
		 * Decrement the restart cooldown.
		 */
		if(restartCooldown > 0) {
			this.restartCooldown--;
		}
                
                /*
		 * Decrement the show level cooldown.
		 */
		if(showLevelCooldown > 0) {
			this.showLevelCooldown--;
		}
                
                /*
		 * Restart the game if needed.
		 */
		if(isGameOver && restartGame) {
			//reiniciar();
		}
                
                /*
		 * If the game is currently in progress, and there are no enemies left alive,
		 * we prepare the next level.
		 */
		if(!isGameOver && areEnemiesDead()) {
			//Increment the current level, and set the show level cooldown.
			this.level++;
			this.showLevelCooldown = DISPLAY_LEVEL_LIMIT;
			
			//Reset the entity lists (to remove bullets).
			resetListaEntidades();
			
			//Reset the player's entity to it's default state, and re-enable firing.
			//nave.reset();
			//nave.setFiringEnabled(true);
			
			//Add the asteroids to the world.
			for(int i = 0; i < level + 2; i++) {
				registraEntidade(new Asteroid(random));
			}
		}
                
                /*
		 * If the player has recently died, decrement the cooldown and handle any
		 * special cases when they occur.
		 */
		if(deathCooldown > 0) {
			this.deathCooldown--;
			switch(deathCooldown) {
			
			//Reset the entity to it's default spawn state, and disable firing.
			case RESPAWN_COOLDOWN_LIMIT:
				//nave.reset();
				//nave.setFiringEnabled(false);
				break;
			
			//Re-enable the ability to fire, as we're no longer invulnerable.
			case INVULN_COOLDOWN_LIMIT:
				//nave.setFiringEnabled(true);
				break;
			
			}
		}
                
                /*
		 * Only run any of the update code if we're not currently displaying the
		 * level to the player.
		 */
		if(showLevelCooldown == 0) {
                    //Iterate through the Entities and update their states.
			for(entidade entity : entidades) {
				entity.mover(this);
                                entity.draw();
			}
                        
                        for(int i = 0; i < entidades.size(); i++) {
				Sprite a = entidades.get(i);
				for(int j = i + 1; j < entidades.size(); j++) {
					Sprite b = entidades.get(j);
					if(i != j && a.collided(b) && ((a != nave && b != nave) || deathCooldown <= INVULN_COOLDOWN_LIMIT)) {
						//a.handleCollision(this, b);
						//b.handleCollision(this, a);
					}
				}
			}
                        
                        Iterator<entidade> iter = entidades.iterator();
			//while(iter.hasNext()) {
			//	if(iter.next().needsRemoval()) {
			//	iter.remove();
			//      }
			//}
		}
	}
    
    private boolean areEnemiesDead() {
		for(Sprite e : entidades) {
			if(e.getClass() == Asteroid.class) {
				return false;
			}
		}
		return true;
	}
    
    private void resetListaEntidades(){
        novas_entidades.clear();
	entidades.clear();
	entidades.add(nave);
    }
    
    private boolean canDrawPlayer(){
        return (deathCooldown <= RESPAWN_COOLDOWN_LIMIT);
    }

    private Sprite getPlayer() {
        return this.nave;
    }
    
    public List<entidade> getEntidades() {
		return entidades;
    }
   
    public void addScore(int score) {
		this.pontos += score;
	}
    
    public Random getRandom() {
		return random;
	}
    public void registraEntidade(entidade entity) {
		novas_entidades.add(entity);
	}
}
