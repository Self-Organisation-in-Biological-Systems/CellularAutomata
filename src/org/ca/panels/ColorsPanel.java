package org.ca.panels;

import org.ca.data.ModelSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;

import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;

public class ColorsPanel extends JPanel {
    private JCheckBox drawInGiraffeColorsCheckBox = new JCheckBox("Draw in Giraffe colors");
    private JCheckBox drawScaledToMaxCheckBox = new JCheckBox("Draw scaled to max");

    private JCheckBox showCellStatesCheckBox = new JCheckBox("Show Cell States");
    private JLabel showCellStatesLabel = new JLabel("Cyan=On. Red=On and able to activate neighbors.");

    private JLabel bandingLabel = new JLabel("Banding:", JLabel.RIGHT);
    private JTextField bandingTextField = new JTextField(5);

    public ColorsPanel(ModelSettings settings) {
        setBorder(BorderFactory.createEmptyBorder());

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

        setValues(settings);
        addButtonActionListeners(settings);
    }

    private void setValues(ModelSettings settings) {
        drawInGiraffeColorsCheckBox.setSelected(settings.getDrawInGiraffeColors());
        drawScaledToMaxCheckBox.setSelected(settings.getDrawScaledToMax());
        showCellStatesCheckBox.setSelected(settings.getShowCellStates());
    }

    public void addButtonActionListeners(ModelSettings settings) {
        drawInGiraffeColorsCheckBox.addItemListener(e -> settings.drawInGiraffeColors(e.getStateChange() == ItemEvent.SELECTED));
        drawScaledToMaxCheckBox.addItemListener(e -> settings.drawScaledToMax(e.getStateChange() == ItemEvent.SELECTED));
        showCellStatesCheckBox.addItemListener(e -> settings.showCellStates(e.getStateChange() == ItemEvent.SELECTED));

    }
}