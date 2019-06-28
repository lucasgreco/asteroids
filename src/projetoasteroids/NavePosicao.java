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

public enum NavePosicao {
    
    /**
     * 
     * direcoes de acordo com os respectivos sprites
     * 
     */
    
        Angulo_90(0,-(Math.PI*8)/16),        
        Angulo_101_25(1,-(Math.PI*9)/16),       
        Angulo_112_5(2,-(Math.PI*10)/16),      
        Angulo_123_75(3,-(Math.PI*11)/16),     
        Angulo_135(4,-(Math.PI*12)/16),     
        Angulo_146_25(5,-(Math.PI*13)/16),     
        Angulo_157_5(6,-(Math.PI*14)/16),    
        Angulo_168_75(7,-(Math.PI*15)/16),     
        Angulo_180(8,-(Math.PI*16)/16),      
        Angulo_191_25(9,-(Math.PI*17)/16),     
        Angulo_202_5(10,-(Math.PI*18)/16),     
        Angulo_213_75(11,-(Math.PI*19)/16),      
        Angulo_225(12,-(Math.PI*20)/16),
        Angulo_236_25(13,-(Math.PI*21)/16),
        Angulo_247_5(14,-(Math.PI*22)/16),
        Angulo_258_75(15,-(Math.PI*23)/16),
        Angulo_270(16,-(Math.PI*24)/16),
        Angulo_281_25(17,-(Math.PI*25)/16),
        Angulo_292_5(18,-(Math.PI*26)/16),
        Angulo_303_75(19,-(Math.PI*27)/16),
        Angulo_315(20,-(Math.PI*28)/16),
        Angulo_326_25(21,-(Math.PI*29)/16),
        Angulo_337_5(22,-(Math.PI*30)/16),
        Angulo_348_75(23,-(Math.PI*31)/16),
        Angulo_360(24,-(Math.PI*32)/16),
        Angulo_11_25(25,-(Math.PI*1)/16),
        Angulo_22_5(26,-(Math.PI*2)/16),
        Angulo_33_75(27,-(Math.PI*3)/16),
        Angulo_45(28,-(Math.PI*4)/16),
        Angulo_56_25(29,-(Math.PI*5)/16),
        Angulo_67_5(30,-(Math.PI*6)/16),
        Angulo_78_75(31,-(Math.PI*7)/16);
        
        
        
	public int frame;
        public double angulo;
       
	
	/**
	 * Creates a new type of Asteroid.
	 * @param frame The radius.
	 * @param angulo The kill value.
	 */
	 NavePosicao(int frame, double angulo) {
		this.frame = frame;
		this.angulo = angulo;
        }
	
	public NavePosicao getAnterior(NavePosicao atual){
            NavePosicao anterior;
            if(atual == Angulo_78_75){
                anterior = Angulo_90;
            }else{
                anterior = NavePosicao.values()[atual.ordinal() +1];
            }
            return anterior;
        }
    
        
        public NavePosicao getProx(NavePosicao atual){
             NavePosicao proximo;
            if(atual == Angulo_90){
                proximo = Angulo_78_75;
            }else{
                proximo = NavePosicao.values()[atual.ordinal() -1];
            }      
            return proximo;
        }
}
