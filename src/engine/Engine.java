package engine;

import draw_well.DrawWell;
import draw_well.Figure;
import geometry.Geometry;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.transform.Rotate;
import main.Main;

public class Engine implements EventHandler<KeyEvent> {

    private static enum State{FIGURE_FALLING, LEVELS_REDUCTION};
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
        drawWell = new DrawWell(Main.BOX_DIMENSION, Main.BOX_NUM_X, Main.BOX_NUM_Y, Main.BOX_NUM_Z + Main.BOX_NUM_EXT_Z, materials);
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

    public DrawWell getDrawWell() {
        return drawWell;
    }

    @Override
    public void handle(KeyEvent event) {

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


    public void update(){
        switch (state){
            case FIGURE_FALLING:
                if (!currentFigure.translate(Rotate.Z_AXIS, -1.0, drawWell))
                    createFigure();
                break;
            case LEVELS_REDUCTION:
                break;
        }
    }
}
