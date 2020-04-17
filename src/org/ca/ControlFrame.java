package org.ca;

import javax.swing.*;

public class ControlFrame {
    private ControlPanel1 controlPanel1;
    private ControlPanel2 controlPanel2;
    private ControlPanel3 controlPanel3;
    private ControlPanel4 controlPanel4;

    public ControlFrame() {
        JFrame f = new JFrame("Giraffe pattern");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(new BoxLayout(f.getContentPane(), BoxLayout.Y_AXIS));

        controlPanel1 = new ControlPanel1();
        f.add(controlPanel1);
        controlPanel2 = new ControlPanel2();
        f.add(controlPanel2);
        controlPanel3 = new ControlPanel3();
        f.add(controlPanel3);
        controlPanel4 = new ControlPanel4();
        f.add(controlPanel4);

        f.add(Box.createVerticalGlue());
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    public boolean drawInGiraffeColors() { return controlPanel2.drawInGiraffeColors(); }
    public boolean drawScaledToMax() { return controlPanel2.drawScaledToMax(); }
    public boolean showCellStates() { return controlPanel2.showCellStates(); }
    public int getXSize() {
        return controlPanel3.getXSize();
    }
    public int getYSize() {
        return controlPanel3.getYSize();
    }
    public int getPigmentThreshold() {
        return controlPanel3.getPigmentThreshold();
    }
    public int getDawEveryNthCycle() {
        return controlPanel3.getDawEveryNthCycle();
    }
    public double getStartA() {
        return controlPanel3.getStartA();
    }
    public double getStartOnPercent() {
        return controlPanel3.getStartOnPercent();
    }
    public double getAReplenish() { return controlPanel3.getAReplenish(); }
    public double getDiffusionRate() {
        return controlPanel3.getDiffusionRate();
    }
    public double getBDiffusionRate() {
        return controlPanel3.getBDiffusionRate();
    }
    public double getBDecayRate() {
        return controlPanel3.getBDecayRate();
    }
    public double getReactionRate() {
        return controlPanel3.getReactionRate();
    }
    public double getActivationRate() {
        return controlPanel3.getActivationRate();
    }
    public double getActivationThreshold() { return controlPanel3.getActivationThreshold(); }
    public double getActivationDelay() {
        return controlPanel3.getActivationDelay();
    }
    public int getMaxLifeTime() {
        return controlPanel3.getMaxLifeTime();
    }
    public int getShutoffAThreshold() {
        return controlPanel3.getShutoffAThreshold();
    }
    public int getShutoffBThreshold() {
        return controlPanel3.getShutoffBThreshold();
    }

    public void addButtonActionListeners(Tick tick) { controlPanel1.addButtonActionListeners(tick); }
}