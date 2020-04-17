package org.ca;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GraphicFrame extends JPanel {
    JFrame frame;
    private ControlFrame mControlFrame;
    private BufferedImage mGiraffeImage;

    public GraphicFrame(ControlFrame controlFrame) {
        mControlFrame = controlFrame;
    }

    public void createGraphicFrame() {
        mGiraffeImage = new BufferedImage(mControlFrame.getXSize(), mControlFrame.getYSize(),
                BufferedImage.TYPE_INT_ARGB);

        frame = new JFrame("Welcome to Giraffe World!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(mControlFrame.getXSize(), mControlFrame.getYSize());

        JPanel panel = new GiraffeJPanel();
        frame.add(panel);
        frame.validate();
        frame.repaint();
    }

    class GiraffeJPanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(mGiraffeImage, 0, 0, null);
        }
    }

    //don't use
//    public void drawGiraffeImage(int tick.getCellCount(), double[] cellB, int xSize) {
//        int x = 0;
//        int y = 0;
//        //rgb for a giraffe brownish color = 209 111 46
//        for (int i = 0; i < tick.getCellCount(); i++) {
//            int r = 255 - (int)(Math.floor(cellB[i] * 46));
//            if (r < 209) r = 209;
//            int g = 255 - (int)(Math.floor(cellB[i] * 145));
//            if (g < 111) g = 111;
//            int b = 255 - (int)(Math.floor(cellB[i] * 209));
//            if (b < 46) b = 46;
//
//            mGiraffeImage.setRGB(x, y, new Color(r,g,b).getRGB());
//            x++;
//            if (x >= xSize) {
//                x = 0;
//                y++;
//            }
//        }
//
//        frame.validate();
//        frame.repaint();
//    }




    //--------------------------------- orig code -----------------------------------

    //main drawing routine, will branch to the selected drawing mode based on checkboxes and stuff
    public void mainDraw(Tick tick) {
        if (mControlFrame.drawInGiraffeColors()) {
            drawAsGiraffe(tick);
        } else if (mControlFrame.drawScaledToMax()) {
            drawAsScaled(tick);
        } else {
            int x = 0;
            int y = 0;
            int r = 0;
            int g = 0;
            int b = 0;

            if (mControlFrame.showCellStates()) {
                for (int i = 0; i < tick.getCellCount(); i++) {
                    if (tick.getCellState(i)) {
                        if (!tick.getTryToActivateNeighbors(i)) { r=0; g=255; b=255; }
                        else { r=255; g=0; b=0; }
                    } else { r=0; g=0; b=0; }
                    mGiraffeImage.setRGB(x, y, new Color(r,g,b).getRGB());
                    x++; //track cell location where we are drawing
                    if (x >= mControlFrame.getXSize()) {
                        x = 0;
                        y++;
                    }
                }
            } else {
                ///rgb for a giraffe brownish hcolor = 209 111 46
//                if (tick.getTickCount() % 2 == 0) c = 0;
//                else c = 254;
                int mult = (int)Math.round(255.0 / tick.getStartA());//scale blue/yellow values by starting value so they don't overdrive
                for (int i = 0; i < tick.getCellCount(); i++) {
                    int c1 = (int)Math.floor(tick.getCellA(i) * mult); //get colors to draw A and B values graphically
                    if (c1 > 255) c1 = 255;
                    int c2 = (int)Math.floor(tick.getCellB(i) * mult);
                    if (c2>255) c2=255;
                    //if (tryToActivateNeighbors[i]) ctx.fillStyle = "rgb("+a+",255,"+b+")";
                    //else if (cellState[i])   ctx.fillStyle = "rgb("+a+","+(c/2)+","+b+")";
                    //else                     ctx.fillStyle = "rgb("+a+",0,"+b+")";

//                    if(c1 <0 || c1 > 255)
//                        System.out.println("c1="+c1);
//                    if(c2 <0 || c2 > 255)
//                        System.out.println("c2="+c2);

                    mGiraffeImage.setRGB(x, y, new Color(c1,c1,c2).getRGB());
                    x++; //track cell location where we are drawing
                    if (x >= mControlFrame.getXSize()) {
                        x = 0;
                        y++;
                    }
                }
            }
        }

        frame.validate();
        frame.repaint();
    }

    private void drawAsGiraffe(Tick tick) {
        if (mControlFrame.getPigmentThreshold() > 0) {
            drawWithThresholds(tick);
            return;
        }
        int x = 0;
        int y = 0;
        ///rgb for a giraffe brownish hcolor = 209 111 46
        for (int i = 0; i < tick.getCellCount(); i++) {
            int r = 255 - (int)(Math.floor(tick.getCellB(i) * 46));
            if (r < 209) r = 209;
            int g = 255 - (int)(Math.floor(tick.getCellB(i) * 145));
            if (g < 111) g = 111;
            int b = 255 - (int)(Math.floor(tick.getCellB(i) * 209));
            if (b < 46) b = 46;
            mGiraffeImage.setRGB(x, y, new Color(r,g,b).getRGB());
            x++; //track cell location where we are drawing
            if (x >= mControlFrame.getXSize()) {
                x = 0;
                y++;
            }
        }
    }

    private void drawAsScaled(Tick tick) {
        int histoBanding = 0; //mControlFrame.getBanding();
        if (histoBanding > 0) {
            drawAsHisto(tick, histoBanding);
        } else {
            int x = 0;
            int y = 0;
            //create table based on max B value
            double bMax = 0; //find biggest B value
            double colorMult;
            for (int i = 0; i < tick.getCellCount(); i++) {
                if (tick.getCellB(i) > bMax) bMax = tick.getCellB(i);
            }
            if (bMax > 0) colorMult = 255 / bMax;
            else colorMult = 0;
//            document.getElementById('maxLabel').innerHTML = "(" + bMax.toFixed(2) + ")";

            for (int i = 0; i < tick.getCellCount(); i++) {
                int g = (int)Math.round(tick.getCellB(i) * colorMult);
                mGiraffeImage.setRGB(x, y, new Color(g,g,g).getRGB());

                x++; //track cell location where we are drawing
                if (x >= mControlFrame.getXSize()) {
                    x = 0;
                    y++;
                }
            }
        }
    }

    private void drawWithThresholds(Tick tick) {
        int x = 0;
        int y = 0;
        int r = 0;
        int g = 0;
        int b = 0;
        for (int i = 0; i < tick.getCellCount(); i++) {
            if (tick.getCellB(i) > mControlFrame.getPigmentThreshold())
                { r=209; g=111; b=46;}
            else
                { r=255; g=255; b=255;}
            mGiraffeImage.setRGB(x, y, new Color(r,g,b).getRGB());
            x++; //track cell location where we are drawing
            if (x >= mControlFrame.getXSize()) {
                x = 0;
                y++;
            }
        }
    }

    private void drawAsHisto(Tick tick, int histoBanding) {
        int x = 0;
        int y = 0;
        int r = 0;
        int g = 0;
        int b = 0;
        //create table based on max B value
        double bMax = 0; //find biggest B value
        double colorMult;
        for (int i = 0; i < tick.getCellCount(); i++) {
            if (tick.getCellB(i) > bMax) bMax = tick.getCellB(i);
        }
        if (bMax > 0) colorMult = 255 / bMax;
        else colorMult = 0;
//        document.getElementById('maxLabel').innerHTML = "(" + bMax.toFixed(2) + ")";

        for (int i = 0; i < tick.getCellCount(); i++) {
            int g1 = (int)Math.round(tick.getCellB(i) * colorMult);
            if (g1 == 0) {
                { r=0; g=255; b=0;}
            } else {
                if (g % histoBanding == 0) {
                    { r=255; g=255; b=255;}
                } else {
                    { r=0; g=0; b=0;}
                }
            }
            mGiraffeImage.setRGB(x, y, new Color(r,g,b).getRGB());
            x++; //track cell location where we are drawing
            if (x >= mControlFrame.getXSize()) {
                x = 0;
                y++;
            }
        }
    }
}
