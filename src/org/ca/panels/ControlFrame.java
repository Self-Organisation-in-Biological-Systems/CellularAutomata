package org.ca.panels;

import org.ca.GraphicFrame;
import org.ca.Tick;

import javax.swing.*;

public class ControlFrame {
    private Start startPanel;
    private Colors colorsPanel;
    private CASettings CASettingsPanel;
    private FilePanel filePanel;
    private Patterns patternsPanel;

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
        startPanel = new Start();
        jTabbedPane.addTab("Run", startPanel);

        jPanel2.setLayout(null);
        colorsPanel = new Colors();
        jTabbedPane.addTab("Colors", colorsPanel);

        jPanel5.setLayout(null);
        patternsPanel = new Patterns();
        jTabbedPane.addTab("Patterns", patternsPanel);

        jPanel3.setLayout(null);
        CASettingsPanel = new CASettings();
        jTabbedPane.addTab("Cellular Automata Settings", CASettingsPanel);

        jPanel4.setLayout(null);
        filePanel = new FilePanel();
        jTabbedPane.addTab("File", filePanel);

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

    public boolean drawInGiraffeColors() { return colorsPanel.drawInGiraffeColors(); }
    public boolean drawScaledToMax() { return colorsPanel.drawScaledToMax(); }
    public boolean showCellStates() { return colorsPanel.showCellStates(); }
    public int getXSize() { return CASettingsPanel.getXSize(); }
    public int getYSize() { return CASettingsPanel.getYSize(); }
    public double getPigmentThreshold() { return CASettingsPanel.getPigmentThreshold(); }
    public int getDawEveryNthCycle() { return CASettingsPanel.getDawEveryNthCycle(); }
    public double getStartA() { return CASettingsPanel.getStartA(); }
    public double getStartOnPercent() { return CASettingsPanel.getStartOnPercent(); }
    public double getAReplenish() { return CASettingsPanel.getAReplenish(); }
    public double getDiffusionRate() { return CASettingsPanel.getDiffusionRate(); }
    public double getBDiffusionRate() { return CASettingsPanel.getBDiffusionRate(); }
    public double getBDecayRate() { return CASettingsPanel.getBDecayRate(); }
    public double getReactionRate() { return CASettingsPanel.getReactionRate(); }
    public double getActivationRate() { return CASettingsPanel.getActivationRate(); }
    public double getActivationThreshold() { return CASettingsPanel.getActivationThreshold(); }
    public double getActivationDelay() { return CASettingsPanel.getActivationDelay(); }
    public int getMaxLifeTime() { return CASettingsPanel.getMaxLifeTime(); }
    public int getShutoffAThreshold() { return CASettingsPanel.getShutoffAThreshold(); }
    public int getShutoffBThreshold() { return CASettingsPanel.getShutoffBThreshold(); }

    public void addButtonActionListeners(Tick tick) { startPanel.addButtonActionListeners(tick); }

    public boolean drawGiraffePattern() { return patternsPanel.drawGiraffePattern(); }
    public boolean drawSingletonPattern() {
        return patternsPanel.drawSingletonPattern();
    }
    public boolean drawWeirdPhylloPattern() {
        return patternsPanel.drawWeirdPhylloPattern();
    }
    public boolean drawPhylloPattern() {
        return patternsPanel.drawPhylloPattern();
    }
    public boolean drawGridPattern() {
        return patternsPanel.drawGridPattern();
    }
    public boolean drawHexPattern() {
        return patternsPanel.drawHexPattern();
    }
    public boolean drawIrregularHexPattern() {
        return patternsPanel.drawIrregularHexPattern();
    }
    public boolean drawConcentricCirclePattern() {
        return patternsPanel.drawConcentricCirclePattern();
    }
    public boolean applyGradient() {
        return patternsPanel.applyGradient();
    }
}