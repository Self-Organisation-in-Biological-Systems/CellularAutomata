package org.ca.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;

public class Colors extends JPanel {
    private boolean drawInGiraffColors = true;
    private boolean drawScaledToMax = false;
    private int banding = 0;
    private boolean showCellStates = true;

    private JCheckBox drawInGiraffeColorsCheckBox = new JCheckBox("Draw in Giraffe colors");
    private JCheckBox drawScaledToMaxCheckBox = new JCheckBox("Draw scaled to max");

    private JCheckBox showCellStatesCheckBox = new JCheckBox("Show Cell States");
    private JLabel showCellStatesLabel = new JLabel("Cyan=On. Red=On and able to activate neighbors.");

    private JLabel bandingLabel = new JLabel("Banding:", JLabel.RIGHT);
    private JTextField bandingTextField = new JTextField(5);

    public Colors() {
        this.setBorder(BorderFactory.createEmptyBorder());

            GroupLayout layout = new GroupLayout(this);
            setLayout(layout);
            layout.setAutoCreateGaps(true);
            layout.setAutoCreateContainerGaps(true);

            layout.setHorizontalGroup(layout.createParallelGroup(LEADING)
                            .addComponent(drawInGiraffeColorsCheckBox)
                            .addComponent(drawScaledToMaxCheckBox)
//                                .addComponent(bandingLabel)
//                                .addComponent(bandingTextField)
                            .addComponent(showCellStatesCheckBox)
            );

            layout.setVerticalGroup(layout.createSequentialGroup()
                            .addComponent(drawInGiraffeColorsCheckBox)
                            .addComponent(drawScaledToMaxCheckBox)
//                                    .addComponent(bandingLabel)
//                                    .addComponent(bandingTextField)
                            .addComponent(showCellStatesCheckBox)
            );

        setValues();
    }

    private void setValues() {
        drawInGiraffeColorsCheckBox.setSelected(drawInGiraffColors);
        drawScaledToMaxCheckBox.setSelected(drawScaledToMax);
        showCellStatesCheckBox.setSelected(showCellStates);
    }

    public boolean drawInGiraffeColors() {
        return drawInGiraffeColorsCheckBox.isSelected();
    }

    public boolean drawScaledToMax() {
        return drawScaledToMaxCheckBox.isSelected();
    }

    public boolean showCellStates() {
        return showCellStatesCheckBox.isSelected();
    }
}