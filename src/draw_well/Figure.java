package draw_well;

import geometry.Geometry;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;

import java.util.Random;

public class Figure extends Group {
    // TODO add material to Figure
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
            Point3D base = Geometry.getLowerLeftPointInParent(boxes[randomGenerator.nextInt(index)]);  // box that newBox will be added to

            newBox.setTranslateX(getTranslateX() + base.getX() + randomTranslation.getX());
            newBox.setTranslateY(getTranslateY() + base.getY() + randomTranslation.getY());
            newBox.setTranslateZ(getTranslateZ() + base.getZ() + randomTranslation.getZ());

            for (int i = 0; i < index; i++)
                if(Geometry.haveSameBoundsInParent(boxes[i], newBox))
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

    private double getMiddleForRotation(double minBound, double maxBound){
        int num = (int) ((maxBound - minBound)/boxDimension);
        if (num%2 == 0) {
            return boxDimension*(num/2 - 1/2);
        }else
            return (maxBound - minBound)/2;
    }

    private Point3D getRotationPivot(Point3D axis){
        // TODO check if axis != (X_AXIS or Y_AXIS or Z_AXIS)
        double x = 0.0, y = 0.0, z = 0.0;
        if(axis == Rotate.X_AXIS){
            y = getMiddleForRotation(getBoundsInParent().getMinY(), getBoundsInParent().getMaxY());
            z = getMiddleForRotation(getBoundsInParent().getMinZ(), getBoundsInParent().getMaxZ());
        }else if (axis == Rotate.Y_AXIS){
            x = getMiddleForRotation(getBoundsInParent().getMinX(), getBoundsInParent().getMaxX());
            z = getMiddleForRotation(getBoundsInParent().getMinZ(), getBoundsInParent().getMaxZ());
        }else if (axis == Rotate.Z_AXIS){
            x = getMiddleForRotation(getBoundsInParent().getMinX(), getBoundsInParent().getMaxX());
            y = getMiddleForRotation(getBoundsInParent().getMinY(), getBoundsInParent().getMaxY());
        }

        return new Point3D(x, y, z);
    }

    private void rotate(double angle, Point3D axis){
        // rotation should be used for angle = ...-180,-90,0,90,180,270,...
        Point3D pivot = getRotationPivot(axis);
        Rotate r = new Rotate(angle, pivot.getX(), pivot.getY(), pivot.getZ(), axis);
        getTransforms().add(r);
    }

    public Box[] getBoxes() {
        return boxes;
    }
}
