import draw_well.DrawWell;
import draw_well.Frame;
import draw_well.Level;
import geometry.Coordinates;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        PerspectiveCamera pc = new PerspectiveCamera(true);

        Rotate t = new Rotate(180,0,0,0,Rotate.Y_AXIS);
        pc.setNearClip(0.1);
        pc.setFarClip(5000);
        pc.getTransforms().addAll(t);
        pc.setTranslateZ(250);
        pc.setTranslateX(50);
        pc.setTranslateY(30);


        Group root = new Group();

        /*Frame f = new Frame(20,5,4,3);
        root.getChildren().add(f);
        System.out.println("FRAME: " + f.getBoundsInParent());*/

        /*Level l = new Level(20,4,4);
        root.getChildren().add(l);
        l.setVisible(0,0);
        l.setVisible(1,0);
        //l.setVisible(2,2);
        l.setVisible(3,3);
        System.out.println("LEVEL: " + l.getBoundsInParent());*/

        PhongMaterial [] niz = new PhongMaterial[5];
        DrawWell dw = new DrawWell(20,2,2,2,0,niz);
        /*dw.setVisible(new Coordinates<>(0,2,2));
        dw.setVisible(new Coordinates<>(1,2,2));
        dw.setVisible(new Coordinates<>(2,2,2));

        dw.setVisible(new Coordinates<>(0,0,0));
        dw.setVisible(new Coordinates<>(1,0,0));
        dw.setVisible(new Coordinates<>(2,0,0));
        Transition tra = dw.removeLevels(0,1);
        tra.play();
        tra.setOnFinished(e->{        dw.setVisible(new Coordinates<>(2,0,0));});*/
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    System.out.println(dw.levels.get(i).boxes[j][k].localToScene(dw.levels.get(i).boxes[j][k].getBoundsInLocal()));
                }
            }
        }



        root.getChildren().addAll(dw);

        Scene scene = new Scene(root, 700, 300,true);
        scene.setCamera(pc );
        primaryStage.setTitle("Svemirci");
        primaryStage.setScene(scene);
        primaryStage.show();

    }}