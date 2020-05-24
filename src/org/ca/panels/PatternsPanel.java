package org.ca.panels;

import org.ca.data.ModelSettings;
import javax.swing.*;
import java.awt.*;

public class PatternsPanel extends JPanel {
    private final ButtonGroup buttonGroup;

    private JRadioButton giraffePatternButton;
    private JRadioButton singletonPatternButton;
    private JRadioButton gridPatternButton;
    private JRadioButton hexPatternButton;
    private JRadioButton irregularHexPatternButton;
    private JRadioButton weirdPhylloPatternButton;
    private JRadioButton phylloPatternButton;
    private JRadioButton concentricCirclePatternButton;
    private JCheckBox gradientCheckBox;

    public PatternsPanel(ModelSettings settings) {
        buttonGroup = new ButtonGroup();

        giraffePatternButton = new JRadioButton("Giraffe pattern");
        buttonGroup.add(giraffePatternButton);

        singletonPatternButton = new JRadioButton("singleton pattern");
        buttonGroup.add(singletonPatternButton);

        gridPatternButton = new JRadioButton("grid pattern");
        buttonGroup.add(gridPatternButton);

        hexPatternButton = new JRadioButton("hex pattern");
        buttonGroup.add(hexPatternButton);

        irregularHexPatternButton = new JRadioButton("irregular hex pattern");
        buttonGroup.add(irregularHexPatternButton);

        weirdPhylloPatternButton = new JRadioButton("weird Phyllo pattern");
        buttonGroup.add(weirdPhylloPatternButton);

        phylloPatternButton = new JRadioButton("Phyllo pattern");
        buttonGroup.add(phylloPatternButton);

        concentricCirclePatternButton = new JRadioButton("Concentric circles");
        buttonGroup.add(concentricCirclePatternButton);

        gradientCheckBox = new JCheckBox("Apply gradient");

        setBorder(BorderFactory.createEmptyBorder());

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.insets = new Insets(4, 4, 4, 4);

        add(giraffePatternButton, gbc);
        gbc.gridy++;
        add(singletonPatternButton, gbc);
        gbc.gridy++;
        add(weirdPhylloPatternButton, gbc);
        gbc.gridy++;
        add(phylloPatternButton, gbc);
        gbc.gridy++;
        add(gridPatternButton, gbc);
        gbc.gridy++;
        add(hexPatternButton, gbc);
        gbc.gridy++;
        add(irregularHexPatternButton, gbc);
        gbc.gridy++;
        add(concentricCirclePatternButton, gbc);
        gbc.gridy++;
        add(gradientCheckBox, gbc);

        setValues(settings);
    }

    private void setValues(ModelSettings settings) {
        giraffePatternButton.setSelected(settings.getDrawGiraffePattern());
        singletonPatternButton.setSelected(settings.getDrawSingletonPattern());
        weirdPhylloPatternButton.setSelected(settings.getDrawWeirdPhylloPattern());
        phylloPatternButton.setSelected(settings.getDrawPhylloPattern());
        gridPatternButton.setSelected(settings.getDrawGridPattern());
        hexPatternButton.setSelected(settings.getDrawHexPattern());
        irregularHexPatternButton.setSelected(settings.getDrawIrregularHexPattern());
        concentricCirclePatternButton.setSelected(settings.getDrawConcentricCirclePattern());

        gradientCheckBox.setSelected(false);
    }

    public void addButtonActionListeners(ModelSettings settings) {
        giraffePatternButton.addActionListener(e -> settings.drawGiraffePattern());
        singletonPatternButton.addActionListener(e -> settings.drawSingletonPattern());
        weirdPhylloPatternButton.addActionListener(e -> settings.drawWeirdPhylloPattern());
        phylloPatternButton.addActionListener(e -> settings.drawPhylloPattern());
        gridPatternButton.addActionListener(e -> settings.drawGridPattern());
        hexPatternButton.addActionListener(e -> settings.drawHexPattern());
        irregularHexPatternButton.addActionListener(e -> settings.drawIrregularHexPattern());
        concentricCirclePatternButton.addActionListener(e -> settings.drawConcentricCirclePattern());
    }
}