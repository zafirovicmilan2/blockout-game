package main;

import engine.Engine;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class Main extends Application {

    public static final double BOX_DIMENSION = 20;
    public static final int BOX_NUM_X = 4;
    public static final int BOX_NUM_Y = 4;
    public static final int BOX_NUM_Z = 4;
    public static final int BOX_NUM_EXT_Z = 3;
    public static final int BOX_NUM_IN_FIGURE = 2;

    public static void main(String[] args) {
        launch(args);
    }

    private Engine engine;

    @Override
    public void start(Stage primaryStage) {

        engine = new Engine();
        primaryStage.setTitle("Svemirci");
        primaryStage.setScene(engine.getScene());
        primaryStage.show();

        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                engine.update();
            }
        }.start();

    }}