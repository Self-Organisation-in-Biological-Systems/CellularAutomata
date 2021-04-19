package org.ca;

import org.ca.data.ModelSettings;
import org.ca.panels.*;

import javax.swing.*;

public class ControlFrame {
    private ModelSettings mSettings;
    private StartPanel startPanel;
    private ColorsPanel colorsPanel;
    private ModelSettingsPanel modelSettingsPanel;
    private PatternsPanel patternsPanel;

    public ControlFrame(ModelSettings settings) {
        mSettings = settings;

        JFrame f = new JFrame("Giraffe pattern");
        JTabbedPane jTabbedPane = new javax.swing.JTabbedPane();
        JPanel jPanel1 = new javax.swing.JPanel();
        JPanel jPanel2 = new javax.swing.JPanel();
        JPanel jPanel3 = new javax.swing.JPanel();
        JPanel jPanel5 = new javax.swing.JPanel();

        f.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(null);
        startPanel = new StartPanel();
        jTabbedPane.addTab("Run", startPanel);

        jPanel2.setLayout(null);
        colorsPanel = new ColorsPanel(mSettings);
        jTabbedPane.addTab("Colors", colorsPanel);

        jPanel5.setLayout(null);
        patternsPanel = new PatternsPanel(mSettings);
        jTabbedPane.addTab("Patterns", patternsPanel);

        jPanel3.setLayout(null);
        modelSettingsPanel = new ModelSettingsPanel(mSettings);
        jTabbedPane.addTab("Cellular Automata Settings", modelSettingsPanel);

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

    public void addButtonActionListeners(Tick tick) {
        startPanel.addButtonActionListeners(tick);
        patternsPanel.addButtonActionListeners(mSettings);
    }
}