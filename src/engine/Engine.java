package engine;

import draw_well.DrawWell;
import draw_well.Figure;
import geometry.Geometry;
import javafx.animation.Transition;
import javafx.event.EventHandler;
import javafx.scene.Group;
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

    public Engine(Group group) {
        this.group = group;
        PhongMaterial[] materials = new PhongMaterial[Main.BOX_NUM_Z + Main.BOX_NUM_EXT_Z];
        for (int i = 0; i < (Main.BOX_NUM_Z + Main.BOX_NUM_EXT_Z); i++) {
            materials[i] = new PhongMaterial();
            materials[i].setDiffuseColor(Color.color(Math.random(), Math.random(), Math.random()));
        }
        drawWell = new DrawWell(Main.BOX_DIMENSION, Main.BOX_NUM_X, Main.BOX_NUM_Y, Main.BOX_NUM_Z, Main.BOX_NUM_EXT_Z, materials);
        drawWell.assignMaterial();
        createFigure();
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
        if (!drawWell.isTheEnd()) {
            switch (event.getCode()){
                case RIGHT:
                    currentFigure.translate(Rotate.X_AXIS, - Main.BOX_DIMENSION, drawWell);
                    break;
                case LEFT:
                    currentFigure.translate(Rotate.X_AXIS, Main.BOX_DIMENSION, drawWell);
                    break;
                case UP:
                    currentFigure.translate(Rotate.Y_AXIS, - Main.BOX_DIMENSION, drawWell);
                    break;
                case DOWN:
                    currentFigure.translate(Rotate.Y_AXIS, Main.BOX_DIMENSION, drawWell);
                    break;
                case V:
                    currentFigure.rotate(Rotate.Z_AXIS, 90, drawWell);
                    break;
                case F:
                    currentFigure.rotate(Rotate.Z_AXIS, -90, drawWell);
                    break;
                case B:
                    currentFigure.rotate(Rotate.X_AXIS, 90, drawWell);
                    break;
                case G:
                    currentFigure.rotate(Rotate.X_AXIS, -90, drawWell);
                    break;
                case N:
                    currentFigure.rotate(Rotate.Y_AXIS, 90, drawWell);
                    break;
                case H:
                    currentFigure.rotate(Rotate.Y_AXIS, -90, drawWell);
                    break;
            }
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
