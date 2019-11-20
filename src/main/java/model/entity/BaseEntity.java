package model.entity;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import model.AnimatedSprite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.Platform;

abstract public class BaseEntity extends Pane implements Interface {

    Logger logger = LoggerFactory.getLogger(getClass());

    public static int CHARACTER_WIDTH = 64;
    public static int CHARACTER_HEIGHT = 64;

    protected Image entityImg;
    protected AnimatedSprite imageView;

    protected int x;
    protected int y;
    protected int startX;
    protected int startY;

    protected int xVelocity = 0;
    protected int yVelocity = 0;
    protected int xAcceleration = 1;
    protected int yAcceleration = 1;
    protected int xMaxVelocity = 5;
    protected int yMaxVelocity = 5;
    protected boolean canMove = true;
    protected boolean isMoveLeft = false;
    protected boolean isMoveRight = false;
    protected boolean isMoveUp = false;
    protected boolean isMoveDown = false;

    protected int deadTickPerFrame = 7;
    protected int deadFrame = 6;
    protected int deadCount = 0;

    public BaseEntity(int x, int y) {
        this.x = x;
        this.y = y;
        this.startX = x;
        this.startY = x;
        this.setTranslateX(x);
        this.setTranslateY(y);
    }

    public AnimatedSprite getImageView() {
        return imageView;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void moveLeft() {
        isMoveLeft = true;
        isMoveRight = false;
    }

    public void moveRight() {
        isMoveRight = true;
        isMoveLeft = false;
    }

    public void moveUp() {
        isMoveUp = true;
        isMoveDown = false;
    }

    public void moveDown() {
        isMoveUp = false;
        isMoveDown = true;
    }

    public void moveX() {
        setTranslateX(x);

        if(canMove) {
            if (isMoveLeft) {
                xVelocity = xVelocity >= xMaxVelocity ? xMaxVelocity : xVelocity + xAcceleration;
                x = x - xVelocity;
            }
            if (isMoveRight) {
                xVelocity = xVelocity >= xMaxVelocity ? xMaxVelocity : xVelocity + xAcceleration;
                x = x + xVelocity;
            }
        }
    }

    public void moveY() {
        setTranslateY(y);

        if(canMove) {
            if (isMoveUp) {
                yVelocity = yVelocity >= yMaxVelocity ? yMaxVelocity : yVelocity + yAcceleration;
                y = y - yVelocity;
            }

            if (isMoveDown) {
                yVelocity = yVelocity >= yMaxVelocity ? yMaxVelocity : yVelocity + yAcceleration;
                y = y + yVelocity;
            }
        }
    }

    public void repaint() {
        moveX();
        moveY();
    }

    public void stopX() {
        isMoveLeft = false;
        isMoveRight = false;
        xVelocity = 0;
    }

    public void stopY() {
        isMoveUp = false;
        isMoveDown = false;
        yVelocity = 0;
    }

    public void stop() {
        stopX();
        stopY();
    }

    public void collided(Interface entity) throws InterruptedException {
        /*if (isMoveLeft) {
            x = entity.getX() + CHARACTER_WIDTH + 1;
            stop();
        } else if (isMoveRight) {
            x = entity.getX() - CHARACTER_WIDTH - 1;
            stop();
        }*/

        if(y < Platform.HEIGHT - CHARACTER_HEIGHT) {
            //Above c
            if(y < entity.getY() && Math.abs(y-entity.getY())<=CHARACTER_HEIGHT+1) {
                y = Platform.HEIGHT - CHARACTER_HEIGHT - 5;
                repaint();
                entity.respawn();
            }
        }
    }

    public void respawn() {
        x = startX;
        y = startY;
        isMoveLeft = false;
        isMoveRight = false;
        isMoveUp = false;
        isMoveDown = false;
    }

    public void respawn(int x, int y) {
        this.x = x;
        this.y = y;
        isMoveLeft = false;
        isMoveRight = false;
        isMoveUp = false;
        isMoveDown = false;
    }

    public void tick() {
        getImageView().tick();
    }

    abstract public void trace();
}
