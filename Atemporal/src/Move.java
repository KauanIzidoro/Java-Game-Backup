import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.util.Objects;

// Classe Move é responsável por instânciar um objeto da classe Mapa e ajustar a imagem na tela
public class Move extends Application {

    private static final double WIDTH = 1000;
    private static final double HEIGHT = 600;
    MapObstacles[] obstacles;

    @Override
    public void start(Stage primaryStage) {
        Pane pane = new Pane();
        Scene scene = new Scene(pane, WIDTH, HEIGHT);

        // Instância do mapa e do player
        GameMap gameMap = new GameMap(WIDTH, HEIGHT);
        Player player = new Player(200, HEIGHT/2);
        pane.getChildren().add(gameMap.getBackgroundImageView());
        pane.getChildren().add(player.getImageView());

        // Obstáculos do mapa em camadas
        obstacles = new MapObstacles[4];
        //imagem da pedra em uma camada acima do player
        obstacles[0] = new MapObstacles(212,416,0);
        // Árvore
        obstacles[1] = new MapObstacles(335,355,1);
        // Altar
        obstacles[2] = new MapObstacles(97,234,2);
        // Coluna
        obstacles[3] = new MapObstacles(468,513,3);

        for (int i = 0; i < 4; i++) {
            pane.getChildren().add(obstacles[i].getMap_obj(i));
            obstacles[i].toFront();
        }

       // Instância da classe GameLoop para acessar o método start()
        GameLoop gameLoop = new GameLoop(scene, player, pane);
        gameLoop.start();


        // Configuração da tela 'primaryStage'
        primaryStage.setTitle("Atemporal");
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("res/logo.png"))));
        primaryStage.show();

        // Atribuir foco aos eventos do painel
        pane.requestFocus();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
