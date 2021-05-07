# Trabalho de POO 2019.1 UFF - Jogo Asteroids
> Implementação de uma versão do jogo Asteroids


Autor:
```sh
Lucas Greco Garcia Fernandes. 
```

![](header.png)

## Preview

![image](https://user-images.githubusercontent.com/50213462/60403762-e19bac00-9b77-11e9-9452-5ebfd4ec2358.png)

![image](https://user-images.githubusercontent.com/50213462/60403784-0c860000-9b78-11e9-9a47-48bff22781f1.png)

![image](https://user-images.githubusercontent.com/50213462/60403792-1871c200-9b78-11e9-88ef-50a3eb51e5f0.png)

## Especificações

### Para este projeto nós utilizamos:
   * Netbeans IDE 8.2
   * Biblioteca JPlay
## Implementação
  
  #### Objetos do jogo
   * Todos os objetos criados na tela herdam da classe entidade, que por sua vez é uma classe abstrata que extende a classe Sprite do JPlay
   * Cada tipo de objeto em tela tem sua respectiva classe; 
      * Nave 
      * Asteroid 
      * Bullet
      * Painel (classe utilizada para objetos de Interface como pontuação e mensagens na tela).

## Relação entre as classes.

![image](https://user-images.githubusercontent.com/50213462/60403858-004e7280-9b79-11e9-8584-fbb3a0231223.png)

## Dificuldades Encontradas durante o desenvolvimento.

A maior dificuldade foi fazer a rotação da nave com as setas direita e esquerda e que ela acelere para as direções.
O JPlay nao permite a rotação de uma sprite então tivemos que utilizar a classe animação para realizar essa rotaçao.
Para uma rotação suficientemente fluida fizemos uma sprite com 32 posições possiveis para a nave e o tiro.

![image](https://user-images.githubusercontent.com/50213462/60405093-82906400-9b84-11e9-8996-5debd4e2646f.png)

![image](https://user-images.githubusercontent.com/50213462/60405110-a358b980-9b84-11e9-93f5-25e38cb48795.png)


Para isso fizemos uma classe enum com todos os angulos possiveis e seus respectivos frames da sprite:

 ![image](https://user-images.githubusercontent.com/50213462/60405139-c5ead280-9b84-11e9-8002-f87b3679bfec.png)
  
Outro detalhe que tivemos que contornar foi trabalhar com as coordenadas x e y do JPlay;
Para calculos de direção, velocidade e etc utilizamos a Classe Vector2 do java, e para usarmos o JPlay e o Vector2 para toda alteração no posicionamento dos objetos na tela precisavamos alterar na posição do tipo Vector2 e no x e y do Jplay.

 ![image](https://user-images.githubusercontent.com/50213462/60405369-1151b080-9b86-11e9-8168-38ec35f9b805.png)

A outra dificuldade foi chegar a conclusão de qual logica usariamos para armazenar as entidades que estão na tela, entao utilizamos duas listas. uma de entidades que ainda serão incluidas no jogo e outra para entidades que ja pertencem ao jogo.
E toda iteração do looping checamos para saber se deveriam ou não serem removidas e para cada entidade na lista checamos se ela colidia com alguma outra entidade na lista.

![image](https://user-images.githubusercontent.com/50213462/60405489-e582fa80-9b86-11e9-9560-0f39bdcb45e9.png)


## Bonus!
![gif2](https://user-images.githubusercontent.com/50213462/60405816-df8e1900-9b88-11e9-9350-5430057e9ea1.gif)

![gif1](https://user-images.githubusercontent.com/50213462/60405935-8f638680-9b89-11e9-8f1b-eac0f600b78a.gif)


![gif3](https://user-images.githubusercontent.com/50213462/60406040-495af280-9b8a-11e9-9f8b-9c3f4510be16.gif)
