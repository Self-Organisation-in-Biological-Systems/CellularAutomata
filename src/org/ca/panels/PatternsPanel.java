package org.ca.panels;

import org.ca.data.ModelSettings;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

public class PatternsPanel extends JPanel {
    private JRadioButton giraffePatternButton;
    private JRadioButton singletonPatternButton;
    private JRadioButton gridPatternButton;
    private JRadioButton hexPatternButton;
    private JRadioButton irregularHexPatternButton;
    private JRadioButton weirdPhylloPatternButton;
    private JRadioButton phylloPatternButton;
    private JCheckBox gradientCheckBox;
    private ButtonGroup panelButtonGroup;


    public PatternsPanel(ModelSettings settings) {
        panelButtonGroup = new ButtonGroup();

        giraffePatternButton = new JRadioButton("Giraffe pattern");
        panelButtonGroup.add(giraffePatternButton);

        singletonPatternButton = new JRadioButton("singleton pattern");
        panelButtonGroup.add(singletonPatternButton);

        gridPatternButton = new JRadioButton("grid pattern");
        panelButtonGroup.add(gridPatternButton);

        hexPatternButton = new JRadioButton("hex pattern");
        panelButtonGroup.add(hexPatternButton);

        irregularHexPatternButton = new JRadioButton("irregular hex pattern");
        panelButtonGroup.add(irregularHexPatternButton);

        weirdPhylloPatternButton = new JRadioButton("weird Phyllo pattern");
        panelButtonGroup.add(weirdPhylloPatternButton);

        phylloPatternButton = new JRadioButton("Phyllo pattern");
        panelButtonGroup.add(phylloPatternButton);

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

        gradientCheckBox.setSelected(false);
    }

    public void addButtonActionListeners(ModelSettings settings) {
        giraffePatternButton.addActionListener(e -> settings.setDrawGiraffePattern(true));
        singletonPatternButton.addActionListener(e -> settings.setDrawSingletonPattern(true));
        weirdPhylloPatternButton.addActionListener(e -> settings.setDrawWeirdPhylloPattern(true));
        phylloPatternButton.addActionListener(e -> settings.setDrawPhylloPattern(true));
        gridPatternButton.addActionListener(e -> settings.setDrawGridPattern(true));
        hexPatternButton.addActionListener(e -> settings.setDrawHexPattern(true));
        irregularHexPatternButton.addActionListener(e -> settings.setDrawIrregularHexPattern(true));

        gradientCheckBox.addItemListener(e -> settings.setApplyGradient(e.getStateChange() == ItemEvent.SELECTED));
    }
}