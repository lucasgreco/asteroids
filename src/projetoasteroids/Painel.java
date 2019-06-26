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
 * @author Lucas Greco
 */
public class Painel extends entidade{

    public Numeros numero;
    
    public Painel(String fileName, Vector2 position) {
        super(fileName, 1, position, null, 0, 0);
    }

    @Override
    public void handleCollision(ProjetoAsteroids game, GameObject other) {
        
    }

    
    
}
