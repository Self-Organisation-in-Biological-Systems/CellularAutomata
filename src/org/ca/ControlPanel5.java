package org.ca;

import javax.swing.*;

import java.awt.*;

import static javax.swing.GroupLayout.Alignment.LEADING;

public class ControlPanel5 extends JPanel {
    private final ButtonGroup buttonGroup;

    private JRadioButton giraffePatternButton;
    private JRadioButton singletonPatternButton;
    private JRadioButton weirdPhylloPatternButton;
    private JRadioButton phylloPatternButton;

    public ControlPanel5() {
        buttonGroup = new ButtonGroup();

        giraffePatternButton = new JRadioButton("Giraffe pattern");
        buttonGroup.add(giraffePatternButton);

        singletonPatternButton = new JRadioButton("singleton patternButton");
        buttonGroup.add(singletonPatternButton);

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
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.weightx = 1;
        gbc.insets = new Insets(4, 4, 4, 4);

        add(giraffePatternButton);
        gbc.gridy++;
        add(singletonPatternButton);
        gbc.gridy++;
        add(weirdPhylloPatternButton);
        gbc.gridy++;
        add(phylloPatternButton);
        setValues();
    }

    private void setValues() {
        giraffePatternButton.setSelected(true);
        singletonPatternButton.setSelected(false);
        weirdPhylloPatternButton.setSelected(false);
        phylloPatternButton.setSelected(false);
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
}