import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import java.util.Objects;

// Classe Player, esta classe é responsável por configurar os atributor e métodos do jogador
// altura, largura, velocidade, imagem, movimentação e colisão com os limites do mapa
public class Player extends ImageView {

    private final double PLAYER_HEIGHT = 74.0;
    private final double PLAYER_WIDTH = 55.0;
    private static final double WIDTH = 1000;
    private static final double HEIGHT = 600;
    private double speed = 4;
    private ImageView imageView;
    private Image[] runRight;
    private Image[] runLeft;
    private Image[] knatk;
    private Image [] dead;
    private Image idle;
    private int indexRun = 0;
    private int indexAtk = 0;
    private Timeline runR;
    private Timeline runL;
    private Timeline atk;
    private Timeline kndead;

    public Player(double startX, double startY) {
        idle = new Image(Objects.requireNonNull(getClass().getResourceAsStream("res/idle.png")));
        imageView = new ImageView(idle);
        imageView.setX(startX);
        imageView.setY(startY);

        loadImages();
        setupAnimations();
    }

    //Método para inicializar as imagens
    private void loadImages() {
        runRight = new Image[7];
        runLeft = new Image[7];
        knatk = new Image[5];
        knatk[0] = new Image("res/knatk0.png");
        knatk[1] = new Image("res/knatk1.png");
        knatk[2] = new Image("res/knatk2.png");
        knatk[3] = new Image("res/knatk3.png");
        knatk[4] = new Image("res/knatk4.png");
        for (int i = 0; i < runRight.length; i++) {
            runRight[i] = new Image(Objects.requireNonNull(getClass().getResourceAsStream("res/runRight" + i + ".png")));
            runLeft[i] = new Image(Objects.requireNonNull(getClass().getResourceAsStream("res/runLeft" + i + ".png")));
        }
    }

    private void setupAnimations() {
        runR = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            indexRun = (indexRun + 1) % runRight.length;
            imageView.setImage(runRight[indexRun]);
        }));
        runR.setCycleCount(Timeline.INDEFINITE);

        runL = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            indexRun = (indexRun + 1) % runLeft.length;
            imageView.setImage(runLeft[indexRun]);
        }));
        runL.setCycleCount(Timeline.INDEFINITE);

        atk = new Timeline(new KeyFrame(Duration.millis(150), e -> {
            indexAtk = (indexAtk + 1) % knatk.length;
            imageView.setImage(knatk[indexAtk]);
        }));
        // cada animação executada incrementa em uma unidade a váriavel 'indexAtk',
        // se usarmos como parâmetro o comprimento do vetor de imagens para animação
        // teremos percorrido o array inteiro a cada chamada do método na Classe GameLoop
        atk.setCycleCount(knatk.length);
    }

    public ImageView getImageView() {return imageView;}

    public boolean player_collisionXleft(){return imageView.getX() == 0;}
    public boolean player_collisionXright(){
        return imageView.getX() + PLAYER_WIDTH > WIDTH;
    }
    public boolean player_collisionYup(){
        return imageView.getY() < 0;
    }
    public boolean player_collisionYdown(){
        return imageView.getY() + PLAYER_HEIGHT > HEIGHT;
    }

    public boolean player_enemy_collision(Enemy enemy){
        if(imageView.getX() + PLAYER_WIDTH == enemy.getX()){
            return true;
        }
        return false;
    }

    // Métodos para movimentação do player baseado na atualização da posição atual + velocidade 
    public void moveLeft() {
        imageView.setX(imageView.getX() - speed);
        System.out.println("P0(x) position: " + imageView.getX());
        runL.play();
    }

    public void moveRight() {
        imageView.setX(imageView.getX() + speed);
        System.out.println("P0(x) position: " + imageView.getX());
        runR.play();
    }

    public void moveUp() {
        imageView.setY(imageView.getY() - speed);
        System.out.println("P0(y) position: " + imageView.getY());
        runR.play();
    }

    public void moveDown() {
        imageView.setY(imageView.getY() + speed);
        System.out.println("P0(y) position: " + imageView.getY());
        runR.play();
    }

    public void stopAnimation() {
        runR.stop();
        runL.stop();
        imageView.setImage(idle);
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getSpeed() {
        return speed;
    }

    public double getWidth() {
        return PLAYER_WIDTH;
    }

    public double getHeight() {
        return PLAYER_HEIGHT;
    }

    public void increaseSpeed() {
        speed++;
    }

    public void decreaseSpeed() {
        speed--;
    }

    public Timeline getAtk() {
        return atk;
    }

    public void setX(int x){
        imageView.setX(x);
    }
    public void setY(int y){
        imageView.setY(y);
    }

}
