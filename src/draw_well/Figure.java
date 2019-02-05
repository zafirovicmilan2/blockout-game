package draw_well;

import geometry.Geometry;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;
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
    private LocalAxes localAxes = new LocalAxes();

    public Figure(double boxDimension, int numOfBoxes) {
        this.boxDimension = boxDimension;
        boxes = new Box[numOfBoxes];
        boxes[0] = new Box(boxDimension, boxDimension, boxDimension);  // TODO potential problem: this is not placed at (0,0,0)
        boxes[0].setDrawMode(DrawMode.LINE);
        boxes[0].setMaterial(new PhongMaterial(Color.BLACK));
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
            newBox.setDrawMode(DrawMode.LINE);
            newBox.setMaterial(new PhongMaterial(Color.BLACK));
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

    public Box[] getBoxes() {
        return boxes;
    }

    public boolean addTransformation(Transform transform, DrawWell drawWell){
        Transform inverse = null;
        try {
            inverse = transform.createInverse();
        } catch (NonInvertibleTransformException e) {
            e.printStackTrace();
        }
        getTransforms().add(transform);
        if (drawWell.intersects(this) || (!drawWell.isInside(this))){
            getTransforms().add(inverse);
            return false;
        }
        return true;
    }

    public boolean rotate(Point3D axis, double angle, DrawWell drawWell){
        Point3D pivot = getRotationPivot();
        Rotate r = new Rotate(localAxes.translateToLocalAngle(axis, angle), pivot.getX(), pivot.getY(), pivot.getZ(), localAxes.translateToLocalAxis(axis));
        if (addTransformation(r, drawWell)){
            localAxes.rotate(axis, angle);
            return true;
        }else
            return false;
    }

    public boolean translate(Point3D axis, double move, DrawWell drawWell){
        return addTransformation(localAxes.translateToLocalTranslation(axis, move), drawWell);
    }
}
