/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetoasteroids;

/**
 *
 * @author Lucas
 */
public class Numeros {
    public int numero;
    public String sprite;
    


    Numeros(int numero){
        switch (numero) {
            case 0:
            this.sprite = "sprites/sprites fonte/PNG/UI/numeral0.png";
            break;
            case 1:
            this.sprite = "sprites/sprites fonte/PNG/UI/numeral1.png";
            break;
            case 2:
            this.sprite = "sprites/sprites fonte/PNG/UI/numeral2.png";
            break;
            case 3:
            this.sprite = "sprites/sprites fonte/PNG/UI/numeral3.png";
            break;
            case 4:
            this.sprite = "sprites/sprites fonte/PNG/UI/numeral4.png";
            break;
            case 5:
            this.sprite = "sprites/sprites fonte/PNG/UI/numeral5.png";
            break;
            case 6:
            this.sprite = "sprites/sprites fonte/PNG/UI/numeral6.png";
            break;
            case 7:
            this.sprite = "sprites/sprites fonte/PNG/UI/numeral7.png";
            break;
            case 8:
            this.sprite = "sprites/sprites fonte/PNG/UI/numeral8.png";
            break;
            case 9:
            this.sprite = "sprites/sprites fonte/PNG/UI/numeral9.png";
            break;
        }
    }
    
    Numeros(char numero){
        switch (numero) {
            case '0':
            this.sprite = "sprites/sprites fonte/PNG/UI/numeral0.png";
            break;
            case '1':
            this.sprite = "sprites/sprites fonte/PNG/UI/numeral1.png";
            break;
            case '2':
            this.sprite = "sprites/sprites fonte/PNG/UI/numeral2.png";
            break;
            case '3':
            this.sprite = "sprites/sprites fonte/PNG/UI/numeral3.png";
            break;
            case '4':
            this.sprite = "sprites/sprites fonte/PNG/UI/numeral4.png";
            break;
            case '5':
            this.sprite = "sprites/sprites fonte/PNG/UI/numeral5.png";
            break;
            case '6':
            this.sprite = "sprites/sprites fonte/PNG/UI/numeral6.png";
            break;
            case '7':
            this.sprite = "sprites/sprites fonte/PNG/UI/numeral7.png";
            break;
            case '8':
            this.sprite = "sprites/sprites fonte/PNG/UI/numeral8.png";
            break;
            case '9':
            this.sprite = "sprites/sprites fonte/PNG/UI/numeral9.png";
            break;
        }
    }
    
    public String getSprite(){
        return sprite;
    }
} 

