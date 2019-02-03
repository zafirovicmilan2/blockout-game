package draw_well;

import geometry.Coordinates;
import geometry.Geometry;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Material;
import javafx.util.Duration;
import main.Main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class DrawWell extends Group {
    public List<Level> levels;
    private Material matLevel[];
    private Frame frame;
    private double boxDimension;
    private double boxNumZ;
    private boolean theEnd;

    public DrawWell(double boxDimension, int boxNumX, int boxNumY, int boxNumZ, int boxNumExtZ, Material[] matLevel) {

        this.boxNumZ = boxNumZ;
        this.boxDimension = boxDimension;
        this.matLevel = matLevel; // TODO 1)update material setting, 2)check if boxNumZ==len(matLevel)

        frame = new Frame(boxDimension, boxNumX, boxNumY, boxNumZ+boxNumExtZ);
        getChildren().add(frame);
        levels = new ArrayList<>();
        double tz = 0;
        for (int i = 0; i < boxNumZ+boxNumExtZ; i++) {
            Level level = new Level(boxDimension, boxNumX, boxNumY);
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
        int i = (int)(z/boxDimension);
        int j = (int)(y/boxDimension);
        int k = (int)(x/boxDimension);
        return new Coordinates<>(i, j, k);
    }

    /**
     * @return indexes of a box that the point belongs to
     */
    public Coordinates<Integer> getIndexes(Point3D point){
        return getIndexes(point.getX(), point.getY(), point.getZ());
    }

    /**
     * make the box(with this indexes) visible
     */
    public void setVisible(Coordinates<Integer> coordinates){
        if (coordinates.getI() >= boxNumZ)
            theEnd = true;
        levels.get(coordinates.getI()).setVisible(coordinates.getJ(),coordinates.getK());
    }

    public Transition getRemoveLevelTransition(int levelIndex){
        boolean noVisibleBoxes = true;

        for (int j = levelIndex + 1; j < levels.size(); j++)
            if (levels.get(j).isAnyBoxVisible()){
                noVisibleBoxes = false;
                break;
            }
        Duration duration = noVisibleBoxes ? Duration.seconds(0.1) : Duration.seconds(2.0);

        ParallelTransition pt = new ParallelTransition();
        for (int j = levelIndex + 1; j < levels.size(); j++) {
            TranslateTransition tt = new TranslateTransition(duration, levels.get(j));
            tt.setByZ(-boxDimension);
            pt.getChildren().add(tt);
        }

        Level removed = levels.remove(levelIndex);
        removed.setInvisible();
        //move removed level to the top; TODO check if this must be done after ParallelTransition
        removed.setTranslateZ(removed.getTranslateZ() + boxDimension * (getHeightBoxesNum() - levelIndex));
        levels.add(removed);

        return pt;
    }

    public Transition getRemoveLevelsTransition(Integer... levelIndexes){
        SequentialTransition st = new SequentialTransition();
        // TODO check if levelIndexes has only unique elements(i.e. not OK:[1,2,2] OK:[1,2,3])
        Arrays.sort(levelIndexes);
        for (int i = levelIndexes.length - 1; i >= 0; i--) {
            st.getChildren().add(getRemoveLevelTransition(levelIndexes[i]));
        }
        return st;
    }

    private int getHeightBoxesNum(){
        return levels.size();
    }

    public void assignMaterial(){
        for (int i = 0; i < levels.size(); i++) {
            levels.get(i).setMaterial(matLevel[i]);
        }
    }

    public boolean intersects(Figure figure){
        for (int i = 0; i < levels.size(); i++)
            if (levels.get(i).intersects(figure))
                return true;
        return false;
    }

    /**
     * @param figure
     * @return if figure is inside draw-well
     */
    public boolean isInside(Figure figure){
        for (int i = 0; i < figure.getBoxes().length; i++) {
            Point3D mp = Geometry.getMiddlePointInScene(figure.getBoxes()[i]);
            Point3D mp1 = new Point3D(mp.getX(), mp.getY(), mp.getZ() - Main.BOX_DIMENSION * 0.4);
            if (! this.localToScene(this.getBoundsInLocal()).contains(mp1))
                return false;
        }
        return true;
    }

    public List<Integer> getFilledLevels(){
        List<Integer> res = new LinkedList<>();
        for (int i = 0; i < levels.size(); i++) {
            if (levels.get(i).isWholeLevelVisible())
                res.add(i);
        }
        return res;
    }

    public void addFigure(Figure figure){
        for (int i = 0; i < figure.getBoxes().length; i++) {
            setVisible(getIndexes(Geometry.getMiddlePointInScene(figure.getBoxes()[i])));
        }
        assignMaterial();
    }

    public boolean isTheEnd() {
        return theEnd;
    }
}
