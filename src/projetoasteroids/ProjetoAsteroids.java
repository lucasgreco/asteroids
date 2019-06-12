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
    
    //Lista de entidades na Tela
    private List<Sprite> entidades;
    
    //Lista de entidades a serem add na Tela
    private List<Sprite> novas_entidades;
    
    //instancia do jogo
    //private Painel jogo;
    
    //nave do jogador
    private Nave nave;

    //Ramdom
    private Random random;
    
    //pontuação
    private int pontuacao;
	
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
                        
                Iterator<Sprite> iter = getEntidades().iterator();
		while(iter.hasNext()) {
			Sprite entidade = iter.next();
			/*
			 * We should only draw the player if it is not dead, so we need to
			 * ensure that the entity can be rendered.
			 */
			if(entidade != getPlayer() || this.canDrawPlayer()) {
				//Draw the entity at it's actual position, and reset the transformation.
				entidade.draw();
				
                                
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
            }
            
            if (teclado.keyDown(Keyboard.UP_KEY) ){
                    nave.y -= 2;
            }
            
            if (teclado.keyDown(Keyboard.DOWN_KEY) ){
                    nave.y += 2;
            }
            
            if (teclado.keyDown(Keyboard.ESCAPE_KEY) ){
                    executando = false;
            }
            if (teclado.keyDown(Keyboard.LEFT_KEY) ){
                    nave.x-= 3;
            }
            if (teclado.keyDown(Keyboard.RIGHT_KEY) ){
                    nave.x+= 3;
            }
            
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
    
    public List<Sprite> getEntidades() {
		return entidades;
    }
    
}
