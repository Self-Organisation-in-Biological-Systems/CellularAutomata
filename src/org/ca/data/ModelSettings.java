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
        mModelSettingsIO = new ModelSettingsIO(this);
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

        drawGiraffePattern();

        drawInGiraffeColors(true);
        drawScaledToMax(false);
        showCellStates(true);
    }

    //--------settings---------

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


    //--------colors---------

    public void showCellStates(boolean show) {
        mShowCellStates = show;
    }

    public boolean getShowCellStates() {
        return mShowCellStates;
    }

    public void drawInGiraffeColors(boolean grf) {
        mDrawInGiraffeColors = grf;
    }

    public boolean getDrawInGiraffeColors() {
        return mDrawInGiraffeColors;
    }

    public void  drawScaledToMax(boolean scale) {
        mDrawScaledToMax = scale;
    }

    public boolean getDrawScaledToMax() {
        return mDrawScaledToMax;
    }

    //--------patterns---------

    private void resetPatterns() {
        mDrawGiraffePattern = false;
        mDrawSingletonPattern = false;
        mDrawWeirdPhylloPattern = false;
        mDrawPhylloPattern = false;
        mDrawGridPattern = false;
        mDrawHexPattern = false;
        mDrawIrregularHexPattern = false;
        mDrawConcentricCirclePattern = false;
    }

    public void drawGiraffePattern() {
        resetPatterns();
        mDrawGiraffePattern = true;
    }

    public boolean getDrawGiraffePattern() {
        return mDrawGiraffePattern;
    }

    public void drawSingletonPattern() {
        resetPatterns();
        mDrawSingletonPattern = true;
    }

    public boolean getDrawSingletonPattern() {
        return mDrawSingletonPattern;
    }

    public void drawWeirdPhylloPattern() {
        resetPatterns();
        mDrawWeirdPhylloPattern = true;
    }

    public boolean getDrawWeirdPhylloPattern() {
        return mDrawWeirdPhylloPattern;
    }

    public void drawPhylloPattern() {
        resetPatterns();
        mDrawPhylloPattern = true;
    }

    public boolean getDrawPhylloPattern() {
        return mDrawPhylloPattern;
    }

    public void drawGridPattern() {
        resetPatterns();
        mDrawGridPattern = true;
    }
    public boolean getDrawGridPattern() {
        return mDrawGridPattern;
    }

    public void drawHexPattern() {
        resetPatterns();
        mDrawHexPattern = true;
    }

    public boolean getDrawHexPattern() {
        return mDrawHexPattern;
    }

    public void drawIrregularHexPattern() {
        resetPatterns();
        mDrawIrregularHexPattern = true;
    }

    public boolean getDrawIrregularHexPattern() {
        return mDrawIrregularHexPattern;
    }

    public void drawConcentricCirclePattern() {
        resetPatterns();
        mDrawConcentricCirclePattern = true;
    }

    public boolean getDrawConcentricCirclePattern() {
        return mDrawConcentricCirclePattern;
    }

    public void applyGradient(boolean apply) {
        mApplyGradient = apply;
    }

    public boolean getApplyGradient() {
        return mApplyGradient;
    }
}
