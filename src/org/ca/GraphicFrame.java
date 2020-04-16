package org.ca;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GraphicFrame extends JPanel {
    JFrame frame;
    private BufferedImage mGiraffeImage = new BufferedImage(400, 400, BufferedImage.TYPE_INT_ARGB);


    public void createGraphicFrame(int xSize, int ySize) {
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
}
