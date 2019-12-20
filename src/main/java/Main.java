import controller.GameLoop;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.Platform;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Platform platform = new Platform();
        GameLoop gameLoop = new GameLoop(platform);

        Scene scene = new Scene(platform,platform.WIDTH,platform.HEIGHT);
        scene.setOnKeyPressed(event -> platform.getKeys().add(event.getCode()));
        scene.setOnKeyReleased(event -> platform.getKeys().remove(event.getCode()));

        primaryStage.setTitle("612115006");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);

        (new Thread(gameLoop)).start();
    }

}
