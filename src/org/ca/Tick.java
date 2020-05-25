package org.ca;

//http://serpwidgets.com/giraffes/giraffealgorithm.htm

import org.ca.data.ModelSettings;
import org.ca.data.ModelState;
import org.ca.data.Patterns;

import java.util.*;

public class Tick {
    private ModelState mState;
    private ModelSettings mSettings;
    private Patterns mPatterns;
    private GraphicFrame mGraphic;
    private Timer mMainTimer;
    private Timer mCheckThresholdTimer;

    public Tick(ModelState modelState, ModelSettings settings, Patterns patterns, GraphicFrame graphic) {
        mState = modelState;
        mSettings = settings;
        mPatterns = patterns;
        mGraphic = graphic;
    }

    // FIXME: add the capability to have a user-defined list of starting "on" cells so that a specific pattern can be replicated//add histogram view
    //add a-replenish rate as an option

    //fixme: add U replenish and V decay options just for the fun of it
    //fixme: once this works exactly like a regular UV R-D system, also add migrating activation to try and generate corn pattens
    //fixme: add "load in a starting picture" to set starting UV values
    //fixme: add "load in another starting picture to set usable cells" (user can specify a non-rectangular picture)

    private boolean running = false;
    private boolean paused = false;

    private int tickCount;//how many cycles have elapsed

    private int oldXSize;
    private int oldYSize;

    private boolean checkForGraphicsSizeChange() {
        boolean graphicsSizeChanged = false;

        if(mSettings.getXSize() != oldXSize || mSettings.getXSize() != oldYSize)
        graphicsSizeChanged = true;

        oldXSize = mSettings.getXSize();
        oldYSize = mSettings.getYSize();

        return graphicsSizeChanged;
    }

    public void init() {
        mState.reset(mSettings);
        mPatterns.getPattern();
        paused = false;
    }

    private void startTicking() {
        mMainTimer = new Timer();
        mMainTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() { tick(true); }
        },0,10);

        mCheckThresholdTimer = new Timer();
        mCheckThresholdTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() { checkThreshold(); }
        },0,500);
    }

    public void start() {
        boolean graphicsSizeChanged = checkForGraphicsSizeChange();
        if(graphicsSizeChanged) {
            mGraphic.createGraphicFrame();
        }
        init();
        startTicking();
        tickCount = 0;
        running = true;
    }

    public void showDotPattern() {
        init();
        tickCount = 0;
        tick(false);
    }

    public void stop() {
        if(!running)
            return;

        if(mMainTimer != null)
            mMainTimer.cancel();
        if(mCheckThresholdTimer != null)
            mCheckThresholdTimer.cancel();
    }

    public void pauseUnPause() {
        if(!running)
            return;

        boolean pausing = !paused;

        if(pausing) {
            if(mMainTimer != null)
                mMainTimer.cancel();
            if(mCheckThresholdTimer != null)
                mCheckThresholdTimer.cancel();
            paused = true;
        } else  {
            startTicking();
            paused = false;
        }
    }

    private void tick(boolean doDiffusion) {
        if(paused)
            return;

        try {
            //System.out.println("tick " + tickCount++);
            //getUserVars();//fetches values so they can be changed as it's running
            double diffRate = mSettings.getDiffusionRate() * 0.5; //allow user to use range of 0-1 but optimize calc speed
            double diffRateDiag = mSettings.getDiffusionRate() * 0.5 * 0.707;
            double bDiffRate = mSettings.getBDiffusionRate() * 0.5; //for B molecule if needed
            double bDiffRateDiag = mSettings.getBDiffusionRate() * 0.5 * 0.707;
            double activationRateDiag = mSettings.getActivationRate() * 0.707;
            for (int t = 0; t < mState.getCellCount(); t++) {
                //pick a cell
                int cNum = (int) Math.floor(Math.random() * mState.getCellCount());
                //diffuse A
                //double dryUpRate = 0.1; //TODO added by paul
                int totalNeighbors = mState.getCellNeighbor(cNum).length + mState.getCellDiagNeighbor(cNum).length;
                double dSelf = (mState.getCellA(cNum)) / totalNeighbors;
                for (int i = 0; i < mState.getCellDiagNeighbor(cNum).length; i++) {
                    int nNum = mState.getCellDiagNeighbor(cNum)[i];
                    double dN = (mState.getCellA(nNum)) / totalNeighbors;//get diffusable value in neighbor
                    double swap = (dSelf - dN) * diffRateDiag; //determine amount to be exchanged
                    mState.decrementCellA(cNum, swap);  //exchange it
                    mState.incrementCellA(nNum, swap);  //*dryUpRate;
                }
                for (int i = 0; i < mState.getCellNeighbor(cNum).length; i++) {
                    int nNum = mState.getCellNeighbor(cNum)[i];
                    double dN = (mState.getCellA(nNum)) / totalNeighbors;//get diffusable value in neighbor
                    double swap = (dSelf - dN) * diffRate; //determine amount to be exchanged
                    mState.decrementCellA(cNum, swap);  //exchange it
                    mState.incrementCellA(nNum, swap);  //*dryUpRate;
                }


                if (mSettings.getBDiffusionRate() > 0) {
                    //diffuse molecule B at its own rate if necessary
                    dSelf = (mState.getCellB(cNum)) / totalNeighbors;
                    for (int i = 0; i < mState.getCellNeighbor(cNum).length; i++) {
                        int nNum = mState.getCellNeighbor(cNum)[i];
                        double dN = (mState.getCellB(nNum)) / totalNeighbors;//get diffusable value in neighbor
                        double swap = (dSelf - dN) * bDiffRate; //determine amount to be exchanged
                        mState.decrementCellB(cNum, swap);  //exchange it
                        mState.incrementCellB(nNum, swap);  //*dryUpRate;
                    }
                    for (int i = 0; i < mState.getCellDiagNeighbor(cNum).length; i++) {
                        int nNum = mState.getCellDiagNeighbor(cNum)[i];
                        double dN = (mState.getCellB(nNum)) / totalNeighbors;//get diffusable value in neighbor
                        double swap = (dSelf - dN) * bDiffRateDiag; //determine amount to be exchanged
                        mState.decrementCellB(cNum, swap);  //exchange it
                        mState.incrementCellB(nNum, swap);  //*dryUpRate;
                    }
                }

                if (mState.getCellState(cNum)) {
                    //if on, turn on any neighbors that are off
                    if (mState.tryToActivateNeighbors(cNum)) {
                        if (mState.getCellActivationDelay(cNum) > 0) {
                            mState.decrementCellActivationDelay(cNum);
                        } else {
                            if (Math.random() < mSettings.getActivationRate()) {
                                for (int i = 0; i < mState.getCellNeighbor(cNum).length; i++) {
                                    int nNum = mState.getCellNeighbor(cNum)[i];
                                    if (!mState.getCellState(nNum) && mState.getCellA(nNum) >= mSettings.getActivationThreshold()) {//if this neighbor is off, turn it on if it can be
                                        mState.setTryToActivateNeighbors(nNum, true);
                                        mState.setCellState(nNum, true);
                                        mState.setCellActivationDelay(nNum, mSettings.getActivationDelay());
                                        //lifeTime[nNum]=maxLifeTime;
                                    }
                                }
                                mState.setTryToActivateNeighbors(cNum, false);
                                for (int i = 0; i < mState.getCellNeighbor(cNum).length; i++) { //if any inactivated neighbors remain, flag this cell to try and activate them again
                                    if (!mState.getCellState(mState.getCellNeighbor(cNum)[i]))
                                        mState.setTryToActivateNeighbors(cNum, true);
                                }
                            }
                            if (Math.random() < activationRateDiag) {
                                for (int i = 0; i < mState.getCellDiagNeighbor(cNum).length; i++) {
                                    int nNum = mState.getCellDiagNeighbor(cNum)[i];
                                    if (!mState.getCellState(nNum) && mState.getCellA(nNum) >= mSettings.getActivationThreshold()) {//if this neighbor is off, turn it on if it can be
                                        mState.setTryToActivateNeighbors(nNum, true);
                                        mState.setCellState(nNum, true);
                                        mState.setCellActivationDelay(nNum, mSettings.getActivationDelay());
                                        //lifeTime[nNum]=maxLifeTime;
                                    }
                                }
                                for (int i = 0; i < mState.getCellDiagNeighbor(cNum).length; i++) { //if any inactivated neighbors remain, flag this cell to try and activate them again
                                    if (!mState.getCellState(mState.getCellDiagNeighbor(cNum)[i]))
                                        mState.setTryToActivateNeighbors(cNum, true);
                                }

                            }
                        }
                    }//try to activate neighbors

                    //shut off cell if it passes concentration thresholds or (fixme) if it has been on for a certain amount of time?
                    if ((mState.getCellA(cNum) < mSettings.getShutoffAThreshold()) || (mState.getCellB(cNum) > mSettings.getShutoffBThreshold())) {
                        mState.setCellState(cNum, false);
                        mState.setTryToActivateNeighbors(cNum, false);
                    } else {
                        mState.decrementLifeTime(cNum);
                        if (mState.getLifeTime(cNum) < 0)
                            mState.setCellState(cNum, false);
                    }
                }//cell state on

                //react A into B if present
                if (mState.getCellState(cNum) && mState.getCellA(cNum) > 0) {
                    double amt = mState.getCellA(cNum) * mState.getCellA(cNum) * mSettings.getReactionRate();
                    mState.decrementCellA(cNum, amt);//convert A into B
                    mState.incrementCellB(cNum, amt);
                }

                //fixme: make cells shut off if their A level is below a certain amount or if B rises above a certain amount.

                //replenish A if needed:
                if ((mSettings.getAReplenish() > 0.0) && (mState.getCellA(cNum) < 1.0))
                    mState.incrementCellA(cNum, mSettings.getAReplenish());

                if ((mSettings.getAReplenish() < 1.0) && (mState.getCellB(cNum) > 0))
                    mState.multiplyCellB(cNum, 1.0-mSettings.getBDecay());
            }

//            for(int col=0; col<mSettings.getXSize(); col++) {
//                int row = 10;
//                int i = row + col * mSettings.getYSize();
//                System.out.print(String.format("%2d,%-2d %s ", (int)(mState.getCellA(i)*10), (int)(mState.getCellB(i)*10), (mState.getCellState(i)?"*":" ")));
//            }
//            System.out.println();

            if (tickCount % mSettings.getDrawEvery() == 0 || !doDiffusion) {
                mainDraw();
                //also check to see if all cells are off, if not a single cell is on, the simulation is over
//                boolean simEnded = true;
//                for (int i = 0; i < mState.getCellCount(); i++) {
//                    if (cellState[i]) {
//                        simEnded = false;
//                        break;
//                    }
//                }
                if (!doDiffusion) {
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private double lastThreshold = 0.0;

    private void checkThreshold() {
        //TODO pk fix this
//        mSettings.setPigmentThreshold(mSettings.getPigmentThreshold());
//        if (lastThreshold != pigmentThreshold) {
//            lastThreshold = pigmentThreshold;
//        }
//        useGiraffeColors = mSettings.drawInGiraffeColors();
//        drawScaled = mSettings.drawScaledToMax();
//        showCellStates = mSettings.showCellStates();
    }

    //will diffuse the B molecule once to smooth out edges a little
    public void diffuseB() {
        double dRate = 0.5;
        double dRateDiag = 0.3535;
        for (int t = 0; t < mState.getCellCount(); t++) {
            int cNum = (int)Math.floor(Math.random() * mState.getCellCount());//pick a cell
            int totalNeighbors = mState.getCellNeighbor(cNum).length + mState.getCellDiagNeighbor(cNum).length;
            double dSelf = (mState.getCellB(cNum)) / totalNeighbors; //TODO dSelf might be global??

            for (int i = 0; i < mState.getCellNeighbor(cNum).length; i++) {
                int nNum = mState.getCellNeighbor(cNum)[i];
                double dN = (mState.getCellB(nNum)) / totalNeighbors;//get diffusable value in neighbor
                double swap = (dSelf - dN) * dRate; //determine amount to be exchanged
                mState.decrementCellB(cNum, swap);  //exchange it
                mState.incrementCellB(nNum, swap);  //*dryUpRate;
            }
            for (int i = 0; i < mState.getCellDiagNeighbor(cNum).length; i++) {
                int nNum = mState.getCellDiagNeighbor(cNum)[i];
                double dN = (mState.getCellB(nNum)) / totalNeighbors;//get diffusable value in neighbor
                double swap = (dSelf - dN) * dRateDiag; //determine amount to be exchanged
                mState.decrementCellB(cNum, swap);  //exchange it
                mState.incrementCellB(nNum, swap);  //*dryUpRate;
            }
        }
        mainDraw();
    }

    public void mainDraw() {
        mGraphic.mainDraw();
    }
}