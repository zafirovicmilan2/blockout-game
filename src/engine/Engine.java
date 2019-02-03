package engine;

import cameras.Camera;
import draw_well.DrawWell;
import draw_well.Figure;
import geometry.Geometry;
import javafx.animation.Transition;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.transform.Rotate;
import main.Main;

import java.util.List;

public class Engine implements EventHandler<KeyEvent> {

    private static enum State{FIGURE_FALLING, LEVELS_REDUCTION, THE_END}
    private Group group;
    private DrawWell drawWell;
    private Figure currentFigure = null;
    private State state = State.FIGURE_FALLING;
    private Scene scene;
    private Camera camera;

    public Engine() {
        group = new Group();
        scene = new Scene(group, 700, 300,true);
        scene.setOnKeyPressed(this);
        camera = new Camera(scene);
        PhongMaterial[] materials = new PhongMaterial[Main.BOX_NUM_Z + Main.BOX_NUM_EXT_Z];
        for (int i = 0; i < (Main.BOX_NUM_Z + Main.BOX_NUM_EXT_Z); i++) {
            materials[i] = new PhongMaterial();
            materials[i].setDiffuseColor(Color.color(Math.random(), Math.random(), Math.random()));
        }
        drawWell = new DrawWell(Main.BOX_DIMENSION, Main.BOX_NUM_X, Main.BOX_NUM_Y, Main.BOX_NUM_Z, Main.BOX_NUM_EXT_Z, materials);
        drawWell.assignMaterial();
        createFigure();
    }

    public Scene getScene() {
        return scene;
    }

    public void createFigure(){
        currentFigure = new Figure(Main.BOX_DIMENSION, Main.BOX_NUM_IN_FIGURE);
        Geometry.positionToZero(currentFigure);
        currentFigure.setTranslateX(currentFigure.getTranslateX() + Main.BOX_DIMENSION);
        currentFigure.setTranslateY(currentFigure.getTranslateY() + Main.BOX_DIMENSION);
        currentFigure.setTranslateZ(currentFigure.getTranslateZ() + (Main.BOX_NUM_Z + 1) * Main.BOX_DIMENSION);
        resetChildren();
    }

    private void resetChildren(){
        group.getChildren().clear();
        group.getChildren().add(drawWell);
        if (currentFigure != null)
            group.getChildren().add(currentFigure);
    }

    @Override
    public void handle(KeyEvent event) {
        switch (event.getCode()){
            case RIGHT:
                if (currentFigure != null)
                    currentFigure.translate(Rotate.X_AXIS, - Main.BOX_DIMENSION, drawWell);
                break;
            case LEFT:
                if (currentFigure != null)
                    currentFigure.translate(Rotate.X_AXIS, Main.BOX_DIMENSION, drawWell);
                break;
            case UP:
                if (currentFigure != null)
                    currentFigure.translate(Rotate.Y_AXIS, - Main.BOX_DIMENSION, drawWell);
                break;
            case DOWN:
                if (currentFigure != null)
                    currentFigure.translate(Rotate.Y_AXIS, Main.BOX_DIMENSION, drawWell);
                break;
            case V:
                if (currentFigure != null)
                    currentFigure.rotate(Rotate.Z_AXIS, 90, drawWell);
                break;
            case F:
                if (currentFigure != null)
                    currentFigure.rotate(Rotate.Z_AXIS, -90, drawWell);
                break;
            case B:
                if (currentFigure != null)
                    currentFigure.rotate(Rotate.X_AXIS, 90, drawWell);
                break;
            case G:
                if (currentFigure != null)
                    currentFigure.rotate(Rotate.X_AXIS, -90, drawWell);
                break;
            case N:
                if (currentFigure != null)
                    currentFigure.rotate(Rotate.Y_AXIS, 90, drawWell);
                break;
            case H:
                if (currentFigure != null)
                    currentFigure.rotate(Rotate.Y_AXIS, -90, drawWell);
                break;
            case W:
                camera.tilt(Camera.NEGATIVE_DIRECTION);
                break;
            case S:
                camera.tilt(Camera.POSITIVE_DIRECTION);
                break;
            case A:
                camera.yaw(Camera.NEGATIVE_DIRECTION);
                break;
            case D:
                camera.yaw(Camera.POSITIVE_DIRECTION);
                break;
            case DIGIT1:
                camera.changeCamera(true);
                break;
            case DIGIT2:
                camera.changeCamera(false);
                break;
            case Q:
                camera.translateHolder(Camera.NEGATIVE_DIRECTION);
                break;
            case E:
                camera.translateHolder(Camera.POSITIVE_DIRECTION);
                break;
        }
    }


    public void update(){
        switch (state){
            case FIGURE_FALLING:
                if (!currentFigure.translate(Rotate.Z_AXIS, -0.5, drawWell)){
                    drawWell.addFigure(currentFigure);
                    currentFigure = null;
                    resetChildren();
                    if (drawWell.isTheEnd()){
                        state = State.THE_END;
                        break;
                    }
                    List<Integer> filledLevels = drawWell.getFilledLevels();
                    if (filledLevels.isEmpty())
                        createFigure();
                    else {
                        state = State.LEVELS_REDUCTION;
                        Transition transition = drawWell.getRemoveLevelsTransition(filledLevels.toArray(new Integer[filledLevels.size()]));
                        transition.setOnFinished(e-> {
                            drawWell.assignMaterial();
                            createFigure();
                            state = State.FIGURE_FALLING;
                        });
                        transition.play();
                    }
                }

                break;
            case LEVELS_REDUCTION:
                break;
            case THE_END:
                break;
        }
    }
}
