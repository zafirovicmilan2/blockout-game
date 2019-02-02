package draw_well;

import javafx.geometry.Point3D;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class LocalAxes {

    private static int POSITIVE = 1;
    private static int NEGATIVE = -1;

    private Point3D xAxis = Rotate.X_AXIS;
    private int xAxisDirection = POSITIVE;
    private Point3D yAxis = Rotate.Y_AXIS;
    private int yAxisDirection = POSITIVE;
    private Point3D zAxis = Rotate.Z_AXIS;
    private int zAxisDirection = POSITIVE;

    public void rotate(Point3D axis, double angle){
        // this can be called just for angle = 90 or -90
        if (axis == Rotate.X_AXIS)
        {
            Point3D tempAxis = yAxis;
            yAxis = zAxis;
            zAxis = tempAxis;
            if (angle == -90)
            {
                int tempDirection = zAxisDirection;
                zAxisDirection = NEGATIVE * yAxisDirection;
                yAxisDirection = tempDirection;
            }
            else if (angle == 90)
            {
                int tempDirection = yAxisDirection;
                yAxisDirection = NEGATIVE * zAxisDirection;
                zAxisDirection = tempDirection;
            }

        }
        else if (axis == Rotate.Y_AXIS)
        {
            Point3D tempAxis = xAxis;
            xAxis = zAxis;
            zAxis = tempAxis;
            if (angle == -90)
            {
                int tempDirection = zAxisDirection;
                zAxisDirection = xAxisDirection;
                xAxisDirection = NEGATIVE * tempDirection;
            }
            else if (angle == 90)
            {
                int tempDirection = xAxisDirection;
                xAxisDirection = zAxisDirection;
                zAxisDirection = NEGATIVE * tempDirection;
            }
        }
        else if (axis == Rotate.Z_AXIS)
        {
            Point3D tempAxis = xAxis;
            xAxis = yAxis;
            yAxis = tempAxis;
            if (angle == -90)
            {
                int tempDirection = xAxisDirection;
                xAxisDirection = yAxisDirection;
                yAxisDirection = NEGATIVE * tempDirection;
            }
            else if (angle == 90)
            {
                int tempDirection = xAxisDirection;
                xAxisDirection = NEGATIVE * yAxisDirection;
                yAxisDirection = tempDirection;
            }
        }
    }

    public Point3D translateToLocalAxis(Point3D axis){
        if (axis == Rotate.X_AXIS){
            return xAxis;
        }else if (axis == Rotate.Y_AXIS){
            return yAxis;
        }else{  // axis == Rotate.Z_AXIS
            return zAxis;
        }
    }

    public double translateToLocalAngle(Point3D axis, double angle){
        if (axis == Rotate.X_AXIS){
            return xAxisDirection * angle;
        }else if (axis == Rotate.Y_AXIS){
            return yAxisDirection * angle;
        }else{  // axis == Rotate.Z_AXIS
            return zAxisDirection * angle;
        }
    }

    public Translate translateToLocalTranslation(Point3D axis, double move){
        Point3D temp;
        if (axis == Rotate.X_AXIS){
            temp = new Point3D(xAxis.getX(), xAxis.getY(), xAxis.getZ()).multiply(xAxisDirection * move);
        }else if (axis == Rotate.Y_AXIS){
            temp = new Point3D(yAxis.getX(), yAxis.getY(), yAxis.getZ()).multiply(yAxisDirection * move);
        }else{  // axis == Rotate.Z_AXIS
            temp = new Point3D(zAxis.getX(), zAxis.getY(), zAxis.getZ()).multiply(zAxisDirection * move);
        }

        return new Translate(temp.getX(), temp.getY(), temp.getZ());
    }




}