package model.entity;

import controller.GameLoop;
import javafx.scene.image.Image;
import model.AnimatedSprite;
import model.Direction;
import view.Platform;

public class Player extends BaseEntity {

    private Platform platform;

    public static int CHARACTER_WIDTH = 114;
    public static int CHARACTER_HEIGHT = 91;

    private boolean isIdle = true;
    private boolean isMove = false;
    private boolean isDead = false;
    private boolean isAttack = false;

    protected boolean canAttack = true;
    protected int attackTickPerFrame = 4;
    protected int attackFrame = 11;
    protected int attackCount = 0;

    private Direction direction = Direction.NONE;
    private Direction face = Direction.W;

    public enum STATE {IDLE, MOVE, ATTACK, DEAD};
    private STATE state;
    private int score = 0;

    public Player(Platform platform, int x, int y) {
        super(x, y);
        this.platform = platform;
        state = STATE.IDLE;

        entityImg = new Image(getClass().getResourceAsStream("/CharacterIdle.png"));
        imageView = new AnimatedSprite(entityImg,4,3, 0,0,116,93);
        imageView.setFitWidth(CHARACTER_WIDTH);
        imageView.setFitHeight(CHARACTER_HEIGHT);

        getChildren().add(imageView);
    }

    public Direction getDirection() { return direction; }

    public void setDirection(Direction direction) { this.direction = direction; }

    public Direction getFace() { return face; }

    public void setFace(Direction direction) { this.face = direction; }

    public int getScore() { return score; }

    public boolean getCanMove() { return canMove; }

    public STATE getState() { return state; }

    public void moveLeft() {
        if(canMove) {
            super.moveLeft();
            isIdle = false;
            isMove = true;
            changeImage();
        }
    }

    public void moveRight() {
        if(canMove) {
            super.moveRight();
            isIdle = false;
            isMove = true;
            changeImage();
        }
    }

    public void moveUp() {
        if(canMove) {
            super.moveUp();
            isIdle = false;
            isMove = true;
            changeImage();
        }
    }

    public void moveDown() {
        if(canMove) {
            super.moveDown();
            isIdle = false;
            isMove = true;
            changeImage();
        }
    }

    public void stop() {
        super.stop();
        isIdle = true;
        isMove = false;
        changeImage();
    }

    public void move() {
        if (direction.equals(Direction.W)) {
            setScaleX(1);
            moveLeft();
        } else if (direction.equals(Direction.NW)) {
            setScaleX(1);
            moveLeft();
            moveUp();
        } else if (direction.equals(Direction.N)) {
            moveUp();
        } else if (direction.equals(Direction.NE)) {
            setScaleX(-1);
            moveRight();
            moveUp();
        } else if (direction.equals(Direction.E)) {
            setScaleX(-1);
            moveRight();
        } else if (direction.equals(Direction.SE)) {
            setScaleX(-1);
            moveRight();
            moveDown();
        } else if (direction.equals(Direction.S)) {
            moveDown();
        } else if (direction.equals(Direction.SW)) {
            setScaleX(1);
            moveLeft();
            moveDown();
        }
    }

    public void trace() {
        if(isMove) {
            String direction = "";
            if(isMoveUp) direction += "↑";
            if(isMoveDown) direction += "↓";
            if(isMoveLeft) direction += "←";
            if(isMoveRight) direction += "→";
            logger.info("x:{}, y:{}, vx:{}, vy:{}, {}", x, y, xVelocity, yVelocity, direction);
        } else {
            //logger.info("State: {}, isIdle: {}, isMove: {}", state.toString(), isIdle, isMove);
        }
    }

    public void tick() {
        if(isAttack) {
            if(attackCount % attackTickPerFrame == 0) {
                imageView.tick();
            }
            if(attackCount == 0) {
                logger.info("Attack scene ended.");
                canMove = true;
                canAttack = true;
                isAttack = false;
                isIdle = true;
                isMove = false;
                state = STATE.MOVE;

                CHARACTER_WIDTH = 114;
                CHARACTER_HEIGHT = 91;
                imageView.setFitWidth(CHARACTER_WIDTH);
                imageView.setFitHeight(CHARACTER_HEIGHT);
                if(face.equals(Direction.W)) {
                    x = x + 75;
                    y = y + 70;
                } else if (face.equals(Direction.E)) {
                    x = x + 25;
                    y = y + 70;
                }
                setTranslateX(x);
                setTranslateY(y);
                changeImage();
            } else {
                attackCount--;
            }
        } else if (isDead) {
            if(deadCount % deadTickPerFrame == 0) {
                imageView.tick();
            }
            if(deadCount == 0) {
                GameLoop.stop();
            } else {
                deadCount--;
            }
        } else {
            imageView.tick();
        }
    }

    public void changeImage() {
        if (isIdle && state.equals(STATE.MOVE)) {
            entityImg = new Image(getClass().getResourceAsStream("/CharacterIdle.png"));
            imageView.setNewImage(entityImg,4,3,0,0,116,93);
            imageView.resetTick();
            state = STATE.IDLE;
        } else if (isMove && !state.equals(STATE.MOVE)) {
            entityImg = new Image(getClass().getResourceAsStream("/CharacterMove.png"));
            imageView.setNewImage(entityImg,4,3,0,0,111,97);
            imageView.resetTick();
            state = STATE.MOVE;
        } else if (isAttack && !state.equals(STATE.ATTACK)) {
            entityImg = new Image(getClass().getResourceAsStream("/CharacterAttack.png"));
            imageView.setNewImage(entityImg,11,3,0,0,214,161);
            imageView.resetTick();
            state = STATE.ATTACK;

            //public static int CHARACTER_WIDTH = 114;
            //public static int CHARACTER_HEIGHT = 91;
            CHARACTER_WIDTH = 214;
            CHARACTER_HEIGHT = 161;
            imageView.setFitWidth(CHARACTER_WIDTH);
            imageView.setFitHeight(CHARACTER_HEIGHT);
            if(face.equals(Direction.W)) {
                x = x - 75;
                y = y - 70;
            } else if (face.equals(Direction.E)) {
                x = x - 25;
                y = y - 70;
            }
            setTranslateX(x);
            setTranslateY(y);
        } else if (isDead && !state.equals(STATE.DEAD)) {
            entityImg = new Image(getClass().getResourceAsStream("/CharacterDead.png"));
            imageView.setNewImage(entityImg,6,6,0,0,108,70);
            imageView.resetTick();
            state = STATE.DEAD;

            CHARACTER_WIDTH = 108;
            CHARACTER_HEIGHT = 70;
            imageView.setFitWidth(CHARACTER_WIDTH);
            imageView.setFitHeight(CHARACTER_HEIGHT);
            x = x + 6;
            y = y + 21;
            setTranslateX(x);
            setTranslateY(y);
        }

        if(face.equals(Direction.W)) {
            setScaleX(1);
        } else {
            setScaleX(-1);
        }
    }

    public void checkReachGameWall() {
        if(!isAttack) {
            if (x < 0) {
                x = 0;
                logger.info("Player hit left border");
            } else if (x + getWidth() > Platform.WIDTH) {
                x = (int) (Platform.WIDTH - getWidth());
                logger.info("Player hit right border");
            }

            if (y < Platform.TOP_GROUND) {
                y = Platform.TOP_GROUND;
                logger.info("Player hit top border");
            } else if (y + getHeight() > Platform.HEIGHT) {
                y = (int) (Platform.HEIGHT - getHeight());
                logger.info("Player hit bottom border");
            }
        }
    }

    public void attack() {
        if(canAttack) {
            canMove = false;
            canAttack = false;
            stop();

            attackCount = attackTickPerFrame * attackFrame - 1;
            isAttack = true;
            isIdle = false;
            isMove = false;
            changeImage();
        }
    }

    public void dead() {
        canMove = false;
        canAttack = false;
        stop();

        deadCount = deadTickPerFrame * deadFrame - 1;
        isDead = true;
        isAttack = false;
        isIdle = false;
        isMove = false;
        changeImage();
    }

    public void collided(Interface entity) throws InterruptedException {
        if(state.equals(STATE.ATTACK)) {
            Enemy enemy = (Enemy) entity;
            enemy.respawn();
            score++;
        } else {
            if(!isDead) {
                dead();
            }
        }
    }
}
