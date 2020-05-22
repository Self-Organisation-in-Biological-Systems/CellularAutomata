package org.ca;

import org.ca.data.ModelSettings;
import org.ca.data.ModelState;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GraphicFrame extends JPanel {
    private JFrame mGraphicsFrame;
    private ModelState mState;
    private ModelSettings mSettings;
    private BufferedImage mGiraffeImage;

    //private double scaleFactor=2.5;

    public GraphicFrame(ModelState state, ModelSettings settings) {
        mGraphicsFrame = new JFrame("Welcome to Giraffe World!");
        mState = state;
        mSettings = settings;
    }

    public void createGraphicFrame() {
        mGiraffeImage = new BufferedImage(mSettings.getXSize(), mSettings.getYSize(),
                BufferedImage.TYPE_INT_ARGB);

        mGraphicsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mGraphicsFrame.setVisible(true);
        //mGraphicsFrame.setSize((int)(mSettings.getXSize()*scaleFactor), (int)(mSettings.getYSize()*scaleFactor));
        mGraphicsFrame.setSize((int)(mSettings.getXSize()), (int)(mSettings.getYSize()));

        JPanel panel = new GiraffeJPanel();
        mGraphicsFrame.add(panel);
        mGraphicsFrame.validate();
        mGraphicsFrame.repaint();
    }

    class GiraffeJPanel extends JPanel {
        //@Override
        public void paintComponent(Graphics g) {
//            super.paintComponent(g);
//            g.drawImage(mGiraffeImage, 0, 0, null);

            Graphics2D g2d = (Graphics2D) g;
            //g2d.scale(scaleFactor, scaleFactor);
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
    public void mainDraw() {
        if (mSettings.drawInGiraffeColors()) {
            drawAsGiraffe();
        } else if (mSettings.drawScaledToMax()) {
            drawAsScaled();
        } else {
            int x = 0;
            int y = 0;
            int r = 0;
            int g = 0;
            int b = 0;

            if (mSettings.showCellStates()) {
                for (int i = 0; i < mState.getCellCount(); i++) {
                    if (mState.getCellState(i)) {
                        if (!mState.tryToActivateNeighbors(i)) { r=0; g=255; b=255; }
                        else { r=255; g=0; b=0; }
                    } else { r=0; g=0; b=0; }
                    mGiraffeImage.setRGB(x, y, new Color(r,g,b).getRGB());
                    x++; //track cell location where we are drawing
                    if (x >= mSettings.getXSize()) {
                        x = 0;
                        y++;
                    }
                }
            } else {
                ///rgb for a giraffe brownish hcolor = 209 111 46
//                if (tick.getTickCount() % 2 == 0) c = 0;
//                else c = 254;
                int mult = (int)Math.round(255.0 / mSettings.getStartA());//scale blue/yellow values by starting value so they don't overdrive
                for (int i = 0; i < mState.getCellCount(); i++) {
                    int c1 = (int)Math.floor(mState.getCellA(i) * mult); //get colors to draw A and B values graphically
                    if (c1 > 255) c1 = 255;
                    int c2 = (int)Math.floor(mState.getCellB(i) * mult);
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
                    if (x >= mSettings.getXSize()) {
                        x = 0;
                        y++;
                    }
                }
            }
        }

        mGraphicsFrame.validate();
        mGraphicsFrame.repaint();
    }

    private void drawAsGiraffe() {
        if (mSettings.getPigmentThreshold() > 0) {
            drawWithThresholds();
            return;
        }
        int x = 0;
        int y = 0;
        ///rgb for a giraffe brownish hcolor = 209 111 46
        for (int i = 0; i < mState.getCellCount(); i++) {
            int r = 255 - (int)(Math.floor(mState.getCellB(i) * 46));
            if (r < 209)
                r = 209;
            int g = 255 - (int)(Math.floor(mState.getCellB(i) * 145));
            if (g < 111)
                g = 111;
            int b = 255 - (int)(Math.floor(mState.getCellB(i) * 209));
            if (b < 46)
                b = 46;
            mGiraffeImage.setRGB(x, y, new Color(r,g,b).getRGB());
            x++; //track cell location where we are drawing
            if (x >= mSettings.getXSize()) {
                x = 0;
                y++;
            }
        }
    }

    private void drawAsScaled() {
        int histoBanding = 0; //mControlFrame.getBanding();
        if (histoBanding > 0) {
            drawAsHisto(histoBanding);
        } else {
            int x = 0;
            int y = 0;
            //create table based on max B value
            double bMax = 0; //find biggest B value
            double colorMult;
            for (int i = 0; i < mState.getCellCount(); i++) {
                if (mState.getCellB(i) > bMax) bMax = mState.getCellB(i);
            }
            if (bMax > 0) colorMult = 255 / bMax;
            else colorMult = 0;
//            document.getElementById('maxLabel').innerHTML = "(" + bMax.toFixed(2) + ")";

            for (int i = 0; i < mState.getCellCount(); i++) {
                int g = (int)Math.round(mState.getCellB(i) * colorMult);
                mGiraffeImage.setRGB(x, y, new Color(g,g,g).getRGB());

                x++; //track cell location where we are drawing
                if (x >= mSettings.getXSize()) {
                    x = 0;
                    y++;
                }
            }
        }
    }

    private void drawWithThresholds() {
        int x = 0;
        int y = 0;
        int r = 0;
        int g = 0;
        int b = 0;
        for (int i = 0; i < mState.getCellCount(); i++) {
//            if (tick.getCellB(i) > mControlFrame.getPigmentThreshold())
//                { r=209; g=111; b=46;}
//            else
//                { r=255; g=255; b=255;}

            if (mState.getCellB(i) > 1.0)
                { r=209; g=3; b=3;}
            else if (mState.getCellB(i) > 0.8)
                { r=3; g=111; b=3;}
            else if (mState.getCellB(i) > 0.5)
                { r=209; g=3; b=3;}
            else
                { r=0; g=0; b=0;}

            mGiraffeImage.setRGB(x, y, new Color(r,g,b).getRGB());
            x++; //track cell location where we are drawing
            if (x >= mSettings.getXSize()) {
                x = 0;
                y++;
            }
        }
    }

    private void drawAsHisto(int histoBanding) {
        int x = 0;
        int y = 0;
        int r = 0;
        int g = 0;
        int b = 0;
        //create table based on max B value
        double bMax = 0; //find biggest B value
        double colorMult;
        for (int i = 0; i < mState.getCellCount(); i++) {
            if (mState.getCellB(i) > bMax) bMax = mState.getCellB(i);
        }
        if (bMax > 0) colorMult = 255 / bMax;
        else colorMult = 0;
//        document.getElementById('maxLabel').innerHTML = "(" + bMax.toFixed(2) + ")";

        for (int i = 0; i < mState.getCellCount(); i++) {
            int g1 = (int)Math.round(mState.getCellB(i) * colorMult);
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
            if (x >= mSettings.getXSize()) {
                x = 0;
                y++;
            }
        }
    }
}
