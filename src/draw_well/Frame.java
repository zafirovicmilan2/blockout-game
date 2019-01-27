package draw_well;

import javafx.scene.Group;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;

public class Frame extends Group {
    public static double WIRE_RADIUS = 1.0;

    public Frame(double boxDimension, int boxNumX, int boxNumY, int boxNumZ) {
        // TODO add material to arguments
        getChildren().add(createFrame(boxDimension, boxNumX, boxNumY, boxNumZ));
    }

    /**
     * This method creates one side i.e. the net of cylinders and positions it to (0,0) in XoY system
     * @param boxDimension
     * @param boxNumWidth number of boxes in X axis direction
     * @param boxNumHeight number of boxes in Y axis direction
     * @return
     */
    private Group createSide(double boxDimension, int boxNumWidth, int boxNumHeight){
        Group res = new Group();
        double tx = 0;
        for(int i =0; i <= boxNumWidth; i++){
            Cylinder c = new Cylinder(WIRE_RADIUS, boxDimension * boxNumHeight);
            //c.setMaterial(material);
            c.setTranslateX(c.getTranslateX() + tx);
            res.getChildren().add(c);
            tx += boxDimension;
        }
        double ty = 0;
        for(int i =0; i <= boxNumHeight; i++){
            Cylinder c = new Cylinder(WIRE_RADIUS, boxDimension * boxNumWidth);
            //c.setMaterial(material);
            c.setRotate(-90);
            c.setTranslateX(c.getTranslateX() + c.getBoundsInLocal().getHeight()/2);
            c.setTranslateY(c.getTranslateY() - boxDimension*boxNumHeight/2 + ty);
            res.getChildren().add(c);
            ty += boxDimension;
        }
        res.setTranslateY(res.getTranslateY() + boxDimension * boxNumHeight / 2);
        return res;
    }

    private Group createFrame(double boxDimension, int boxNumX, int boxNumY, int boxNumZ){
        Group res = new Group();

        Group bottom = createSide(boxDimension, boxNumX, boxNumY);

        Group side1 = createSide(boxDimension, boxNumZ, boxNumY);
        Rotate r1 = new Rotate(90, Rotate.Y_AXIS);
        r1.setPivotX(side1.getBoundsInLocal().getMinX() + WIRE_RADIUS);
        r1.setPivotZ(side1.getBoundsInLocal().getMinZ() + WIRE_RADIUS);
        side1.getTransforms().add(r1);

        Group side2 = createSide(boxDimension, boxNumX, boxNumZ);
        Rotate r2 = new Rotate(-90, Rotate.X_AXIS);
        r2.setPivotY(side2.getBoundsInLocal().getMinY() + WIRE_RADIUS);
        r2.setPivotZ(side2.getBoundsInLocal().getMinZ() + WIRE_RADIUS);
        side2.getTransforms().add(r2);

        Group side3 = createSide(boxDimension, boxNumZ, boxNumY);
        side3.getTransforms().add(r1);
        side3.setTranslateX(side3.getTranslateX() + boxNumX * boxDimension);

        Group side4 = createSide(boxDimension, boxNumX, boxNumZ);
        side4.getTransforms().add(r2);
        side4.setTranslateY(side4.getTranslateY() + boxNumY * boxDimension);

        res.getChildren().addAll(bottom, side1, side2, side3, side4);

        return res;
    }
}
