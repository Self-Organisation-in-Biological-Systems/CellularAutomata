package org.ca.panels;

import org.ca.data.ModelSettings;
import org.ca.data.ModelSettingsIO;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.util.Objects;

public class ModelSettingsPanel extends JPanel {
    private JLabel pigmentThresholdLabel1 = new JLabel("pigmentThreshold:", JLabel.RIGHT);
    private JTextField pigmentThresholdTextField = new JTextField(5);
    private JLabel pigmentThresholdLabel2 = new JLabel("(0=ignore thresholds, typical values range from 0.1 to 2.0)");

    private JLabel drawEveryNthCycleLabel1 = new JLabel("draw every nth cycle:", JLabel.RIGHT);
    private JTextField drawEveryNthCycleTextField = new JTextField(5);
    private JLabel drawEveryNthCycleLabel2 = new JLabel("Runs faster if you don't draw every cycle.");

    private JLabel xSizeLabel1 = new JLabel("xSize:", JLabel.RIGHT);
    private JTextField xSizeTextField = new JTextField(5);
    private JLabel xSizeLabel2 = new JLabel("Width of simulation in cells");

    private JLabel ySizeLabel1 = new JLabel("ySize:", JLabel.RIGHT);
    private JTextField ySizeTextField = new JTextField(5);
    private JLabel ySizeLabel2 = new JLabel("Height of simulation in cells");

    private JLabel startALabel1 = new JLabel("startA:", JLabel.RIGHT);
    private JTextField startATextField = new JTextField(5);
    private JLabel startALabel2 = new JLabel("Cells start out with this much of molecule A");

    private JLabel startOnPercentLabel1 = new JLabel("startOnPercent:", JLabel.RIGHT);
    private JTextField startOnPercentTextField = new JTextField(5);
    private JLabel startOnPercentLabel2 = new JLabel("Fraction of cells that start in the On state");

    private JLabel AReplenishLabel1 = new JLabel("A Replenish:", JLabel.RIGHT);
    private JTextField AReplenishTextField = new JTextField(5);
    private JLabel AReplenishLabel2 = new JLabel("Cells replenish this much of molecule A each tick");

    private JLabel DiffusionRateLabel1 = new JLabel("DiffusionRate:", JLabel.RIGHT);
    private JTextField DiffusionRateTextField = new JTextField(5);
    private JLabel DiffusionRateLabel2 = new JLabel("How quickly A diffuses through tissues");

    private JLabel BDiffusionRateLabel1 = new JLabel("B DiffusionRate:", JLabel.RIGHT);
    private JTextField BDiffusionRateTextField = new JTextField(5);
    private JLabel BDiffusionRateLabel2 = new JLabel("How quickly B diffuses through tissues. If set to 0, simulation will run more quickly, too.");

    private JLabel BDecayRateLabel1 = new JLabel("B Decay Rate:", JLabel.RIGHT);
    private JTextField BDecayRateTextField = new JTextField(5);
    private JLabel BDecayRateLabel2 = new JLabel("(0.0 to 1.0) This proportion of molecule B decays each tick into molecule C.");

    private JLabel reactionRateLabel1 = new JLabel("reactionRate:", JLabel.RIGHT);
    private JTextField reactionRateTextField = new JTextField(5);
    private JLabel reactionRateLabel2 = new JLabel("How quickly a cell can convert A into B in one cycle. Values over 2.0 may cause bugs or unrealistic results.");

    private JLabel activationRateLabel1 = new JLabel("activationRate:", JLabel.RIGHT);
    private JTextField activationRateTextField = new JTextField(5);
    private JLabel activationRateLabel2 = new JLabel("How often a cell is successful at activating a neighbor cell.");

    private JLabel activationThresholdLabel1 = new JLabel("activationThreshold:", JLabel.RIGHT);
    private JTextField activationThresholdTextField = new JTextField(5);
    private JLabel activationThresholdLabel2 = new JLabel("A cell must have an A value of this much or higher to be activated.");

    private JLabel activationDelayLabel1 = new JLabel("activationDelay:", JLabel.RIGHT);
    private JTextField activationDelayTextField = new JTextField(5);
    private JLabel activationDelayLabel2 = new JLabel("A cell cannot activate its neighbors until this many cycles after it was first switched on.");

    private JLabel maxLifeTimeLabel1 = new JLabel("maxLifeTime:", JLabel.RIGHT);
    private JTextField maxLifeTimeTextField = new JTextField(5);
    private JLabel maxLifeTimeLabel2 = new JLabel("A cell can only stay in the On state for this long, and then will turn itself off.");

    private JLabel shutoffAThresholdLabel1 = new JLabel("shutoffAThreshold:", JLabel.RIGHT);
    private JTextField shutoffAThresholdTextField = new JTextField(5);
    private JLabel shutoffAThresholdLabel2 = new JLabel("A cell will switch off if its A value falls below this number.");

    private JLabel shutoffBThresholdLabel1 = new JLabel("shutoffBThreshold:", JLabel.RIGHT);
    private JTextField shutoffBThresholdTextField = new JTextField(5);
    private JLabel shutoffBThresholdLabel2 = new JLabel("A cell will switch off if its B value exceeds this number.");

    private JButton loadButton = new JButton("Load");
    private JButton saveButton = new JButton("Save");
    private JButton resetButton = new JButton("Reset");
    private JFileChooser fileChooser;

    ModelSettings mSettings;
    ModelSettingsIO mModelSettingsIO;

    public ModelSettingsPanel(ModelSettings settings) {
        mSettings = settings;
        mModelSettingsIO = new ModelSettingsIO(settings);

        setBorder(BorderFactory.createEmptyBorder());

        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        this.setLayout(layout);
        setLayout(layout);

        addRow(c, 0, pigmentThresholdLabel1, pigmentThresholdTextField, pigmentThresholdLabel2);
        addRow(c, 1, drawEveryNthCycleLabel1, drawEveryNthCycleTextField, drawEveryNthCycleLabel2);
        addRow(c, 2, xSizeLabel1, xSizeTextField, xSizeLabel2);
        addRow(c, 3, ySizeLabel1, ySizeTextField, ySizeLabel2);

        addRow(c, 4, startALabel1, startATextField, startALabel2);
        addRow(c, 5, startOnPercentLabel1, startOnPercentTextField, startOnPercentLabel2);
        addRow(c, 6, AReplenishLabel1, AReplenishTextField, AReplenishLabel2);
        addRow(c, 7, DiffusionRateLabel1, DiffusionRateTextField, DiffusionRateLabel2);
        addRow(c, 8, BDiffusionRateLabel1, BDiffusionRateTextField, BDiffusionRateLabel2);
        addRow(c, 9, BDecayRateLabel1, BDecayRateTextField, BDecayRateLabel2);
        addRow(c, 10, reactionRateLabel1, reactionRateTextField, reactionRateLabel2);
        addRow(c, 11, activationRateLabel1, activationRateTextField, activationRateLabel2);
        addRow(c, 12, activationThresholdLabel1, activationThresholdTextField, activationThresholdLabel2);
        addRow(c, 13, activationDelayLabel1, activationDelayTextField, activationDelayLabel2);
        addRow(c, 14, maxLifeTimeLabel1, maxLifeTimeTextField, maxLifeTimeLabel2);
        addRow(c, 15, shutoffAThresholdLabel1, shutoffAThresholdTextField, shutoffAThresholdLabel2);
        addRow(c, 16, shutoffBThresholdLabel1, shutoffBThresholdTextField, shutoffBThresholdLabel2);

        setValues();

        addTextActionListeners();

        addButton(c, 18, 0, loadButton);
        addButton(c, 18, 1, saveButton);
        addButton(c, 18, 2, resetButton);

        fileChooser = new JFileChooser("/Users/paul/Desktop/IdeaProjects/CellularAutomata/settings");
        fileChooser.setDialogTitle("Settings");
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Json",  "json");
        fileChooser.addChoosableFileFilter(filter);

        addButtonActionListeners();
    }

    private void addRow(GridBagConstraints c, int row, JLabel label1, JTextField text, JLabel label2) {
        c.gridx = 0;
        c.gridy = row;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(label1, c);
        c.gridx = 1;
        add(text, c);
        c.gridx = 2;
        add(label2, c);
    }

    private void setValues() {
        pigmentThresholdTextField.setText(Double.toString(mSettings.getPigmentThreshold()));
        drawEveryNthCycleTextField.setText(Integer.toString(mSettings.getDrawEvery()));
        xSizeTextField.setText(Integer.toString(mSettings.getXSize()));
        ySizeTextField.setText(Integer.toString(mSettings.getYSize()));
        startATextField.setText(Double.toString(mSettings.getStartA()));
        startOnPercentTextField.setText(Double.toString(mSettings.getStartOnPercent()));
        AReplenishTextField.setText(Double.toString(mSettings.getAReplenish()));
        DiffusionRateTextField.setText(Double.toString(mSettings.getDiffusionRate()));
        BDiffusionRateTextField.setText(Double.toString(mSettings.getBDiffusionRate()));
        BDecayRateTextField.setText(Double.toString(mSettings.getBDecayRate()));
        reactionRateTextField.setText(Double.toString(mSettings.getReactionRate()));
        activationRateTextField.setText(Double.toString(mSettings.getActivationRate()));
        activationThresholdTextField.setText(Double.toString(mSettings.getActivationThreshold()));
        activationDelayTextField.setText(Double.toString(mSettings.getActivationDelay()));
        maxLifeTimeTextField.setText(Integer.toString(mSettings.getMaxLifeTime()));
        shutoffAThresholdTextField.setText(Integer.toString(mSettings.getShutoffAThreshold()));
        shutoffBThresholdTextField.setText(Integer.toString(mSettings.getShutoffBThreshold()));
    }

    private void addTextActionListeners() {
        addChangeListener(pigmentThresholdTextField, e -> mSettings.setPigmentThreshold(Double.parseDouble(pigmentThresholdTextField.getText())));
        addChangeListener(drawEveryNthCycleTextField, e -> mSettings.setDrawEvery(Integer.parseInt(drawEveryNthCycleTextField.getText())));
        addChangeListener(xSizeTextField, e -> mSettings.setXSize(Integer.parseInt(xSizeTextField.getText())));
        addChangeListener(ySizeTextField, e -> mSettings.setYSize(Integer.parseInt(ySizeTextField.getText())));
        addChangeListener(startATextField, e -> mSettings.setStartA(Double.parseDouble(startATextField.getText())));
        addChangeListener(startOnPercentTextField, e -> mSettings.setStartOnPercent(Double.parseDouble(startOnPercentTextField.getText())));
        addChangeListener(AReplenishTextField, e -> mSettings.setAReplenish(Double.parseDouble(AReplenishTextField.getText())));
        addChangeListener(DiffusionRateTextField, e -> mSettings.setDiffusionRate(Double.parseDouble(DiffusionRateTextField.getText())));
        addChangeListener(BDiffusionRateTextField, e -> mSettings.setBDiffusionRate(Double.parseDouble(BDiffusionRateTextField.getText())));
        addChangeListener(BDecayRateTextField, e -> mSettings.setBDecayRate(Double.parseDouble(BDecayRateTextField.getText())));
        addChangeListener(reactionRateTextField, e -> mSettings.setReactionRate(Double.parseDouble(reactionRateTextField.getText())));
        addChangeListener(activationRateTextField, e -> mSettings.setActivationRate(Double.parseDouble(activationRateTextField.getText())));
        addChangeListener(activationThresholdTextField, e -> mSettings.setActivationThreshold(Double.parseDouble(activationThresholdTextField.getText())));
        addChangeListener(activationDelayTextField, e -> mSettings.setActivationDelay(Double.parseDouble(activationDelayTextField.getText())));
        addChangeListener(maxLifeTimeTextField, e -> mSettings.setMaxLifeTime(Integer.parseInt(maxLifeTimeTextField.getText())));
        addChangeListener(shutoffAThresholdTextField, e -> mSettings.setShutoffAThreshold(Integer.parseInt(shutoffAThresholdTextField.getText())));
        addChangeListener(shutoffBThresholdTextField, e -> mSettings.setShutoffBThreshold(Integer.parseInt(shutoffBThresholdTextField.getText())));
    }

    public static void addChangeListener(JTextComponent text, ChangeListener changeListener) {
        Objects.requireNonNull(text);
        Objects.requireNonNull(changeListener);
        DocumentListener dl = new DocumentListener() {
            private int lastChange = 0, lastNotifiedChange = 0;

            @Override
            public void insertUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                lastChange++;
                SwingUtilities.invokeLater(() -> {
                    if (lastNotifiedChange != lastChange) {
                        lastNotifiedChange = lastChange;
                        changeListener.stateChanged(new ChangeEvent(text));
                    }
                });
            }
        };
        text.addPropertyChangeListener("document", (PropertyChangeEvent e) -> {
            Document d1 = (Document)e.getOldValue();
            Document d2 = (Document)e.getNewValue();
            if (d1 != null) d1.removeDocumentListener(dl);
            if (d2 != null) d2.addDocumentListener(dl);
            dl.changedUpdate(null);
        });
        Document d = text.getDocument();
        if (d != null) d.addDocumentListener(dl);
    }

    private void addButton(GridBagConstraints c, int row, int col, JButton button) {
        c.gridx = col;
        c.gridy = row;
        c.fill = GridBagConstraints.NONE;
        add(button, c);
    }

    private void addButtonActionListeners() {
        loadButton.addActionListener(e -> {
            int returnVal = fileChooser.showOpenDialog((Component)e.getSource());
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    mModelSettingsIO.read(file);
                    setValues();
                } catch (Exception ex) {
                    System.out.println("problem accessing file"+file.getAbsolutePath());
                }
            }
        });

        saveButton.addActionListener(e -> {
            int returnVal = fileChooser.showSaveDialog((Component)e.getSource());
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    mModelSettingsIO.save(file);
                } catch (Exception ex) {
                    System.out.println("problem accessing file"+file.getAbsolutePath());
                }
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mSettings.reset();
                setValues();
            }
        });
    }
}
