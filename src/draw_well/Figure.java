package draw_well;

import geometry.Geometry;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.shape.Box;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;

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
        loop: while(index != numOfBoxes){

            Box newBox = new Box(boxDimension, boxDimension, boxDimension);  // TODO potential problem: this is not placed at (0,0,0)
            Point3D randomTranslation = getRandomTranslation();
            Point3D base = Geometry.getLowerLeftPointInParent(boxes[randomGenerator.nextInt(index)]);  // box that newBox will be added to
            Geometry.positionToZero(newBox);
            newBox.setTranslateX(newBox.getTranslateX() + base.getX() + randomTranslation.getX());
            newBox.setTranslateY(newBox.getTranslateY() + base.getY() + randomTranslation.getY());
            newBox.setTranslateZ(newBox.getTranslateZ() + base.getZ() + randomTranslation.getZ());

            for (int i = 0; i < index; i++)
                if(Geometry.haveSameBoundsInParent(boxes[i], newBox))
                    continue loop;

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
            return minBound + boxDimension*(num/2 - 0.5);
        }else
            return (maxBound + minBound)/2;
    }

    private Point3D getRotationPivot(){
        double x = getMiddleForRotation(getBoundsInLocal().getMinX(), getBoundsInLocal().getMaxX());
        double y = getMiddleForRotation(getBoundsInLocal().getMinY(), getBoundsInLocal().getMaxY());
        double z = getMiddleForRotation(getBoundsInLocal().getMinZ(), getBoundsInLocal().getMaxZ());
        return new Point3D(x, y, z);
    }

    /**
     * @param angle
     * @param axis - coordinates from scene coordinate system, not local
     */
    public Rotate getRotation(double angle, Point3D axis){
        // rotation should be used for angle = ...-180,-90,0,90,180,270,...
        Point3D pivot = getRotationPivot();
        Rotate r = new Rotate(angle, pivot.getX(), pivot.getY(), pivot.getZ(), axis);
        return r;
    }

    public Box[] getBoxes() {
        return boxes;
    }

    public void addTransformation(Transform transform, DrawWell drawWell){
        Transform inverse = null;
        try {
            inverse = transform.createInverse();
        } catch (NonInvertibleTransformException e) {
            e.printStackTrace();
        }
        getTransforms().add(transform);
        if (drawWell.intersects(this))
            getTransforms().add(inverse);
    }
}
