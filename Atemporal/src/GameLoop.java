import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.event.EventHandler;

import java.util.Queue;
import java.util.LinkedList;

public class GameLoop {

    private Player player;
    private Queue<Player> q_player= new LinkedList<>();
    private Pane pane;
    private Scene scene;
    private boolean flag_enemy = false;
    private Enemy[] enemies;
    private AnimationTimer animationTimer;
    private int enemylife = 0;
    private int playerlife = 250;
//    MapObstacles[] obstacles;

    public GameLoop(Scene scene, Player player, Pane pane) {
        this.player = player;
        q_player.add(player);
        this.pane = pane;
        setupKeyHandlers(scene);
        setupAnimationTimer();
    }


    private void setupAnimationTimer() {
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (flag_enemy && enemies != null) {
                    enemies[0].followPlayer(player);
                    // Verificar colisão dos inimigos com o player
                    if(enemies[0].enemy_collision_hit(player)){
//                        System.out.println("enemy position: " + enemies[0].getX());
                        enemies[0].getskatkT().play();
                        System.out.println("enemy hit");
                        playerlife--;
                        System.out.println("Player life: " + playerlife);
                    }else{
                        // Se o inimigo não estiver em colisão com o personagem
                        // a animação de ataque é interrompida
                        enemies[0].getskatkT().stop();
                    }

                    if(playerlife == 0){
                        player.getImageView().setVisible(false);
                        System.out.println("Game Over");
                    }

                }




            }
        };


    }

    // Verificar a existência de inimigos no mapa, se não instância 1 inimigo
    private void spawnEnemies() {
        if (enemies == null) {
            enemies = new Enemy[1];
            enemies[0] = new Enemy(650, 250);


            //Atribui cada objeto da Classe Enemy a tela
            for (Enemy enemy : enemies) {
                pane.getChildren().add(enemy);
            }
        }
    }

    // Método usado para teste do funcionamento de inimigos no mapa //
    private void setupKeyHandlers(Scene scene) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                    handleMovement(event.getCode());
                if (event.getCode() == KeyCode.B) {
                    flag_enemy = true;
                }

                if (flag_enemy) {
                    spawnEnemies();
                    animationTimer.start();
                }
            }
        });

        // Este método verifica alguma tecla que inicia a animação de corrida do player foi
        // de 1 para 0
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                player.stopAnimation();
            }
        });
    }

    // Movimentaçção do player baseada na leitura do teclado
    private void handleMovement(KeyCode code) {
        switch (code) {
            case A:
                player.moveLeft();
                System.out.println("Input A");
                break;
            case W:
                player.moveUp();
                System.out.println("Input W");
                break;
            case S:
                player.moveDown();
                System.out.println("Input S");
                break;
            case D:
                player.moveRight();
                System.out.println("Input D");
                break;
            default:
                break;
        }
        // Ataque do player
        if(code == KeyCode.SPACE){
            player.getAtk().play();
        }else{
            player.getAtk().stop();
        }

        // Método usado para teste das primeiras mecânicas de combate //
        if(flag_enemy){
            // Lógica do combate entre player e inimigos
            if(player.player_enemy_collision(enemies[0]) && code == KeyCode.SPACE){
                enemies[0].getskatkT().stop();
                enemies[0].getHurt().play();
                System.out.println("hit");
                enemylife++;
            }else{
                enemies[0].getHurt().stop();
                enemies[0].getskatkT().play();
            }

            if(enemylife == 5){
                enemies[0].setVisible(false); //apenas fica invisivel
//                enemies[0].pop(enemies); //tirar o inimigo da pilha de inimigos, remover inimigo da memoria
            }

//            // Obstáculos do mapa em camadas
//            obstacles = new MapObstacles[4];
//            //imagem da pedra em uma camada acima do player
//            obstacles[0] = new MapObstacles(100,200,0);
//            // Árvore
//            obstacles[1] = new MapObstacles(335,355,1);
//            //Altar
//            obstacles[2] = new MapObstacles(200,75,2);
//            // Coluna
//            obstacles[3] = new MapObstacles(750,150,3);
//
//            for (int i = 0; i < 4; i++) {
//                pane.getChildren().add(obstacles[i].getMap_obj(i));
//            }
        }


        // Verificar colisão com os limites da tela/mapa
        if (player.player_collisionXleft()) {
            player.setSpeed(0);
            if (code == KeyCode.D || code == KeyCode.S || code == KeyCode.W) {
                player.setSpeed(5);
            }
        }
        if (player.player_collisionXright()) {
            player.setSpeed(0);
            if (code == KeyCode.A || code == KeyCode.W || code == KeyCode.S) {
                player.setSpeed(5);
            }
        }
        if (player.player_collisionYdown()) {
            player.setSpeed(0);
            if (code == KeyCode.W || code == KeyCode.A || code == KeyCode.D) {
                player.setSpeed(5);
            }
        }
        if (player.player_collisionYup()) {
            player.setSpeed(0);
            if (code == KeyCode.S || code == KeyCode.A || code == KeyCode.D) {
                player.setSpeed(5);
            }
        }
    }





    public void start() {

    }

}
