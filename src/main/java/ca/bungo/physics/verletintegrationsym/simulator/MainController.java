package ca.bungo.physics.verletintegrationsym.simulator;

import ca.bungo.physics.verletintegrationsym.simulator.types.Solver;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

import java.util.Timer;
import java.util.TimerTask;

public class MainController {

    public Scene mainScene;
    private int radius = 450;
    private Solver solver;
    private AnimationTimer timer;

    public MainController(Scene mainScene){
        this.mainScene = mainScene;
        Circle circle = new Circle(mainScene.getWidth()/2, mainScene.getHeight()/2, radius);
        this.solver = new Solver(mainScene, circle);
        ((StackPane)mainScene.getRoot()).getChildren().add(circle);

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                solver.update(60);
            }
        };
        timer.start();

        circle.toBack();
    }


    public void stopTimer(){
        solver.stopTimers();
    }

}
