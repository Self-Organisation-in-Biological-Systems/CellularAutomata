package org.ca;

import javax.swing.*;

import java.awt.*;

import static javax.swing.GroupLayout.Alignment.LEADING;

public class ControlPanel5 extends JPanel {
    private final ButtonGroup buttonGroup;

    private JRadioButton giraffePatternButton;
    private JRadioButton singletonPatternButton;
    private JRadioButton gridPatternButton;
    private JRadioButton hexPatternButton;
    private JRadioButton weirdPhylloPatternButton;
    private JRadioButton phylloPatternButton;

    public ControlPanel5() {
        buttonGroup = new ButtonGroup();

        giraffePatternButton = new JRadioButton("Giraffe pattern");
        buttonGroup.add(giraffePatternButton);

        singletonPatternButton = new JRadioButton("singleton pattern");
        buttonGroup.add(singletonPatternButton);

        gridPatternButton = new JRadioButton("grid pattern");
        buttonGroup.add(gridPatternButton);

        hexPatternButton = new JRadioButton("hex pattern");
        buttonGroup.add(hexPatternButton);

        weirdPhylloPatternButton = new JRadioButton("weird Phyllo pattern");
        buttonGroup.add(weirdPhylloPatternButton);

        phylloPatternButton = new JRadioButton("Phyllo pattern");
        buttonGroup.add(phylloPatternButton);

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

        setValues();
    }

    private void setValues() {
        giraffePatternButton.setSelected(true);
        singletonPatternButton.setSelected(false);
        weirdPhylloPatternButton.setSelected(false);
        phylloPatternButton.setSelected(false);
        gridPatternButton.setSelected(false);
        hexPatternButton.setSelected(false);

    }

    public boolean drawGiraffePattern() {
        return giraffePatternButton.isSelected();
    }
    public boolean drawSingletonPattern() {
        return singletonPatternButton.isSelected();
    }
    public boolean drawWeirdPhylloPattern() {
        return weirdPhylloPatternButton.isSelected();
    }
    public boolean drawPhylloPattern() {
        return phylloPatternButton.isSelected();
    }
    public boolean drawGridPattern() { return gridPatternButton.isSelected(); }
    public boolean drawHexPattern() { return hexPatternButton.isSelected(); }

}