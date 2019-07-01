/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetoasteroids;


import java.awt.FontFormatException;
import jplay.Window;
import jplay.GameImage;
import jplay.Keyboard;
import java.util.List;
import jplay.Sprite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;


/**
 *
 * @author PHELLIPE SIMOES E LUCAS GRECO
 */
public class ProjetoAsteroids {
    
    //resolução da janela
    public static final int WORLD_SIZEX = 1280;
    //resolução da janela
    public static final int WORLD_SIZEY = 768;
    //tempo morto
    private static final int DEATH_COOLDOWN_LIMIT = 200;
    //tempo para poder jogar apos renascer
    private static final int RESPAWN_COOLDOWN_LIMIT = 100;
    //tempo invuneravel apos renascer
    private static final int INVULN_COOLDOWN_LIMIT = 0;
    //tempo que mostra o 'passou de nivel'
    private static final int DISPLAY_LEVEL_LIMIT = 200;
    //tempo de recomeçar o jogo apos perder
    private static final int RESET_COOLDOWN_LIMIT = 200;
    
    //Lista de entidades na Tela
    private List<entidade> entidades;
    
    //Lista de entidades a serem add na Tela
    private List<entidade> novas_entidades;
      
    //nave do jogador
    private Nave nave;

    //Ramdom
    private Random random;
    	
    //vidas
    private int vidas;
	
    //gameover
    private boolean isGameOver;
    
    //se o jogo esta executando
    boolean executando;
    
    //pontuacao
    private Integer pontos;
    
    //nivel de dificuldade
    private int level;
    
    //CONTADORES
    //tempo renascer
    private int deathCooldown;
    //tempo passar de nivel
    private int showLevelCooldown;
    //tempo para reiniciar
    private int restartCooldown;
    
    //instancia da janela
    public Window janela;
    
    /**
     * @param args the command line arguments
     * @throws java.awt.FontFormatException
     */
    public static void main(String[] args) throws FontFormatException{
        // TODO code application logic here   
        ProjetoAsteroids game = new ProjetoAsteroids();
        game.iniciar();
    }
    
    
    private void iniciar() throws FontFormatException {
        this.random = new Random();
        this.entidades = new LinkedList<>();
	this.novas_entidades = new ArrayList<>();
	this.nave = new Nave();
        this.executando = true;
        
        //reinicia as variaveis para default
        reiniciar();
        
        //inicia instancias e objetos da interface
        this.janela = new Window(WORLD_SIZEX,WORLD_SIZEY);
        Keyboard teclado = janela.getKeyboard();
        GameImage background = new GameImage("sprites/darkPurple.png");
        Painel placar_vidaimg = new Painel("sprites/playerLife1_blue.png", new Vector2(20,20));
        Painel placar_vezes = new Painel("sprites/sprites fonte/PNG/UI/numeralX.png", new Vector2(70,27));
        

        //loop do jogo
        while(executando){
            //desenha o fundo e o numero de vidas na tela
                background.draw();
                Painel placar_vida = new Painel(new Numeros(vidas).getSprite(), new Vector2(100,27));
                String pontuacaostring = pontos.toString();
              
               //desenha a pontuação na tela
                for (int i=0; i<pontuacaostring.length(); i++) {
                    char c = pontuacaostring.charAt(i);
                    Painel placar_numero = new Painel(new Numeros(c).getSprite(), new Vector2(WORLD_SIZEX -(25*(i+1)),27));
                    placar_numero.draw();
                }
                
                
                updateGame();        
                Iterator<entidade> iter = getEntidades().iterator();
		while(iter.hasNext()) {
			Sprite entidade = iter.next();
			//apenas desenha o jogador se ele nao estiver morto
			if(entidade != getPlayer() || this.canDrawPlayer()) {
				//desenha a entidade na posicao.
				entidade.draw();
				if(entidade != getPlayer()){
                                    entidade.update();
                                }
                                
				double x = (entidade.x < entidade.width) ? entidade.x + WORLD_SIZEX
						: (entidade.x > WORLD_SIZEX - entidade.width) ? entidade.x - WORLD_SIZEX : entidade.x;
				double y = (entidade.y < entidade.height) ? entidade.y + WORLD_SIZEY
						: (entidade.y > WORLD_SIZEY - entidade.height) ? entidade.y - WORLD_SIZEY : entidade.y;
				
				//desenha a entidade na posicao do outro lado da tela.
				if(x != entidade.x || y != entidade.y) {
					entidade.x = x;
                                        entidade.y = y;
                                        entidade.draw();
					
				}
                                
			}	
		}
                placar_vida.draw();
                placar_vezes.draw();
                placar_vidaimg.draw();
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
            
            janela.delay(3);
            
            
            
            
        }
        janela.exit();
    }
    
    private void reiniciar(){
        this.pontos = 0;
	this.level = 0;
	this.vidas = 3;
	this.deathCooldown = 0;
	this.isGameOver = false;
	resetListaEntidades();
    }
    
    private void updateGame() {
        
                //adiciona entidades pendentes
		entidades.addAll(novas_entidades);
		novas_entidades.clear();
                
                //diminui o contador de reiniciar
		if(restartCooldown > 0) {
			this.restartCooldown--;
                        Painel perdeu = new Painel("sprites/lose.png", new Vector2((WORLD_SIZEX /2.0)-200,(WORLD_SIZEY /2.0)-200));
                        perdeu.draw();
                        String pontuacaostring = pontos.toString();
                       for (int i=0; i<pontuacaostring.length(); i++) {
                            char c = pontuacaostring.charAt(i);
                            Painel placar_numero = new Painel(new Numeros(c).getSprite(), new Vector2((WORLD_SIZEX /2.0)+57 -(25*(i+1)),(WORLD_SIZEY /2.0)-100));
                            placar_numero.draw();
                        }
		}
                
                //diminui o contador de mostrar o nivel
		if(showLevelCooldown > 0) {
			this.showLevelCooldown--;
		}
                
                //reinicia o jogo se necessario
		if(checkForRestart()) {
			reiniciar();
		}
                
                //se o jogo ainda esta rodando e nao tem mais inimigos, passa de nivel
		if(!isGameOver && areEnemiesDead()) {
			//aumenta de nivel e seta o tempo para o maximo.
			this.level++;
			this.showLevelCooldown = DISPLAY_LEVEL_LIMIT;
                        
			//Reseta a lista de entidades removendo as balas da tela.
			resetListaEntidades();
			
			//Reseta o jogador.
			nave.reset();
			nave.setFiringEnabled(true);
			
			//Adiciona os asterois iniciais de acordo com o nivel.
			for(int i = 0; i < level + 2; i++) {
				registraEntidade(new Asteroid(random));
			}
		}
                
                //se o jogador morreu recentemente trata as exceçoes
		 
		if(deathCooldown > 0) {
			this.deathCooldown--;
			switch(deathCooldown) {
			
			//devolve o jogador para a posiçao inicial e desabilita atirar.
			case RESPAWN_COOLDOWN_LIMIT:
				nave.reset();
				nave.setFiringEnabled(false);
				break;
			
			//habilita o jogador a disparar quando acabar o tempo invuneravel.
			case INVULN_COOLDOWN_LIMIT:
				nave.setFiringEnabled(true);
				break;
			
			}
		}
                
                
		  //só roda o update do jogo se nao estiver mais mostrando o nivel para o jogador.
		 
		 
		if(showLevelCooldown == 0) {
                    //Varre a lista de entidades e atualiza o estado
			for(entidade entity : entidades) {
				entity.mover(this);
                                
			}
                        //checa colisão entre todas as entidades possiveis
                        for(int i = 0; i < entidades.size(); i++) {
				entidade a = entidades.get(i);
				for(int j = i + 1; j < entidades.size(); j++) {
					entidade b = entidades.get(j);
					if(i != j && a.collided(b) && ((a != nave && b != nave) || deathCooldown <= INVULN_COOLDOWN_LIMIT)) {
						a.handleCollision(this, b);
						b.handleCollision(this, a);
					}
				}
			}
                      //checa se alguma entidade precisa ser removida do jogo  
                        Iterator<entidade> iter = entidades.iterator();
			while(iter.hasNext()) {
				if(iter.next().needsRemoval()) {
                                    iter.remove();
			      }
			}
		}else{
                    if(this.level > 1){
                        Painel passou_nivel = new Painel("sprites/win.png", new Vector2((WORLD_SIZEX /2.0)-200,(WORLD_SIZEY /2.0)-200));
                        passou_nivel.draw();
                        Painel palavra_nivel = new Painel("sprites/level.png", new Vector2((WORLD_SIZEX /2.0)-40,(WORLD_SIZEY /2.0)-100));
                        palavra_nivel.draw();
                        Painel quantidade_nivel = new Painel(new Numeros(this.level).getSprite(), new Vector2((WORLD_SIZEX /2.0)+70,(WORLD_SIZEY /2.0)-100));
                        quantidade_nivel.draw();
                    }else{
                        Painel passou_nivel = new Painel("sprites/start.png", new Vector2((WORLD_SIZEX /2.0)-150,(WORLD_SIZEY /2.0)-200));
                        passou_nivel.draw();
                    }
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
    
    
    public void killPlayer() {
		//Decrementa as vidas.
		this.vidas--;
	
		/*
		 * Se nao houverem mais vidas restantes prepara o jogo para reiniciar,
		 * se nao prepara o deathcooldown.
		 * 
		 */
		if(vidas == 0) {
			this.isGameOver = true;
			this.restartCooldown = RESET_COOLDOWN_LIMIT;
			this.deathCooldown = Integer.MAX_VALUE;
		} else {
			this.deathCooldown = DEATH_COOLDOWN_LIMIT;
		}
		
		//não deixa o jogador atirar.
		nave.setFiringEnabled(false);
	}
    
    
    private boolean checkForRestart() {
		boolean restart = (isGameOver && restartCooldown <= 0);
		return restart;
	}
    
}
