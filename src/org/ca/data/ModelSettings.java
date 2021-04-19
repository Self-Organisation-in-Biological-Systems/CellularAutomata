package org.ca.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ModelSettings {
    private int XSize;
    private int YSize;
    private double StartA;
    private double AReplenish;
    private double BDecayRate;
    private double StartOnPercent;
    private double DiffusionRate;
    private double BDiffusionRate;
    private double ReactionRate;
    private double ActivationRate; //chance of an active cell turning on its neighbors
    private double ActivationThreshold; //min A value that a cell ust have in order to be switched on, otherwise it will stay off
    private double ActivationDelay;
    private int DrawEvery;
    private int MaxLifeTime;
    private int ShutoffAThreshold; //thee values will keep them fro shutting off fro thresholds
    private int ShutoffBThreshold;
    private double PigmentThreshold;
    private boolean ShowCellStates;

    private boolean DrawInGiraffeColors;
    private boolean DrawScaledToMax;

    private boolean DrawGiraffePattern;
    private boolean DrawSingletonPattern;
    private boolean DrawWeirdPhylloPattern;
    private boolean DrawPhylloPattern;
    private boolean DrawGridPattern;
    private boolean DrawHexPattern;
    private boolean DrawIrregularHexPattern;
    private boolean ApplyGradient;

    public ModelSettings() {
        reset();
    }

    public void load(ModelSettings other) {
        setPigmentThreshold(other.getPigmentThreshold());
        setDrawEvery(other.getDrawEvery());
        setXSize(other.getXSize());
        setYSize(other.getYSize());
        setStartA(other.getStartA());
        setStartOnPercent(other.getStartOnPercent());
        setAReplenish(other.getAReplenish());
        setDiffusionRate(other.getDiffusionRate());
        setBDiffusionRate(other.getBDiffusionRate());
        setBDecayRate(other.getBDecayRate());
        setReactionRate(other.getReactionRate());
        setActivationRate(other.getActivationRate());
        setActivationThreshold(other.getActivationThreshold());
        setActivationDelay(other.getActivationDelay());
        setMaxLifeTime(other.getMaxLifeTime());
        setShutoffAThreshold(other.getShutoffAThreshold());
        setShutoffBThreshold(other.getShutoffBThreshold());

        setDrawGiraffePattern(other.getDrawGiraffePattern());
        setDrawSingletonPattern(other.getDrawSingletonPattern());
        setDrawWeirdPhylloPattern(other.getDrawWeirdPhylloPattern());
        setDrawPhylloPattern(other.getDrawPhylloPattern());
        setDrawGridPattern(other.getDrawGridPattern());
        setDrawHexPattern(other.getDrawHexPattern());
        setDrawIrregularHexPattern(other.getDrawIrregularHexPattern());

        setDrawInGiraffeColors(other.getDrawInGiraffeColors());
        setDrawScaledToMax(other.getDrawScaledToMax());
        setShowCellStates(other.getShowCellStates());

        setApplyGradient(other.getApplyGradient());
    }

    public void reset() {
        setPigmentThreshold(0.0);
        setDrawEvery(10);
        setXSize(200);
        setYSize(200);
        setStartA(1.0);
        setStartOnPercent(0.001);
        setAReplenish(0.0);
        setDiffusionRate(1.0);
        setBDiffusionRate(0);
        setBDecayRate(0);
        setReactionRate(1.0);
        setActivationRate(1);//chance of an active cell turning on its neighbors
        setActivationThreshold(0.25); //min A value that a cell must have in order to be switched on, otherwise it will stay off
        setActivationDelay(10.0);
        setMaxLifeTime(1000);
        setShutoffAThreshold(-1); //thee values will keep them from shutting off from thresholds
        setShutoffBThreshold(10);

        setDrawGiraffePattern(true);

        setDrawInGiraffeColors(true);
        setDrawScaledToMax(false);
        setShowCellStates(true);

        setApplyGradient(false);
    }

    //--------settings---------
    @JsonProperty("XSize")
    public int getXSize() {
        return XSize;
    }
    @JsonProperty("XSize")
    public void setXSize(int xSize) {
        XSize = xSize;
    }

    @JsonProperty("YSize")
    public int getYSize() {
        return YSize;
    }
    @JsonProperty("YSize")
    public void setYSize(int ySize) {
        YSize = ySize;
    }

    @JsonProperty("StartA")
    public double getStartA() {
        return StartA;
    }
    @JsonProperty("StartA")
    public void setStartA(double startA) {
        StartA = startA;
    }

    @JsonProperty("AReplenish")
    public double getAReplenish() {
        return AReplenish;
    }
    @JsonProperty("AReplenish")
    public void setAReplenish(double aReplenish) {
        AReplenish = aReplenish;
    }

    @JsonProperty("BDecayRate")
    public double getBDecayRate() { return BDecayRate; }
    @JsonProperty("BDecayRate")
    public void setBDecayRate(double bDecayRate) {
        BDecayRate = bDecayRate;
    }

    @JsonProperty("StartOnPercent")
    public double getStartOnPercent() {
        return StartOnPercent;
    }
    @JsonProperty("StartOnPercent")
    public void setStartOnPercent(double startOnPercent) { StartOnPercent = startOnPercent; }

    @JsonProperty("DiffusionRate")
    public double getDiffusionRate() {
        return DiffusionRate;
    }
    @JsonProperty("DiffusionRate")
    public void setDiffusionRate(double diffusionRate) {
        DiffusionRate = diffusionRate;
    }

    @JsonProperty("BDiffusionRate")
    public double getBDiffusionRate() {
        return BDiffusionRate;
    }
    @JsonProperty("BDiffusionRate")
    public void setBDiffusionRate(double bDiffusionRate) {
        BDiffusionRate = bDiffusionRate;
    }

    @JsonProperty("ReactionRate")
    public double getReactionRate() {
        return ReactionRate;
    }
    @JsonProperty("ReactionRate")
    public void setReactionRate(double reactionRate) {
        ReactionRate = reactionRate;
    }

    @JsonProperty("ActivationRate")
    public double getActivationRate() {
        return ActivationRate;
    }
    @JsonProperty("ActivationRate")
    public void setActivationRate(double activationRate) {
        ActivationRate = activationRate;
    }

    @JsonProperty("ActivationThreshold")
    public double getActivationThreshold() {
        return ActivationThreshold;
    }
    @JsonProperty("ActivationThreshold")
    public void setActivationThreshold(double activationThreshold) {
        ActivationThreshold = activationThreshold;
    }

    @JsonProperty("ActivationDelay")
    public double getActivationDelay() {
        return ActivationDelay;
    }
    @JsonProperty("ActivationDelay")
    public void setActivationDelay(double activationDelay) {
        ActivationDelay = activationDelay;
    }

    @JsonProperty("DrawEvery")
    public int getDrawEvery() {
        return DrawEvery;
    }
    @JsonProperty("DrawEvery")
    public void setDrawEvery(int drawEvery) {
        DrawEvery = drawEvery;
    }

    @JsonProperty("MaxLifeTime")
    public int getMaxLifeTime() {
        return MaxLifeTime;
    }
    @JsonProperty("MaxLifeTime")
    public void setMaxLifeTime(int maxLifeTime) {
        MaxLifeTime = maxLifeTime;
    }

    @JsonProperty("ShutoffAThreshold")
    public int getShutoffAThreshold() {
        return ShutoffAThreshold;
    }
    @JsonProperty("ShutoffAThreshold")
    public void setShutoffAThreshold(int shutoffAThreshold) {
        ShutoffAThreshold = shutoffAThreshold;
    }

    @JsonProperty("ShutoffBThreshold")
    public int getShutoffBThreshold() {
        return ShutoffBThreshold;
    }
    @JsonProperty("ShutoffBThreshold")
    public void setShutoffBThreshold(int shutoffBThreshold) {
        ShutoffBThreshold = shutoffBThreshold;
    }

    @JsonProperty("PigmentThreshold")
    public double getPigmentThreshold() { return PigmentThreshold; }
    @JsonProperty("PigmentThreshold")
    public void setPigmentThreshold(double threshold) {PigmentThreshold = threshold; }


    //--------colors---------

    @JsonProperty("ShowCellStates")
    public void setShowCellStates(boolean show) {
        ShowCellStates = show;
    }
    @JsonProperty("ShowCellStates")
    public boolean getShowCellStates() {
        return ShowCellStates;
    }

    @JsonProperty("DrawInGiraffeColors")
    public void setDrawInGiraffeColors(boolean grf) {
        DrawInGiraffeColors = grf;
    }
    @JsonProperty("DrawInGiraffeColors")
    public boolean getDrawInGiraffeColors() {
        return DrawInGiraffeColors;
    }

    @JsonProperty("DrawScaledToMax")
    public void setDrawScaledToMax(boolean scale) {
        DrawScaledToMax = scale;
    }
    @JsonProperty("DrawScaledToMax")
    public boolean getDrawScaledToMax() {
        return DrawScaledToMax;
    }

    //--------patterns---------

    private void resetPatterns() {
        DrawGiraffePattern = false;
        DrawSingletonPattern = false;
        DrawWeirdPhylloPattern = false;
        DrawPhylloPattern = false;
        DrawGridPattern = false;
        DrawHexPattern = false;
        DrawIrregularHexPattern = false;
    }

    @JsonProperty("DrawGiraffePattern")
    public void setDrawGiraffePattern(boolean b) {
        resetPatterns();
        DrawGiraffePattern = true;
    }

    @JsonProperty("DrawGiraffePattern")
    public boolean getDrawGiraffePattern() {
        return DrawGiraffePattern;
    }

    @JsonProperty("DrawSingletonPattern")
    public void setDrawSingletonPattern(boolean b) {
        resetPatterns();
        DrawSingletonPattern = true;
    }
    @JsonProperty("DrawSingletonPattern")
    public boolean getDrawSingletonPattern() {
        return DrawSingletonPattern;
    }

    @JsonProperty("DrawWeirdPhylloPattern")
    public void setDrawWeirdPhylloPattern(boolean b) {
        resetPatterns();
        DrawWeirdPhylloPattern = true;
    }
    @JsonProperty("DrawWeirdPhylloPattern")
    public boolean getDrawWeirdPhylloPattern() {
        return DrawWeirdPhylloPattern;
    }

    @JsonProperty("DrawPhylloPattern")
    public void setDrawPhylloPattern(boolean b) {
        resetPatterns();
        DrawPhylloPattern = true;
    }
    @JsonProperty("DrawPhylloPattern")
    public boolean getDrawPhylloPattern() {
        return DrawPhylloPattern;
    }

    @JsonProperty("DrawGridPattern")
    public void setDrawGridPattern(boolean b) {
        resetPatterns();
        DrawGridPattern = true;
    }
    @JsonProperty("DrawGridPattern")
    public boolean getDrawGridPattern() {
        return DrawGridPattern;
    }

    @JsonProperty("DrawHexPattern")
    public void setDrawHexPattern(boolean b) {
        resetPatterns();
        DrawHexPattern = true;
    }
    @JsonProperty("DrawHexPattern")
    public boolean getDrawHexPattern() {
        return DrawHexPattern;
    }

    @JsonProperty("DrawIrregularHexPattern")
    public void setDrawIrregularHexPattern(boolean b) {
        resetPatterns();
        DrawIrregularHexPattern = true;
    }
    @JsonProperty("DrawIrregularHexPattern")
    public boolean getDrawIrregularHexPattern() {
        return DrawIrregularHexPattern;
    }

    @JsonProperty("ApplyGradient")
    public void setApplyGradient(boolean apply) {
        ApplyGradient = apply;
    }
    @JsonProperty("ApplyGradient")
    public boolean getApplyGradient() {
        return ApplyGradient;
    }
}
