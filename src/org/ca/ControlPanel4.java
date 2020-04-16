package org.ca;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;

public class ControlPanel4 extends JPanel {
    private JButton loadButton = new JButton("Load");
    private JButton saveButton = new JButton("Save");
    private JButton resetButton = new JButton("Reset");
    private JFileChooser fileChooser = new JFileChooser();

    public ControlPanel4() {
        setBorder(BorderFactory.createEmptyBorder());
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(loadButton)
                        .addComponent(saveButton)
                        .addComponent(resetButton))
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(loadButton)
                        .addComponent(saveButton)
                        .addComponent(resetButton))
        );

        addButtonActionListeners();
    }

    private void addButtonActionListeners() {
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
    }
}