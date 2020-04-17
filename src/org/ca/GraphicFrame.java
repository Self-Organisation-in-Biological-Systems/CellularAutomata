package org.ca;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GraphicFrame extends JPanel {
    JFrame frame;
    private BufferedImage mGiraffeImage;

    public void createGraphicFrame(int xSize, int ySize) {
        mGiraffeImage = new BufferedImage(xSize, ySize, BufferedImage.TYPE_INT_ARGB);

        frame = new JFrame("Welcome to Giraffe World!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(xSize, ySize);

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

    public void drawGiraffeImage(int cellCount, double[] cellB, int xSize) {
        int x = 0;
        int y = 0;
        //rgb for a giraffe brownish color = 209 111 46
        for (int i = 0; i < cellCount; i++) {
            int r = 255 - (int)(Math.floor(cellB[i] * 46));
            if (r < 209) r = 209;
            int g = 255 - (int)(Math.floor(cellB[i] * 145));
            if (g < 111) g = 111;
            int b = 255 - (int)(Math.floor(cellB[i] * 209));
            if (b < 46) b = 46;

            mGiraffeImage.setRGB(x, y, new Color(r,g,b).getRGB());
            x++;
            if (x >= xSize) {
                x = 0;
                y++;
            }
        }

        frame.validate();
        frame.repaint();
    }




//    //--------------------------------- orig code -----------------------------------
//
//    //main drawing routine, will branch to the selected rawing mode based on checkboxes and stuff
//    private void mainDraw() {
//        if (useGiraffeColors) {
//            drawAsGiraffe();
//        } else if (drawScaled) {
//            drawAsScaled();
//        } else {
//            var canvas = document.getElementById('skin');
//            var ctx = canvas.getContext('2d');
//            int x = 0;
//            int y = 0;
//
//            if (showCellStates) {
//                for (int i = 0; i < cellCount; i++) {
//                    if (cellState[i]) {
//                        if (!tryToActivateNeighbors[i]) ctx.fillStyle = "rgb(0,255,255)";
//                        else ctx.fillStyle = "rgb(255,0,0)";
//                    } else ctx.fillStyle = "rgb(0,0,0)";
//                    ctx.fillRect(x, y, 1, 1);
//                    x++; //track cell location where we are drawing
//                    if (x >= xSize) {
//                        x = 0;
//                        y++;
//                    }
//                }
//            } else {
//                ///rgb for a giraffe brownish hcolor = 209 111 46
//                if (tickCount % 2 == 0) c = 0;
//                else c = 254;
//                int mult = 255 / startA;//scale blue/yellow values by starting value so they don't overdrive
//                for (int i = 0; i < cellCount; i++) {
//                    int a = (int)Math.floor(cellA[i] * mult); //get colors to draw A and B values graphically
//                    if (a > 255) a = 255;
//                    //var b=0;
//                    int b = (int)Math.floor(cellB[i] * mult);
//                    //if (b>255) b=255;
//                    //if (tryToActivateNeighbors[i]) ctx.fillStyle = "rgb("+a+",255,"+b+")";
//                    //else if (cellState[i])   ctx.fillStyle = "rgb("+a+","+(c/2)+","+b+")";
//                    //else                     ctx.fillStyle = "rgb("+a+",0,"+b+")";
//                    ctx.fillStyle = "rgb(" + a + "," + a + "," + b + ")";
//                    ctx.fillRect(x, y, 1, 1);
//                    x++; //track cell location where we are drawing
//                    if (x >= xSize) {
//                        x = 0;
//                        y++;
//                    }
//                }
//            }
//        }
//    }
//
//    private void drawAsGiraffe() {
//        if (pigmentThreshold > 0) {
//            drawWithThresholds();
//            return;
//        }
//        var canvas = document.getElementById('skin');
//        var ctx = canvas.getContext('2d');
//        int x = 0;
//        int y = 0;
//        ///rgb for a giraffe brownish hcolor = 209 111 46
//        for (int i = 0; i < cellCount; i++) {
//            int r = 255 - (int)(Math.floor(cellB[i] * 46));
//            if (r < 209) r = 209;
//            int g = 255 - (int)(Math.floor(cellB[i] * 145));
//            if (g < 111) g = 111;
//            int b = 255 - (int)(Math.floor(cellB[i] * 209));
//            if (b < 46) b = 46;
//            ctx.fillStyle = "rgb(" + r + "," + g + "," + b + ")";
//            ctx.fillRect(x, y, 1, 1);
//            x++; //track cell location where we are drawing
//            if (x >= xSize) {
//                x = 0;
//                y++;
//            }
//        }
//    }
//
//    private void drawAsScaled() {
//        histoBanding = document.getElementById("histoBanding").value - 0;
//        if (histoBanding > 0) {
//            drawAsHisto();
//        } else {
//            var canvas = document.getElementById('skin');
//            var ctx = canvas.getContext('2d');
//            int x = 0;
//            int y = 0;
//            //create table based on max B value
//            double bMax = 0; //find biggest B value
//            double colorMult;
//            for (int i = 0; i < cellCount; i++) {
//                if (cellB[i] > bMax) bMax = cellB[i];
//            }
//            if (bMax > 0) colorMult = 255 / bMax;
//            else colorMult = 0;
//            document.getElementById('maxLabel').innerHTML = "(" + bMax.toFixed(2) + ")";
//
//            for (int i = 0; i < cellCount; i++) {
//                int g = Math.round(cellB[i] * colorMult).toString();
//                ctx.fillStyle = "rgb(" + g + "," + g + "," + g + ")";
//                ctx.fillRect(x, y, 1, 1);
//                x++; //track cell location where we are drawing
//                if (x >= xSize) {
//                    x = 0;
//                    y++;
//                }
//            }
//        }
//    }
//
//    private void drawWithThresholds() {
//        int x = 0;
//        int y = 0;
//        for (int i = 0; i < cellCount; i++) {
//            if (cellB[i] > pigmentThreshold)
//                ctx.fillStyle = "rgb(209,111,46)";
//            else
//                ctx.fillStyle = "rgb(255,255,255)";
//            ctx.fillRect(x, y, 1, 1);
//            x++; //track cell location where we are drawing
//            if (x >= xSize) {
//                x = 0;
//                y++;
//            }
//        }
//    }
//
//    private void drawAsHisto() {
//        var canvas = document.getElementById('skin');
//        var ctx = canvas.getContext('2d');
//        int x = 0;
//        int y = 0;
//        //create table based on max B value
//        double bMax = 0; //find biggest B value
//        double colorMult;
//        for (int i = 0; i < cellCount; i++) {
//            if (cellB[i] > bMax) bMax = cellB[i];
//        }
//        if (bMax > 0) colorMult = 255 / bMax;
//        else colorMult = 0;
//        document.getElementById('maxLabel').innerHTML = "(" + bMax.toFixed(2) + ")";
//
//        for (int i = 0; i < cellCount; i++) {
//            int g = Math.round(cellB[i] * colorMult);
//            if (g == 0) {
//                ctx.fillStyle = "rgb(0,255,0)";
//            } else {
//                if (g % histoBanding == 0) {
//                    ctx.fillStyle = "rgb(255,255,255)";
//                } else {
//                    ctx.fillStyle = "rgb(0,0,0)";
//                }
//            }
//            ctx.fillRect(x, y, 1, 1);
//            x++; //track cell location where we are drawing
//            if (x >= xSize) {
//                x = 0;
//                y++;
//            }
//        }
//    }
}
