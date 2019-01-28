package draw_well;

import geometry.Geometry;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.shape.Box;

import java.util.Random;

public class Figure extends Group {

    private Box[] boxes;
    private double boxDimension;
    private Point3D[] randomTranslations = null;
    private Random randomGenerator = null;

    public Figure(double boxDimension, int numOfBoxes) {
        this.boxDimension = boxDimension;
        boxes = new Box[numOfBoxes];
        boxes[0] = new Box(boxDimension, boxDimension, boxDimension);  // TODO potential problem: this is not placed at (0,0,0)
        int index = 1;
        while(index != numOfBoxes){

            Box newBox = new Box(boxDimension, boxDimension, boxDimension);  // TODO potential problem: this is not placed at (0,0,0)
            Point3D randomTranslation = getRandomTranslation();
            Point3D base = Geometry.getLowerLeftPoint(boxes[randomGenerator.nextInt(index)]);  // box that newBox will be added to

            newBox.setTranslateX(getTranslateX() + base.getX() + randomTranslation.getX());
            newBox.setTranslateY(getTranslateY() + base.getY() + randomTranslation.getY());
            newBox.setTranslateZ(getTranslateZ() + base.getZ() + randomTranslation.getZ());

            for (int i = 0; i < index; i++)
                if(Geometry.haveSameBounds(boxes[i], newBox))
                    continue;

            boxes[index++] = newBox;
        }

        for (int i = 0; i < numOfBoxes; i++) {
            getChildren().add(boxes[i]);
        }
    }

    private Point3D getRandomTranslation(){
        if (randomTranslations == null){
            randomGenerator = new Random();
            randomTranslations = new Point3D[]{
                    new Point3D(boxDimension,0,0),new Point3D(0,boxDimension,0),new Point3D(0,0,boxDimension),
                    new Point3D(-boxDimension,0,0),new Point3D(0,-boxDimension,0),new Point3D(0,0,-boxDimension)
            };
        }

        return randomTranslations[randomGenerator.nextInt(6)];
    }
}
