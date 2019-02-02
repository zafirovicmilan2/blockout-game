package engine;

import draw_well.DrawWell;
import draw_well.Figure;
import geometry.Geometry;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import main.Main;

public class Engine implements EventHandler<KeyEvent> {

    private Group group;
    private DrawWell drawWell;
    private Figure currentFigure = null;

    public Engine(Group group) {
        this.group = group;
        PhongMaterial[] materials = new PhongMaterial[Main.BOX_NUM_Z]; // TODO add material
        drawWell = new DrawWell(Main.BOX_DIMENSION, Main.BOX_NUM_X, Main.BOX_NUM_Y, Main.BOX_NUM_Z, materials);
        group.getChildren().add(drawWell);
    }

    public void createFigure(){
        // TODO add material
        currentFigure = new Figure(Main.BOX_DIMENSION, Main.BOX_NUM_IN_FIGURE);
        Geometry.positionToZero(currentFigure);  // TODO position on correct coordinates
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
}
