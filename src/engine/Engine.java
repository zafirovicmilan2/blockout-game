package engine;

import draw_well.DrawWell;
import draw_well.Figure;
import geometry.Geometry;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.PhongMaterial;
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
        if (event.getCode() == KeyCode.RIGHT && event.getEventType() == KeyEvent.KEY_PRESSED)
        {
            currentFigure.addTransformation(new Translate(-Main.BOX_DIMENSION, 0.0,0.0), drawWell);
        }
        else if (event.getCode() == KeyCode.LEFT && event.getEventType() == KeyEvent.KEY_PRESSED)
        {
            currentFigure.addTransformation(new Translate(Main.BOX_DIMENSION, 0.0,0.0), drawWell);
        }
        else if (event.getCode() == KeyCode.UP && event.getEventType() == KeyEvent.KEY_PRESSED)
        {
            currentFigure.addTransformation(new Translate(0.0, -Main.BOX_DIMENSION,0.0), drawWell);
        }
        else if (event.getCode() == KeyCode.DOWN && event.getEventType() == KeyEvent.KEY_PRESSED)
        {
            currentFigure.addTransformation(new Translate(0.0, Main.BOX_DIMENSION,0.0), drawWell);
        }
        else if (event.getCode() == KeyCode.V && event.getEventType() == KeyEvent.KEY_PRESSED)
        {

        }
        else if (event.getCode() == KeyCode.F && event.getEventType() == KeyEvent.KEY_PRESSED)
        {

        }
        else if (event.getCode() == KeyCode.B && event.getEventType() == KeyEvent.KEY_PRESSED)
        {

        }
        else if (event.getCode() == KeyCode.G && event.getEventType() == KeyEvent.KEY_PRESSED)
        {

        }
        else if (event.getCode() == KeyCode.N && event.getEventType() == KeyEvent.KEY_PRESSED)
        {

        }
        else if (event.getCode() == KeyCode.H && event.getEventType() == KeyEvent.KEY_PRESSED)
        {

        }
    }
}
