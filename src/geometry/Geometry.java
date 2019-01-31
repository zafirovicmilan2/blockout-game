package geometry;

import javafx.geometry.Bounds;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.transform.Translate;

public class Geometry {

    /**
     * Position lower left corner of the node to (0,0,0)
     */
    public static void positionToZero(Node node){
        positionTo(node, 0, 0, 0);
    }

    /**
     * Position lower left corner of the node to (x,y,z)
     */
    public static void positionTo(Node node, double x, double y, double z){
        Bounds bounds = node.localToScene(node.getBoundsInLocal());
        double tx = - bounds.getMinX() + node.getTranslateX();
        double ty = - bounds.getMinY() + node.getTranslateY();
        double tz = - bounds.getMinZ() + node.getTranslateZ();
        node.getTransforms().addAll(new Translate(tx + x, ty + y, tz + z));
    }

    public static boolean haveSameBoundsInParent(Node n1, Node n2){
        return n1.getBoundsInParent() == n2.getBoundsInParent();
    }

    public static Point3D getLowerLeftPointInParent(Node node){
        double x = node.getBoundsInParent().getMinX();
        double y = node.getBoundsInParent().getMinY();
        double z = node.getBoundsInParent().getMinZ();
        return new Point3D(x, y, z);
    }

    public static Point3D getMiddlePointInScene(Node node){
        Bounds bounds = node.localToScene(node.getBoundsInLocal());
        double x = (bounds.getMinX() + bounds.getMaxX())/2;
        double y = (bounds.getMinY() + bounds.getMaxY())/2;
        double z = (bounds.getMinZ() + bounds.getMaxZ())/2;
        return new Point3D(x, y, z);
    }

}
