package draw_well;

import geometry.Coordinates;
import geometry.Geometry;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.paint.Material;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DrawWell extends Group {
    private List<Level> levels;
    private Material matLevel[];
    private Frame frame;
    private double boxDimension;

    public DrawWell(double boxDimension, int boxNumX, int boxNumY, int boxNumZ, int extBoxNumZ, Material[] matLevel) {

        this.boxDimension = boxDimension;
        this.matLevel = matLevel; // TODO 1)update material setting, 2)check if boxNumZ==len(matLevel)

        frame = new Frame(boxDimension, boxNumX, boxNumY, boxNumZ + extBoxNumZ);
        Geometry.positionTo(frame, Frame.WIRE_RADIUS, Frame.WIRE_RADIUS, Frame.WIRE_RADIUS);
        getChildren().add(frame);

        levels = new ArrayList<>();
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

    /**
     * make the box(with this indexes) visible
     */
    public void setVisible(Coordinates<Integer> coordinates){
        // TODO check index out of bounds
        levels.get(coordinates.getI()).setVisible(coordinates.getJ(),coordinates.getK());
    }

    private void removeLevel(int levelIndex){
        // TODO check index out of bounds
        //bring down all levels above current level
        ParallelTransition pt = new ParallelTransition();
        for (int j = levelIndex + 1; j < levels.size(); j++) {
            TranslateTransition tt = new TranslateTransition(Duration.seconds(1.0), levels.get(j));
            tt.setByZ(-boxDimension);
            pt.getChildren().add(tt);
        }

        Level removed = levels.remove(levelIndex);
        removed.setInvisible();
        //move removed level to the top; TODO check if this must be done after ParallelTransition
        removed.setTranslateZ(removed.getTranslateZ() + boxDimension * (getHeightBoxesNum() - levelIndex));
        levels.add(removed);

        pt.play();

    }

    public void removeLevels(int... levelIndexes){
        // TODO check if levelIndexes has only unique elements(i.e. not OK:[1,2,2] OK:[1,2,3])
        Arrays.sort(levelIndexes);
        for (int i = levelIndexes.length - 1; i >= 0; i--) {
            removeLevel(i);
        }

        assignMaterial();
    }

    private int getHeightBoxesNum(){
        return levels.size();
    }

    private void assignMaterial(){
        for (int i = 0; i < levels.size(); i++) {
            levels.get(i).setMaterial(matLevel[i]);
        }
    }
}
