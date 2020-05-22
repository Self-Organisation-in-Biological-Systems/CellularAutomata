package org.ca;

import org.ca.data.ModelSettings;
import org.ca.panels.*;

import javax.swing.*;

public class ControlFrame {
    private ModelSettings mModelSettings;
    private Start startPanel;
    private Colors colorsPanel;
    private ModelSettingsPanel modelSettingsPanel;
    private FilePanel filePanel;
    private Patterns patternsPanel;

    public ControlFrame(ModelSettings settings) {
        mModelSettings = settings;

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
        modelSettingsPanel = new ModelSettingsPanel(mModelSettings);
        jTabbedPane.addTab("Cellular Automata Settings", modelSettingsPanel);

//        jPanel4.setLayout(null);
//        filePanel = new FilePanel();
//        jTabbedPane.addTab("File", filePanel);

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

//    public boolean drawInGiraffeColors() { return colorsPanel.drawInGiraffeColors(); }
//    public boolean drawScaledToMax() { return colorsPanel.drawScaledToMax(); }
//    public boolean showCellStates() { return colorsPanel.showCellStates(); }

//    public int getXSize() { return ModelSettingsPanel.getXSize(); }
//    public int getYSize() { return ModelSettingsPanel.getYSize(); }
//    public double getPigmentThreshold() { return ModelSettingsPanel.getPigmentThreshold(); }
//    public int getDawEveryNthCycle() { return ModelSettingsPanel.getDawEveryNthCycle(); }
//    public double getStartA() { return ModelSettingsPanel.getStartA(); }
//    public double getStartOnPercent() { return ModelSettingsPanel.getStartOnPercent(); }
//    public double getAReplenish() { return ModelSettingsPanel.getAReplenish(); }
//    public double getDiffusionRate() { return ModelSettingsPanel.getDiffusionRate(); }
//    public double getBDiffusionRate() { return ModelSettingsPanel.getBDiffusionRate(); }
//    public double getBDecayRate() { return ModelSettingsPanel.getBDecayRate(); }
//    public double getReactionRate() { return ModelSettingsPanel.getReactionRate(); }
//    public double getActivationRate() { return ModelSettingsPanel.getActivationRate(); }
//    public double getActivationThreshold() { return ModelSettingsPanel.getActivationThreshold(); }
//    public double getActivationDelay() { return ModelSettingsPanel.getActivationDelay(); }
//    public int getMaxLifeTime() { return ModelSettingsPanel.getMaxLifeTime(); }
//    public int getShutoffAThreshold() { return ModelSettingsPanel.getShutoffAThreshold(); }
//    public int getShutoffBThreshold() { return ModelSettingsPanel.getShutoffBThreshold(); }

    public void addButtonActionListeners(Tick tick) { startPanel.addButtonActionListeners(tick); }

//    public boolean drawGiraffePattern() { return patternsPanel.drawGiraffePattern(); }
//    public boolean drawSingletonPattern() {
//        return patternsPanel.drawSingletonPattern();
//    }
//    public boolean drawWeirdPhylloPattern() {
//        return patternsPanel.drawWeirdPhylloPattern();
//    }
//    public boolean drawPhylloPattern() {
//        return patternsPanel.drawPhylloPattern();
//    }
//    public boolean drawGridPattern() {
//        return patternsPanel.drawGridPattern();
//    }
//    public boolean drawHexPattern() {
//        return patternsPanel.drawHexPattern();
//    }
//    public boolean drawIrregularHexPattern() {
//        return patternsPanel.drawIrregularHexPattern();
//    }
//    public boolean drawConcentricCirclePattern() {
//        return patternsPanel.drawConcentricCirclePattern();
//    }
//    public boolean applyGradient() {
//        return patternsPanel.applyGradient();
//    }
}