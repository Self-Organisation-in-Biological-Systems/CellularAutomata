package org.ca;

import javax.swing.*;

public class ControlFrame {
    private ControlPanel1 controlPanel1;
    private ControlPanel2 controlPanel2;
    private ControlPanel3 controlPanel3;
    private ControlPanel4 controlPanel4;
    private ControlPanel5 controlPanel5;

    public ControlFrame() {
        JFrame f = new JFrame("Giraffe pattern");
        JTabbedPane jTabbedPane = new javax.swing.JTabbedPane();
        JPanel jPanel1 = new javax.swing.JPanel();
        JPanel jPanel2 = new javax.swing.JPanel();
        JPanel jPanel3 = new javax.swing.JPanel();
        JPanel jPanel4 = new javax.swing.JPanel();
        JPanel jPanel5 = new javax.swing.JPanel();

        f.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(null);
        controlPanel1 = new ControlPanel1();
        jTabbedPane.addTab("Run", controlPanel1);

        jPanel2.setLayout(null);
        controlPanel2 = new ControlPanel2();
        jTabbedPane.addTab("Colors", controlPanel2);

        jPanel5.setLayout(null);
        controlPanel5 = new ControlPanel5();
        jTabbedPane.addTab("Patterns", controlPanel5);

        jPanel3.setLayout(null);
        controlPanel3 = new ControlPanel3();
        jTabbedPane.addTab("Cellular Automata Settings", controlPanel3);

        jPanel4.setLayout(null);
        controlPanel4 = new ControlPanel4();
        jTabbedPane.addTab("File", controlPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(f.getContentPane());
        f.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTabbedPane)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTabbedPane)
        );

        f.setLocation(520,100);
        f.pack();
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

    public boolean drawGiraffePattern() { return controlPanel5.drawGiraffePattern(); }
    public boolean drawSingletonPattern() {
        return controlPanel5.drawSingletonPattern();
    }
    public boolean drawWeirdPhylloPattern() {
        return controlPanel5.drawWeirdPhylloPattern();
    }
    public boolean drawPhylloPattern() {
        return controlPanel5.drawPhylloPattern();
    }
}