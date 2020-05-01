package org.ca.panels;

import org.ca.GraphicFrame;
import org.ca.Tick;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class CASettings extends JPanel {
    private int pigmentThreshold = 0;
    private int drawEveryNthCycle = 10;
    private int xSize = 200;
    private int ySize = 200;
    private double startA = 1.0;
    private double startOnPercent = 0.001;
    private double A_Replenish = 0.0;
    private double DiffusionRate = 1.0;
    private double B_DiffusionRate = 0.0;
    private double B_DecayRate = 0;
    private double reactionRate = 1.0;
    private double activationRate = 1.0;
    private double activationThreshold = 0.25;
    private double activationDelay = 10.0;
    private int maxLifeTime = 1000;
    private int shutoffAThreshold = -1;
    private int shutoffBThreshold = 10;

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

    public CASettings() {
        this.setBorder(BorderFactory.createEmptyBorder());

        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        this.setLayout(layout);

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
        pigmentThresholdTextField.setText(Integer.toString(pigmentThreshold));
        drawEveryNthCycleTextField.setText(Integer.toString(drawEveryNthCycle));
        xSizeTextField.setText(Integer.toString(xSize));
        ySizeTextField.setText(Integer.toString(ySize));
        startATextField.setText(Double.toString(startA));
        startOnPercentTextField.setText(Double.toString(startOnPercent));
        AReplenishTextField.setText(Double.toString(A_Replenish));
        DiffusionRateTextField.setText(Double.toString(DiffusionRate));
        BDiffusionRateTextField.setText(Double.toString(B_DiffusionRate));
        BDecayRateTextField.setText(Double.toString(B_DecayRate));
        reactionRateTextField.setText(Double.toString(reactionRate));
        activationRateTextField.setText(Double.toString(activationRate));
        activationThresholdTextField.setText(Double.toString(activationThreshold));
        activationDelayTextField.setText(Double.toString(activationDelay));
        maxLifeTimeTextField.setText(Integer.toString(maxLifeTime));
        shutoffAThresholdTextField.setText(Integer.toString(shutoffAThreshold));
        shutoffBThresholdTextField.setText(Integer.toString(shutoffBThreshold));
    }

    public int getXSize() {
        return Integer.parseInt(xSizeTextField.getText());
    }
    public int getYSize() { return Integer.parseInt(ySizeTextField.getText()); }
    public double getPigmentThreshold() {
        return Double.parseDouble(pigmentThresholdTextField.getText());
    }
    public int getDawEveryNthCycle() {
        return Integer.parseInt(drawEveryNthCycleTextField.getText());
    }
    public double getStartA() {
        return Double.parseDouble(startATextField.getText());
    }
    public double getStartOnPercent() {
        return Double.parseDouble(startOnPercentTextField.getText());
    }
    public double getAReplenish() { return Double.parseDouble(AReplenishTextField.getText()); }
    public double getDiffusionRate() {
        return Double.parseDouble(DiffusionRateTextField.getText());
    }
    public double getBDiffusionRate() {
        return Double.parseDouble(BDiffusionRateTextField.getText());
    }
    public double getBDecayRate() {
        return Double.parseDouble(BDecayRateTextField.getText());
    }
    public double getReactionRate() {
        return Double.parseDouble(reactionRateTextField.getText());
    }
    public double getActivationRate() {
        return Double.parseDouble(activationRateTextField.getText());
    }
    public double getActivationThreshold() {
        return Double.parseDouble(activationThresholdTextField.getText());
    }
    public double getActivationDelay() { return Double.parseDouble(activationDelayTextField.getText()); }
    public int getMaxLifeTime() {
        return Integer.parseInt(maxLifeTimeTextField.getText());
    }
    public int getShutoffAThreshold() {
        return Integer.parseInt(shutoffAThresholdTextField.getText());
    }
    public int getShutoffBThreshold() {
        return Integer.parseInt(shutoffBThresholdTextField.getText());
    }
}