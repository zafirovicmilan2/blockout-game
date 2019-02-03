import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.transform.Rotate;
import main.Main;

public class Camera {

    private static double TRANSLATE_VELOCITY = 5.0;
    private static double ROTATE_VELOCITY = 2.0;

    public static double POSITIVE_DIRECTION = 1.0;
    public static double NEGATIVE_DIRECTION = -1.0;

    private Scene scene;
    private PerspectiveCamera defaultCamera;
    private PerspectiveCamera definedCamera;
    private Group definedCameraHolder;
    private boolean defaultOrDefined = true;

    public Camera(Scene scene) {
        this.scene = scene;
        defaultCamera = new PerspectiveCamera(true);
        setUpDefaultCamera(defaultCamera);
        definedCamera = new PerspectiveCamera(true);
        setUpDefinedCamera(definedCamera, definedCameraHolder);
        definedCameraHolder = new Group(definedCamera); // TODO maybe to add this to root
        changeCamera(true);
    }

    private void setUpDefaultCamera(PerspectiveCamera defaultCamera){
        Rotate t = new Rotate(180,0,0,0,Rotate.Y_AXIS);
        defaultCamera.setNearClip(0.1);
        defaultCamera.setFarClip(5000);
        defaultCamera.getTransforms().add(t);
        defaultCamera.setTranslateZ(2.0 * Main.BOX_DIMENSION * (Main.BOX_NUM_X + Main.BOX_NUM_EXT_Z));
        defaultCamera.setTranslateX(Main.BOX_DIMENSION * Main.BOX_NUM_X / 2);
        defaultCamera.setTranslateY(Main.BOX_DIMENSION * Main.BOX_NUM_Y / 2);
    }

    private void setUpDefinedCamera(PerspectiveCamera definedCamera, Group definedCameraHolder){
        Rotate t = new Rotate(90,0,0,0,Rotate.Y_AXIS);
        definedCamera.setNearClip(0.1);
        definedCamera.setFarClip(5000);
        definedCamera.getTransforms().add(t);
        definedCameraHolder.setTranslateX(Main.BOX_DIMENSION * Main.BOX_NUM_X / 2);
        definedCameraHolder.setTranslateY(Main.BOX_DIMENSION * Main.BOX_NUM_Y / 2);
        definedCameraHolder.setTranslateZ(Main.BOX_DIMENSION * (Main.BOX_NUM_X + Main.BOX_NUM_EXT_Z) / 2);
    }

    private void changeCamera(boolean defaultOrDefined){
        this.defaultOrDefined = defaultOrDefined;
        if (defaultOrDefined)
            scene.setCamera(defaultCamera);
        else
            scene.setCamera(definedCamera);
    }

    public void tilt(double direction){
        if (!defaultOrDefined){
            if (definedCamera.getRotationAxis() != Rotate.X_AXIS) {
                definedCamera.setRotationAxis(Rotate.X_AXIS);
                definedCamera.setRotate(0);
            } else
                definedCamera.setRotationAxis(Rotate.X_AXIS);
            definedCamera.setRotate(definedCamera.getRotate() + direction * ROTATE_VELOCITY);
        }
    }

    public void yaw(double direction){
        if (!defaultOrDefined){
            if (definedCamera.getRotationAxis() != Rotate.Y_AXIS) {
                definedCamera.setRotationAxis(Rotate.Y_AXIS);
                definedCamera.setRotate(0);
            } else
                definedCamera.setRotationAxis(Rotate.Y_AXIS);
            definedCamera.setRotate(definedCamera.getRotate() + direction * ROTATE_VELOCITY);
        }
    }

    public void translateHolder(double direction){
        if (!defaultOrDefined)
            definedCameraHolder.setTranslateZ(definedCameraHolder.getTranslateZ() + direction * TRANSLATE_VELOCITY);
    }


}
