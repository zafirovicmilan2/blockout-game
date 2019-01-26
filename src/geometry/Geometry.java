package geometry;

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

}
