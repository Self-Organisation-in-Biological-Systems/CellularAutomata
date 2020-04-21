package org.ca;


import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author jbberinger
 *
 * This program generates natural plant forms called phyllotaxis.
 * Pine cones, romanesco, and sunflowers are good examples of this.
 *
 * Inspiration: https://youtu.be/KWoJgHFYWxY
 * Based on: http://algorithmicbotany.org/papers/abop/abop-ch4.pdf
 *
 */
public class Phyllotaxis extends Application {

    final double WIDTH = 800; // window width
    final double HEIGHT = 800; // window height

    double n = 0; // ordering number of object
    double phi; // divergence phiAngle
    double r; // radius from origin to object center
    double xPos, yPos; // shape positions

    /**
     * Manipulate these values.
     *
     * Try setting phiAngle at 137.3, 137.5, or 137.6 from the paper.
     * Interesting results at phiAngle 30 and 45.
     *
     */

    double phiAngle = 137.5; // phiAngle multiplied by n to calculate phi
    double C = 15; // scaling factor
    double objectSize = 6; // size of objects

    static List<Ellipse> ellipseList = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {

        Pane root = new Pane();
        root.setMinSize(WIDTH, HEIGHT);

        Timeline timeline = new Timeline();
        Duration timepoint = Duration.ZERO;
        Duration pause = Duration.seconds(1.0 / 240);

        while (n < 10000) {

            phi = Math.toRadians(n * phiAngle);
            r = C * Math.sqrt(n);
            xPos = (WIDTH / 2) + (r * Math.cos(phi));
            yPos = (HEIGHT / 2) + (r * Math.sin(phi));

            Ellipse ellipse = new Ellipse(xPos, yPos, objectSize, objectSize);
            //ellipse.setFill(Color.rgb(0, (int)n % 255, (int)n % 10));
            ellipse.setFill(Color.hsb(n % 256, 1, 1));
            ellipse.setStroke(Color.BLACK);
            ellipse.setStrokeWidth(0.1);
            ellipse.setSmooth(true);

            timepoint = timepoint.add(pause);
            KeyFrame keyFrame = new KeyFrame(timepoint, e -> root.getChildren().addAll(ellipse));
            timeline.getKeyFrames().add(keyFrame);

            n++;

        }

        Scene scene = new Scene(root);
        scene.setFill(Color.BLACK);
        primaryStage.setTitle("Phyllotaxis with " + (int) n + " objects");
        primaryStage.setScene(scene);
        primaryStage.show();

        timeline.play();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}