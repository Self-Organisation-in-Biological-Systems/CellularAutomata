package org.ca.data;

public class ModelSettings {
    private int mXSize;
    private int mYSize;
    private double mStartA;
    private double mAReplenish;
    private double mBDecay;
    private double mStartOnPercent;
    private double mDiffusionRate;
    private double mBDiffusionRate;
    private double mReactionRate;
    private double mActivationRate; //chance of an active cell turning on its neighbors
    private double mActivationThreshold; //min A value that a cell must have in order to be switched on, otherwise it will stay off
    private double mActivationDelay;
    private int mDrawEvery;
    private int mMaxLifeTime;
    private int mShutoffAThreshold; //thee values will keep them from shutting off from thresholds
    private int mShutoffBThreshold;
    private double mPigmentThreshold;
    private boolean mShowCellStates;

    private boolean mDrawInGiraffeColors;
    private boolean mDrawScaledToMax;

    private boolean mDrawGiraffePattern;
    private boolean mDrawSingletonPattern;
    private boolean mDrawWeirdPhylloPattern;
    private boolean mDrawPhylloPattern;
    private boolean mDrawGridPattern;
    private boolean mDrawHexPattern;
    private boolean mDrawIrregularHexPattern;
    private boolean mDrawConcentricCirclePattern;
    private boolean mApplyGradient;

    private ModelSettingsIO mModelSettingsIO;

    public ModelSettings() {
        mModelSettingsIO = new ModelSettingsIO();
        reset();
    }

    public ModelSettingsIO getModelSettingsIO() {
        return mModelSettingsIO;
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
        setDrawSingletonPattern(false);
        setDrawWeirdPhylloPattern(false);
        setDrawPhylloPattern(false);
        setDrawGridPattern(false);
        setDrawHexPattern(false);
        setDrawIrregularHexPattern(false);
        setDrawConcentricCirclePattern(false);
        setApplyGradient(false);

        setDrawInGiraffeColors(true);
        setDrawScaledToMax(true);
        setShowCellStates(false);
    }


    public int getXSize() {
        return mXSize;
    }

    public void setXSize(int xSize) {
        this.mXSize = xSize;
    }

    public int getYSize() {
        return mYSize;
    }

    public void setYSize(int ySize) {
        this.mYSize = ySize;
    }

    public double getStartA() {
        return mStartA;
    }

    public void setStartA(double startA) {
        this.mStartA = startA;
    }

    public double getAReplenish() {
        return mAReplenish;
    }

    public void setAReplenish(double aReplenish) {
        this.mAReplenish = aReplenish;
    }

    public double getBDecay() {
        return mBDecay;
    }

    public void setBDecayRate(double bDecay) {
        this.mBDecay = bDecay;
    }

    public double getStartOnPercent() {
        return mStartOnPercent;
    }

    public void setStartOnPercent(double startOnPercent) {
        this.mStartOnPercent = startOnPercent;
    }

    public double getDiffusionRate() {
        return mDiffusionRate;
    }

    public void setDiffusionRate(double diffusionRate) {
        this.mDiffusionRate = diffusionRate;
    }

    public double getBDiffusionRate() {
        return mBDiffusionRate;
    }

    public void setBDiffusionRate(double bDiffusionRate) {
        this.mBDiffusionRate = bDiffusionRate;
    }

    public double getReactionRate() {
        return mReactionRate;
    }

    public void setReactionRate(double reactionRate) {
        this.mReactionRate = reactionRate;
    }

    public double getActivationRate() {
        return mActivationRate;
    }

    public void setActivationRate(double activationRate) {
        this.mActivationRate = activationRate;
    }

    public double getActivationThreshold() {
        return mActivationThreshold;
    }

    public void setActivationThreshold(double activationThreshold) {
        this.mActivationThreshold = activationThreshold;
    }

    public double getActivationDelay() {
        return mActivationDelay;
    }

    public void setActivationDelay(double activationDelay) {
        this.mActivationDelay = activationDelay;
    }

    public int getDrawEvery() {
        return mDrawEvery;
    }

    public void setDrawEvery(int drawEvery) {
        this.mDrawEvery = drawEvery;
    }

    public int getMaxLifeTime() {
        return mMaxLifeTime;
    }

    public void setMaxLifeTime(int maxLifeTime) {
        this.mMaxLifeTime = maxLifeTime;
    }

    public int getShutoffAThreshold() {
        return mShutoffAThreshold;
    }

    public void setShutoffAThreshold(int shutoffAThreshold) {
        this.mShutoffAThreshold = shutoffAThreshold;
    }

    public int getShutoffBThreshold() {
        return mShutoffBThreshold;
    }

    public void setShutoffBThreshold(int shutoffBThreshold) {
        this.mShutoffBThreshold = shutoffBThreshold;
    }

    public double getPigmentThreshold() { return mPigmentThreshold; }

    public void setPigmentThreshold(double threshold) {mPigmentThreshold = threshold; }

    public boolean showCellStates() {
        return mShowCellStates;
    }

    public void setShowCellStates(boolean showCellStates) {
        mShowCellStates = showCellStates;
    }

    public boolean drawInGiraffeColors() {
        return mDrawInGiraffeColors;
    }

    public void setDrawInGiraffeColors(boolean drawInGiraffeColors) {
        mDrawInGiraffeColors = drawInGiraffeColors;
    }

    public boolean drawScaledToMax() {
        return mDrawScaledToMax;
    }

    public void setDrawScaledToMax(boolean drawScaledToMax) {
        mDrawScaledToMax = drawScaledToMax;
    }

    public boolean drawGiraffePattern() {
        return mDrawGiraffePattern;
    }

    public void setDrawGiraffePattern(boolean drawGiraffePattern) {
        mDrawGiraffePattern = drawGiraffePattern;
    }

    public boolean drawSingletonPattern() {
        return mDrawSingletonPattern;
    }

    public void setDrawSingletonPattern(boolean drawSingletonPattern) {
        mDrawSingletonPattern = drawSingletonPattern;
    }

    public boolean drawWeirdPhylloPattern() {
        return mDrawWeirdPhylloPattern;
    }

    public void setDrawWeirdPhylloPattern(boolean drawWeirdPhylloPattern) {
        mDrawWeirdPhylloPattern = drawWeirdPhylloPattern;
    }

    public boolean drawPhylloPattern() {
        return mDrawPhylloPattern;
    }

    public void setDrawPhylloPattern(boolean drawPhylloPattern) {
        mDrawPhylloPattern = drawPhylloPattern;
    }

    public boolean drawGridPattern() {
        return mDrawGridPattern;
    }

    public void setDrawGridPattern(boolean drawGridPattern) {
        mDrawGridPattern = drawGridPattern;
    }

    public boolean drawHexPattern() {
        return mDrawHexPattern;
    }

    public void setDrawHexPattern(boolean drawHexPattern) {
        mDrawHexPattern = drawHexPattern;
    }

    public boolean drawIrregularHexPattern() {
        return mDrawIrregularHexPattern;
    }

    public void setDrawIrregularHexPattern(boolean drawIrregularHexPattern) {
        mDrawIrregularHexPattern = drawIrregularHexPattern;
    }

    public boolean drawConcentricCirclePattern() {
        return mDrawConcentricCirclePattern;
    }

    public void setDrawConcentricCirclePattern(boolean drawConcentricCirclePattern) {
        mDrawConcentricCirclePattern = drawConcentricCirclePattern;
    }

    public boolean applyGradient() {
        return mApplyGradient;
    }

    public void setApplyGradient(boolean applyGradient) {
        mApplyGradient = applyGradient;
    }
}
