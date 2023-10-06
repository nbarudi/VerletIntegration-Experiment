package ca.bungo.physics.verletintegrationsym;

import ca.bungo.physics.verletintegrationsym.simulator.MainController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class VerletIntegrationApplication extends Application {

    @Override
    public void start(Stage stage) {
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 1000, 1000);
        scene.setFill(Color.BLACK);
        stage.setTitle("Verlet Integration");
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.show();
        Platform.runLater(() -> {
            MainController controller = new MainController(scene);

            stage.setOnCloseRequest(windowEvent -> {
                controller.stopTimer();
            });
        });
    }

    public static void main(String[] args) {
        launch();
    }
}