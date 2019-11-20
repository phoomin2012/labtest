package model.entity;

import controller.GameLoop;
import javafx.scene.image.Image;
import model.AnimatedSprite;
import view.Platform;

import java.util.Random;

public class Enemy extends BaseEntity {

    public static int CHARACTER_WIDTH = 63;
    public static int CHARACTER_HEIGHT = 45;

    private enum SPAWN {LEFT, RIGHT}
    private SPAWN spawn;

    private boolean isDead = false;

    public Enemy(int x, int y) {
        super(x, y);
        xMaxVelocity = 3;
        spawn = SPAWN.LEFT;

        entityImg = new Image(getClass().getResourceAsStream("/Bullet.png"));
        imageView = new AnimatedSprite(entityImg,7,7,0,0,63,45);
        imageView.setFitWidth(CHARACTER_WIDTH);
        imageView.setFitHeight(CHARACTER_HEIGHT);

        getChildren().add(imageView);
    }

    public void tick() {
        if(!isDead) {
            super.tick();

            if (spawn.equals(SPAWN.LEFT)) {
                imageView.setScaleX(-1);
                super.moveRight();
            } else if (spawn.equals(SPAWN.RIGHT)) {
                imageView.setScaleX(1);
                super.moveLeft();
            }
            imageView.tick();
        } else {
            stop();
            if(deadCount % deadTickPerFrame == 0) {
                imageView.tick();
            }
            if(deadCount == 0) {
                GameLoop.stop();
            } else {
                deadCount--;
            }
        }
    }

    public void moveX() {
        super.moveX();

        if(x < 0 && spawn.equals(SPAWN.RIGHT)) {
            logger.info("Hit left border");
            respawn();
        }
        if(x + getWidth() > Platform.WIDTH && spawn.equals(SPAWN.LEFT)) {
            logger.info("Hit right border");
            respawn();
        }
    }

    public void respawn() {
        super.respawn();

        Random rand = new Random();
        int offsetY = rand.nextInt(Platform.HEIGHT - Platform.TOP_GROUND);
        int n = rand.nextInt(2);

        if(n == 0) {
            spawn = SPAWN.LEFT;
            x = 0;
        } else {
            spawn = SPAWN.RIGHT;
            x = Platform.WIDTH - CHARACTER_WIDTH;
        }
        y = Platform.TOP_GROUND + offsetY - CHARACTER_HEIGHT;
    }

    public void trace() {
        String direction = "";
        if(isMoveUp) direction += "↑";
        if(isMoveDown) direction += "↓";
        if(isMoveLeft) direction += "←";
        if(isMoveRight) direction += "→";
        //logger.info("x:{}, y:{}, vx:{}, vy:{}, {}", x, y, xVelocity, yVelocity, direction);
    }

    public void collided(Interface entity) throws InterruptedException {
        if(entity.getClass().getSimpleName().equals("Player")) {
            Player player = (Player) entity;
            if(!player.getState().equals(Player.STATE.ATTACK)) {
                if(!isDead) {
                    dead();
                }
            }
        }
        logger.info("Hit: {}", entity.getClass().getSimpleName());
    }

    public void dead() {
        deadCount = deadTickPerFrame * deadFrame - 1;
        isDead = true;

        // Change image
        entityImg = new Image(getClass().getResourceAsStream("/Bomb.png"));
        imageView.setNewImage(entityImg, 6, 6, 0, 0, 139, 115);
        imageView.resetTick();
    }
}
