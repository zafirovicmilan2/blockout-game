package geometry;

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
        double tx = - node.getBoundsInLocal().getMinX();
        double ty = - node.getBoundsInLocal().getMinY();
        double tz = - node.getBoundsInLocal().getMinZ();
        node.getTransforms().addAll(new Translate(tx + x, ty + y, tz + z));
    }

    public static boolean isInside(Node node, Point3D point){
        if (node.getBoundsInParent().getMinX() > point.getX())
            return false;
        if (node.getBoundsInParent().getMinY() > point.getY())
            return false;
        if (node.getBoundsInParent().getMinZ() > point.getZ())
            return false;
        if (node.getBoundsInParent().getMaxX() < point.getX())
            return false;
        if (node.getBoundsInParent().getMaxY() < point.getY())
            return false;
        if (node.getBoundsInParent().getMaxZ() < point.getZ())
            return false;
        return true;
    }

    public static boolean haveSameBounds(Node n1, Node n2){
        return n1.getBoundsInParent() == n2.getBoundsInParent();
    }

    public static Point3D getLowerLeftPoint(Node node){
        double x = node.getBoundsInParent().getMinX();
        double y = node.getBoundsInParent().getMinY();
        double z = node.getBoundsInParent().getMinZ();
        return new Point3D(x, y, z);
    }

    public static Point3D getMiddlePoint(Node node){
        double x = (node.getBoundsInParent().getMinX() + node.getBoundsInParent().getMaxX())/2;
        double y = (node.getBoundsInParent().getMinY() + node.getBoundsInParent().getMaxY())/2;
        double z = (node.getBoundsInParent().getMinZ() + node.getBoundsInParent().getMaxZ())/2;
        return new Point3D(x, y, z);
    }

}
