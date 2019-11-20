package controller;

import javafx.scene.input.KeyCode;
import model.Direction;
import model.entity.BaseEntity;
import model.entity.Player;
import view.Platform;

import java.util.ArrayList;

public class GameLoop implements Runnable {

    private Platform platform;
    private int frameRate, time = 0;
    private float interval;
    private static boolean running;

    public GameLoop(Platform platform) {
        this.platform = platform;
        frameRate = 60;
        interval = 1000.0f / frameRate; // 1000 ms = 1 second
        running = true;
    }

    public void updateDirection(Player player) {
        if(player.getCanMove()) {
            if (platform.getKeys().isPressed(KeyCode.A) && platform.getKeys().isPressed(KeyCode.W)) {
                player.setDirection(Direction.NW);
                player.setFace(Direction.W);
            } else if (platform.getKeys().isPressed(KeyCode.W) && platform.getKeys().isPressed(KeyCode.D)) {
                player.setDirection(Direction.NE);
                player.setFace(Direction.E);
            } else if (platform.getKeys().isPressed(KeyCode.D) && platform.getKeys().isPressed(KeyCode.S)) {
                player.setDirection(Direction.SE);
                player.setFace(Direction.E);
            } else if (platform.getKeys().isPressed(KeyCode.S) && platform.getKeys().isPressed(KeyCode.A)) {
                player.setDirection(Direction.SW);
                player.setFace(Direction.W);
            } else if (platform.getKeys().isPressed(KeyCode.A)) {
                player.setDirection(Direction.W);
                player.setFace(Direction.W);
            } else if (platform.getKeys().isPressed(KeyCode.W)) {
                player.setDirection(Direction.N);
            } else if (platform.getKeys().isPressed(KeyCode.D)) {
                player.setDirection(Direction.E);
                player.setFace(Direction.E);
            } else if (platform.getKeys().isPressed(KeyCode.S)) {
                player.setDirection(Direction.S);
            } else if (platform.getKeys().isPressed(KeyCode.SPACE)) {
                player.attack();
            } else {
                player.setDirection(Direction.NONE);
            }

            if (!platform.getKeys().isPressed(KeyCode.A) && !platform.getKeys().isPressed(KeyCode.D)) {
                player.stopX();
            }

            if (!platform.getKeys().isPressed(KeyCode.W) && !platform.getKeys().isPressed(KeyCode.S)) {
                player.stopY();
            }

            if (platform.getKeys().isPressed(KeyCode.A)
                    || platform.getKeys().isPressed(KeyCode.W)
                    || platform.getKeys().isPressed(KeyCode.D)
                    || platform.getKeys().isPressed(KeyCode.S)) {
                player.move();
            } else {
                player.stop();
            }

            player.checkReachGameWall();
        }
    }

    private void update(ArrayList<BaseEntity> entityList) {
        for (BaseEntity entity : entityList ) {
            entity.tick();
            entity.repaint();
            entity.trace();
        }
    }

    private void checkDrawCollisions(ArrayList<BaseEntity> entityList) throws InterruptedException {
        for (BaseEntity eA : entityList) {
            for (BaseEntity eB : entityList) {
                if(eA != eB) {
                    if (eA.getBoundsInParent().intersects(eB.getBoundsInParent())) {
                        eA.collided(eB);
                        eB.collided(eA);
                        return;
                    }
                }
            }
        }
    }

    private void updateScore() {
        javafx.application.Platform.runLater(() -> {
            platform.getScore().setPoint(platform.getPlayer().getScore());
        });
    }

    private void updateCountup() {
        javafx.application.Platform.runLater(() -> {
            platform.getCountup().setTime(time++);
        });
    }

    public static void stop() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {

            float time = System.currentTimeMillis();

            // Update player direction
            updateDirection(platform.getPlayer());
            // Update all entity
            update(platform.getEntityList());

            // Check collisions
            try {
                checkDrawCollisions(platform.getEntityList());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Update score board and play time
            updateScore();
            updateCountup();

            time = System.currentTimeMillis() - time;

            if (time < interval) {
                try {
                    Thread.sleep((long) (interval - time));
                } catch (InterruptedException ignore) {
                }
            } else {
                try {
                    Thread.sleep((long) (interval - (interval % time)));
                } catch (InterruptedException ignore) {
                }
            }
        }

        // After game loop end
        platform.afterGameEnd();
    }
}
