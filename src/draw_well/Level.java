package draw_well;

import javafx.scene.Group;
import javafx.scene.paint.Material;
import javafx.scene.shape.Box;

public class Level extends Group {

    public Box [][] boxes;
    private int boxNumX, boxNumY;
    private int visibleBoxesCnt = 0;

    public Level(double boxDimension, int boxNumX, int boxNumY) {
        this.boxNumX = boxNumX;
        this.boxNumY = boxNumY;
        boxes = new Box[boxNumY][];
        double tx = 0, ty = 0;
        for (int i = 0; i < boxNumY; i++) {
            boxes[i] = new Box[boxNumX];
            for (int j = 0; j < boxNumX; j++) {
                Box box = new Box(boxDimension, boxDimension, boxDimension);
                box.setTranslateX(box.getTranslateX() + tx + boxDimension/2);
                box.setTranslateY(box.getTranslateY() + ty + boxDimension/2);
                box.setTranslateZ(box.getTranslateZ() + boxDimension/2);
                boxes[i][j] = box;
                box.setVisible(false);
                getChildren().add(box);
                tx += boxDimension;
            }
            tx = 0;
            ty += boxDimension;
        }
    }

    /**
     * Set material for the whole level
     * @param material
     */
    public void setMaterial(Material material){
        for (int i = 0; i < boxNumY; i++) {
            for (int j = 0; j < boxNumX; j++) {
                boxes[i][j].setMaterial(material);
            }
        }
    }

    /**
     * Set visibility for just one box
     */
    public void setVisible(int i, int j){
        if(i>=0 && j>=0 && i<boxNumX &&  j<boxNumY)
            if(!boxes[i][j].isVisible()){
                boxes[i][j].setVisible(true);
                ++visibleBoxesCnt;
            }
    }

    /**
     * Make the whole level invisible
     */
    public void setInvisible(){
        for (int i = 0; i < boxNumY; i++) {
            for (int j = 0; j < boxNumX; j++) {
                boxes[i][j].setVisible(false);
            }
        }
        visibleBoxesCnt = 0;
    }

    /**
     * Checks if all boxes at this level are visible
     */
    public boolean isWholeLevelVisible(){
        return visibleBoxesCnt == (boxNumX * boxNumY);
    }


}
