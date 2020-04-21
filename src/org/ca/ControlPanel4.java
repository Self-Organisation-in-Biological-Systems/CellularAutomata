package org.ca;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.filechooser.FileFilter;

import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;

public class ControlPanel4 extends JPanel {
    private JButton loadButton = new JButton("Load");
    private JButton saveButton = new JButton("Save");
    private JButton resetButton = new JButton("Reset");
    private JFileChooser fileChooser;

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

        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new ExtensionFileFilter("json", new String[] { "json" }));

        addButtonActionListeners();
    }

    private void addButtonActionListeners() {
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fileChooser.showOpenDialog((Component)e.getSource());
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        String fileName = file.toString();
                        System.out.println(fileName);
                    } catch (Exception ex) {
                        System.out.println("problem accessing file"+file.getAbsolutePath());
                    }
                }
                else {
                    System.out.println("File access cancelled by user.");
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fileChooser.showSaveDialog((Component)e.getSource());
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        String fileName = file.toString();
                        System.out.println(fileName);
                    } catch (Exception ex) {
                        System.out.println("problem accessing file"+file.getAbsolutePath());
                    }
                }
                else {
                    System.out.println("File access cancelled by user.");
                }
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
    }

    class ExtensionFileFilter extends FileFilter {
        String description;

        String extensions[];

        public ExtensionFileFilter(String description, String extension) {
            this(description, new String[] { extension });
        }

        public ExtensionFileFilter(String description, String extensions[]) {
            if (description == null) {
                this.description = extensions[0];
            } else {
                this.description = description;
            }
            this.extensions = (String[]) extensions.clone();
            toLower(this.extensions);
        }

        private void toLower(String array[]) {
            for (int i = 0, n = array.length; i < n; i++) {
                array[i] = array[i].toLowerCase();
            }
        }

        public String getDescription() {
            return description;
        }

        public boolean accept(File file) {
            if (file.isDirectory()) {
                return true;
            } else {
                String path = file.getAbsolutePath().toLowerCase();
                for (int i = 0, n = extensions.length; i < n; i++) {
                    String extension = extensions[i];
                    if ((path.endsWith(extension) && (path.charAt(path.length() - extension.length() - 1)) == '.')) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
}