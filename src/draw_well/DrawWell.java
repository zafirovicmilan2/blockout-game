package draw_well;

import geometry.Coordinates;
import geometry.Geometry;
import javafx.scene.Group;
import javafx.scene.paint.Material;

import java.util.ArrayList;
import java.util.List;

public class DrawWell extends Group {
    private List<Level> levels;
    private Material matLevel[];
    private Frame frame;
    private double boxDimension;

    public DrawWell(double boxDimension, int boxNumX, int boxNumY, int boxNumZ, int extBoxNumZ, Material[] matLevel) {
        this.boxDimension = boxDimension;
        this.matLevel = matLevel; // TODO 1)update material setting, 2)check if boxNumZ==len(matLevel)
        levels = new ArrayList<>();
        frame = new Frame(boxDimension, boxNumX, boxNumY, boxNumZ + extBoxNumZ);
        Geometry.positionTo(frame, Frame.WIRE_RADIUS, Frame.WIRE_RADIUS, Frame.WIRE_RADIUS);
        double tz = 0;
        for (int i = 0; i < boxNumZ; i++) {
            Level level = new Level(boxDimension, boxNumX, boxNumY);
            Geometry.positionToZero(level);
            level.setTranslateZ(level.getTranslateZ() + tz);
            level.setMaterial(this.matLevel[i]);
            levels.add(level);
            getChildren().add(level);
            tz += boxDimension;
        }
    }

    /**
     * @return indexes of a box that the point(x,y,z) belongs to
     */
    public Coordinates<Integer> getIndexes(double x, double y, double z){
        // TODO check if the point is inside draw-well
        int i = (int)(x/boxDimension);
        int j = (int)(y/boxDimension);
        int k = (int)(z/boxDimension);
        return new Coordinates<>(i, j, k);
    }

    public void setVisible(Coordinates<Integer> coordinates){
        // TODO check index out of bounds
        levels.get(coordinates.getI()).setVisible(coordinates.getJ(),coordinates.getK());
    }
}
