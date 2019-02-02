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
        PerspectiveCamera pc = new PerspectiveCamera(true);

        Rotate t = new Rotate(180,0,0,0,Rotate.Y_AXIS);
        pc.setNearClip(0.1);
        pc.setFarClip(5000);
        pc.getTransforms().addAll(t);
        pc.setTranslateZ(250);
        pc.setTranslateX(50);
        pc.setTranslateY(30);

        Group root = new Group();
        engine = new Engine(root);

        Scene scene = new Scene(root, 700, 300,true);
        scene.setCamera(pc );
        scene.setOnKeyPressed(engine);
        primaryStage.setTitle("Svemirci");
        primaryStage.setScene(scene);
        primaryStage.show();

        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                engine.update();
            }
        }.start();

    }}