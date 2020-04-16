package org.ca;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.GroupLayout.Alignment.*;

public class ControlPanel1 extends JPanel {
    private JButton restartFromZeroButton = new JButton("Restart from zero");
    private JButton pauseUnpauseEndButton = new JButton("pause/unpause/end");
    private JButton redrawPictureButton = new JButton("Redraw picture");
    private JButton smoothEdgesButton = new JButton("Smooth Edges");

    public ControlPanel1() {
        this.setBorder(BorderFactory.createEmptyBorder());

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(restartFromZeroButton)
                                .addComponent(pauseUnpauseEndButton)
                                .addComponent(redrawPictureButton)
                                .addComponent(smoothEdgesButton))
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(BASELINE)
                                .addComponent(restartFromZeroButton)
                                .addComponent(pauseUnpauseEndButton)
                                .addComponent(redrawPictureButton)
                                .addComponent(smoothEdgesButton))
        );
    }

    public void addButtonActionListeners(Tick tick) {
        restartFromZeroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tick.start();
            }
        });

        pauseUnpauseEndButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tick.pauseUnPause();
            }
        });

        redrawPictureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tick.mainDraw();
            }
        });

        smoothEdgesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tick.diffuseB();
            }
        });
    }
}