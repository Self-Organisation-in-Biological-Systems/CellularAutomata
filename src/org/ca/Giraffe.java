package org.ca;

public class Giraffe {

    public static void main(String[] args) {
        Giraffe g = new Giraffe();
        g.run();
    }

    private void run() {
        ControlFrame controlFrame = new ControlFrame();

        GraphicFrame graphic = new GraphicFrame();
        graphic.createGraphicFrame(controlFrame.getXSize(), controlFrame.getYSize());

        Tick tick = new Tick(controlFrame, graphic);
        tick.init();
    }
}
