package view;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.Countup;
import model.Keys;
import model.Score;
import model.entity.BaseEntity;
import model.entity.Enemy;
import model.entity.Player;

import java.util.ArrayList;

public class Platform extends Pane {

    public final static int WIDTH = 800;
    public final static int HEIGHT = 583;
    public final static int TOP_GROUND = 280;

    private Keys keys;
    public ImageView backgroundImg;

    private Player player;
    private Score score;
    private Countup countup;
    private ArrayList<BaseEntity> entityList;

    public Platform() {
        entityList = new ArrayList<BaseEntity>();
        score = new Score(10,10);
        countup = new Countup(Platform.WIDTH - 10, 10);
        keys = new Keys();

        Image platformImg = new Image(getClass().getResourceAsStream("/Background.png"));
        backgroundImg = new ImageView(platformImg);
        backgroundImg.setFitHeight(HEIGHT);
        backgroundImg.setFitWidth(WIDTH);

        player = new Player(this,Platform.WIDTH / 2, Platform.HEIGHT - Player.CHARACTER_HEIGHT);
        entityList.add(player);
        Enemy enemy1 = new Enemy(0,300);
        Enemy enemy2 = new Enemy(0,400);
        enemy1.respawn();
        enemy2.respawn();
        entityList.add(enemy1);
        entityList.add(enemy2);

        getChildren().addAll(backgroundImg);
        getChildren().addAll(enemy1, enemy2, player);
        getChildren().addAll(score, countup);
    }

    public Keys getKeys() { return keys; }

    public Score getScore() { return score; }

    public Countup getCountup() {
        return countup;
    }

    public Player getPlayer() { return player; }

    public ArrayList<BaseEntity> getEntityList() {
        return entityList;
    }

    public void afterGameEnd() {
        // After game end
        javafx.application.Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("");
            alert.setHeaderText("Your scores: " + getScore().getPoint() + " and use " + getCountup().getTimeString() + ".");
            alert.setContentText(null);
            alert.showAndWait();

            System.exit(0);
        });
    }
}
