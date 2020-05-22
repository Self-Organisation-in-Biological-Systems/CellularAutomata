package org.ca;

//http://serpwidgets.com/giraffes/giraffealgorithm.htm

import org.ca.data.ModelSettings;
import org.ca.data.ModelState;

import java.util.*;

public class Tick {
    private ModelState mState;
    private ModelSettings mSettings;
    private GraphicFrame mGraphic;
    private Timer mMainTimer;
    private Timer mCheckThresholdTimer;

    public Tick(ModelState modelState, ModelSettings settings, ControlFrame controlFrame, GraphicFrame graphic) {
        mState = modelState;
        mSettings = settings;
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

    private int isRunning = 0;
    private int tickCount;//how many cycles have elapsed
    private boolean useGiraffeColors = true;
    private boolean drawScaled = false;
    private int histoBanding = 0;

    private boolean getUserVars() {
        boolean graphicsSizeChanged = false;
        int oldXSize = mSettings.getXSize();
        int oldYSize = mSettings.getYSize();

//        if(xSize != oldXSize || ySize != oldYSize)
//            graphicsSizeChanged = true;
        histoBanding = 0;


        return graphicsSizeChanged;
    }

    //deterministic number generator so we can see varied results with identical starting conditions but different settings
    private double myRandom() {
        return Math.random();
    }

    public void init() {
        getUserVars();

        if (mSettings.drawGiraffePattern())
            drawGiraffePattern();
        else if (mSettings.drawSingletonPattern())
            drawSingletonPattern();
        else if (mSettings.drawHexPattern())
            drawHexPattern();
        else if (mSettings.drawIrregularHexPattern())
            drawIrregularHexPattern();
        else if (mSettings.drawGridPattern())
            drawGridPattern();
        else if (mSettings.drawWeirdPhylloPattern())
            drawWeirdPhylloPattern();
//        else if (mSettings.drawConcentricCirclePattern())
//            drawConcentricCirclePattern();
        else if (mSettings.drawPhylloPattern())
            drawPhylloPattern();

        if (mSettings.applyGradient())
            applyGradient();

        paused = false;
    }

    private void drawGiraffePattern() {
        for (int i = 0; i < mState.getCellCount(); i++) {
            if (myRandom() < mSettings.getStartOnPercent()) {
                mState.setCellState(i, true);
                mState.setTryToActivateNeighbors(i, true);
            } else {
                mState.setCellState(i, false);
                mState.setTryToActivateNeighbors(i, false);
            }
        }
    }

    private void drawSingletonPattern() {
        for (int i = 0; i < mState.getCellCount(); i++) {
            int j = mSettings.getXSize() / 2 + mSettings.getXSize() * (mSettings.getYSize()) / 2;
            mState.setCellState(j, true);
            mState.setTryToActivateNeighbors(j, true);
        }
    }

    private void drawHexPattern() {
        int c = 0;
        for (int xPos = 20; xPos < mSettings.getXSize() - 20; xPos += 20) {
            for (int yPos = 20; yPos < mSettings.getYSize() - 20; yPos += 20) {
                int offset = (++c % 2 == 0 ? 10 : 0);
                int j = (int) xPos + offset + (int) yPos * mSettings.getYSize();
                mState.setCellState(j, true);
                mState.setTryToActivateNeighbors(j, true);
            }
        }
    }

    private void drawTortoiseShellPattern() {

    }
    private void drawIrregularHexPattern() {
        int c = 0;
        for (int x = 20; x < mSettings.getXSize() - 20; x += 20) {
            for (int y = 20; y < mSettings.getYSize() - 20; y += 20) {
                int xPos = x;
                int yPos = y;
                int n = 3;
                if (Math.random() < 0.33) xPos -= n;
                else if (Math.random() > 0.67) xPos += n;
                if (Math.random() < 0.33) yPos -= n;
                else if (Math.random() > 0.67) yPos += n;

                int offset = (++c % 2 == 0 ? 10 : 0);
                int j = xPos + offset + yPos * mSettings.getYSize();
                mState.setCellState(j, true);
                mState.setTryToActivateNeighbors(j, true);
            }
        }
    }

    private void drawGridPattern() {
        for (int xPos = 20; xPos < mSettings.getXSize() - 20; xPos += 20) {
            for (int yPos = 20; yPos < mSettings.getYSize() - 20; yPos += 20) {
                int j = xPos + yPos * mSettings.getYSize();
                mState.setCellState(j, true);
                mState.setTryToActivateNeighbors(j, true);
            }
        }
    }

    private void drawWeirdPhylloPattern() {
        double n = 0; // ordering number of object
        double phi; // divergence phiAngle
        double r; // radius from origin to object center
        double xPos, yPos; // shape positions
        double phiAngle = 137.5; // phiAngle multiplied by n to calculate phi
        double C = 3; // scaling factor
        while (n < 1000) {
            phi = Math.toRadians(n * phiAngle);
            r = C * Math.sqrt(n);
            xPos = (300) + (r * Math.cos(phi));
            yPos = (300) + (r * Math.sin(phi));
            int j = (int) xPos * (int) yPos / 4;
            mState.setCellState(j, true);
            mState.setTryToActivateNeighbors(j, true);
            n++;
        }
    }

    private void drawPhylloPattern() {
        double n = 0; // ordering number of object
        double phi; // divergence phiAngle
        double r; // radius from origin to object center
        double xPos, yPos; // shape positions
        double phiAngle = 137.5; // phiAngle multiplied by n to calculate phi
        double C = 3; // scaling factor
        while (n < 600) {
            phi = Math.toRadians(n * phiAngle);
            r = C * Math.sqrt(n);
            xPos = (100) + (r * Math.cos(phi));
            yPos = (100) + (r * Math.sin(phi));
            int j = (int) xPos + (int) yPos * mSettings.getYSize();
            mState.setCellState(j, true);
            mState.setTryToActivateNeighbors(j, true);
            n++;
        }
    }


//    ///////////////////////
//    //
//    //CODE PLASTIC very cool
//    //
//    //http://www.codeplastic.com/2017/09/09/controlled-circle-packing-with-processing/
//    //https://www.youtube.com/watch?v=SSWudanJc7c&t=5s
//    //https://entagma.com/packing-the-torus/
//    //
//    //////////////////////
//
//
//    private void drawConcentricCirclePattern() {
////        Double[] radius = {0.0,0.1,0.2};
////        Integer[] numberOfDots = {1,10,20};
////        Double[] theta = new Double[3];
////
////        //get angles for each concentric circle
////        for (int i=0; i<3; i++) {
////            theta[i] = 2 * Math.PI/numberOfDots[i];
////        }
////
////        for (int i=0; i<3; i++) {
////            double radius = R[i];
////            for (int i=0; i<3; i++) {
////                double xPos = Math.round(R[i] * Math.cos(t[i]));
////                double yPos = Math.round(R[i] * Math.sin(t[i]));
////                int j = (int) xPos + (int) yPos * ySize;
////                cellState[j] = true;
////                tryToActivateNeighbors[j] = true;
////            }
////        }
//    }

    private void applyGradient() {
        List<Integer> cellsOn = new LinkedList<>();
        int xsize = mSettings.getXSize();
        int ysize = mSettings.getYSize();

        for (int y = 0; y < ysize; y++) {

            //count cells on in each row
            cellsOn.clear();
            ;
            for (int x = 0; x < xsize; x++) {
                int i = x + y * mSettings.getYSize();
                if (mState.getCellState(i))
                    cellsOn.add(i);
            }

            //determine the percentage of cells to turn off in each row
            double pct = 0.0;
            if (y < ysize * 0.10) pct = 0.0;
            else if (y < ysize * 0.15) pct = 30.0;
            else if (y < ysize * 0.20) pct = 70.0;
            else if (y < ysize * 0.30) pct = 80.0;
            else if (y < ysize * 0.35) pct = 95.0;
            else if (y < ysize * 0.40) pct = 97.0;
            else if (y < ysize * 0.50) pct = 98.0;
            else if (y < ysize * 0.60) pct = 96.0;
            else if (y < ysize * 0.70) pct = 80.0;
            else if (y < ysize * 0.80) pct = 70.0;
            else if (y < ysize * 0.90) pct = 30.0;
            else pct = ysize * 0.0;

            int numCellsToTurnOff = (int) Math.round(cellsOn.size() * (pct / 100.0));

            //turn off a percentage of cells randomly in each row
            Collections.shuffle(cellsOn);
            for (int c = 0; c < numCellsToTurnOff; c++) {
                int i = cellsOn.get(c);
                mState.setCellState(i ,false);
            }
        }
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
        boolean graphicsSizeChanged = getUserVars();
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