package org.ca;

//http://serpwidgets.com/giraffes/giraffealgorithm.htm

import org.ca.data.ModelSettings;
import org.ca.data.ModelState;
import org.ca.panels.ControlFrame;

import java.util.*;

public class Tick {
    private ModelState mState;
    private ModelSettings mSettings;
    private ControlFrame mControlFrame;
    private GraphicFrame mGraphic;
    private Timer mMainTimer;
    private Timer mCheckThresholdTimer;

    public Tick(ModelState modelState, ModelSettings settings, ControlFrame controlFrame, GraphicFrame graphic) {
        mState = modelState;
        mSettings = settings;
        mControlFrame = controlFrame;
        mGraphic = graphic;
    }

    // FIXME: add the capability to have a user-defined list of starting "on" cells so that a specific pattern can be replicated//add histogram view
    //add a-replenish rate as an option

    private int xSize = 200;
    private int ySize = 200;
    private double startA = 1.0;
    private double aReplenish = 0.0;
    private double bDecay = 0;
    private double startOnPercent = 0.001;
    private double DiffusionRate = 1.0;
    private double bDiffusionRate = 0;
    private double reactionRate = 1.0;
    private double activationRate = 1;//chance of an active cell turning on its neighbors
    private double activationThreshold = 0.25; //min A value that a cell must have in order to be switched on, otherwise it will stay off
    private double activationDelay = 10.0;
    private int drawEvery = 10;
    private int maxLifeTime = 1000;
    private int shutoffAThreshold = -1; //thee values will keep them from shutting off from thresholds
    private int shutoffBThreshold = 10;
    private boolean showCellStates = false;

    //fixme: add U replenish and V decay options just for the fun of it
    //fixme: once this works exactly like a regular UV R-D system, also add migrating activation to try and generate corn pattens
    //fixme: add "load in a starting picture" to set starting UV values
    //fixme: add "load in another starting picture to set usable cells" (user can specify a non-rectangular picture)

    private boolean running = false;
    private boolean paused = false;

//    private int cellCount = xSize * ySize;//total number of cells being used in the simulation
//    private double[] cellA;//level of molecule concentration in each cell
//    private double[] cellB;
//    private double[] cellC;
//    private int[] lifeTime;
//    private boolean[] cellState; //true if on
//    private double[] cellActivationDelay;
//    private int[] cellX; //xy drawing position, if an array lookup is faster than calculating while drawing it
//    int[] cellY;
//    private String[] cellColor;//to store fate of cell as a color if needed
//    private int[][] cellNeighbor; //array of indices of neighboring adjacent cells
//    private int[][] cellDiagNeighbor; //array of indices of neighboring diagonal cells
//    private boolean[] tryToActivateNeighbors; //true for only the first time it is turned on, then set to false

    private int isRunning = 0;
    private int tickCount;//how many cycles have elapsed
    private boolean useGiraffeColors = true;
    private boolean drawScaled = false;
    private int histoBanding = 0;
    private double pigmentThreshold;

    private boolean getUserVars() {
        boolean graphicsSizeChanged = false;
        int oldXSize = xSize;
        int oldYSize = ySize;
        xSize = mControlFrame.getXSize();
        ySize = mControlFrame.getYSize();
        if(xSize != oldXSize || ySize != oldYSize)
            graphicsSizeChanged = true;
        histoBanding = 0;
        aReplenish = mControlFrame.getAReplenish();
        bDecay = 1.0 - mControlFrame.getBDecayRate();
        DiffusionRate = mControlFrame.getDiffusionRate();
        reactionRate = mControlFrame.getReactionRate();
        activationRate = mControlFrame.getActivationRate();
        activationThreshold = mControlFrame.getActivationThreshold();
        activationDelay = mControlFrame.getActivationDelay();
        drawEvery = mControlFrame.getDawEveryNthCycle();
        bDiffusionRate = mControlFrame.getBDiffusionRate();
        useGiraffeColors = mControlFrame.drawInGiraffeColors();
        drawScaled = mControlFrame.drawScaledToMax();
        pigmentThreshold = mControlFrame.getPigmentThreshold();
        maxLifeTime = mControlFrame.getMaxLifeTime();
        shutoffAThreshold = mControlFrame.getShutoffAThreshold();
        shutoffBThreshold = mControlFrame.getShutoffBThreshold();
        showCellStates = mControlFrame.showCellStates();
        startOnPercent = mControlFrame.getStartOnPercent();
        startA = mControlFrame.getStartA();

        return graphicsSizeChanged;
    }

    //deterministic number generator so we can see varied results with identical starting conditions but different settings
    private double myRandom() {
        return Math.random();
    }

    public void init() {
        getUserVars();

//        //get number of cells needed
//        cellCount = xSize * ySize;
//        //empty out the arrays and randomize their contents
//        cellA = new double[cellCount];
//        cellB = new double[cellCount];
//        cellC = new double[cellCount];
//        cellX = new int[cellCount];
//        cellY = new int[cellCount];
//        lifeTime = new int[cellCount];
//        cellState = new boolean[cellCount];
//        cellColor = new String[cellCount];
//        tryToActivateNeighbors = new boolean[cellCount];
//        cellActivationDelay = new double[cellCount];
//
//        for (int i = 0; i < mState.getCellCount(); i++) {
//            cellX[i] = i % xSize;
//            cellY[i] = (int) Math.floor(i / ySize);
//            cellA[i] = startA;
//            cellB[i] = 0;
//            cellC[i] = 0;
//            lifeTime[i] = maxLifeTime;
//            cellActivationDelay[i] = activationDelay;
//        }

        if (mControlFrame.drawGiraffePattern())
            drawGiraffePattern();
//        else if (mControlFrame.drawSingletonPattern())
//            drawSingletonPattern();
//        else if (mControlFrame.drawHexPattern())
//            drawHexPattern();
//        else if (mControlFrame.drawIrregularHexPattern())
//            drawIrregularHexPattern();
//        else if (mControlFrame.drawGridPattern())
//            drawGridPattern();
//        else if (mControlFrame.drawWeirdPhylloPattern())
//            drawWeirdPhylloPattern();
//        else if (mControlFrame.drawConcentricCirclePattern())
//            drawConcentricCirclePattern();
//        else if (mControlFrame.drawPhylloPattern())
//            drawPhylloPattern();

        if (mControlFrame.applyGradient())
            applyGradient();

//        recalculateNeighbors();

        paused = false;
    }

    private void drawGiraffePattern() {
        for (int i = 0; i < mState.getCellCount(); i++) {
            if (myRandom() < startOnPercent) {
                mState.setCellState(i, true);
                mState.setTryToActivateNeighbors(i, true);
            } else {
                mState.setCellState(i, false);
                mState.setTryToActivateNeighbors(i, false);
            }
        }
    }

//    private void drawSingletonPattern() {
//        for (int i = 0; i < cellCount; i++) {
//            int j = xSize / 2 + xSize * (ySize) / 2;
//            cellState[j] = true;
//            tryToActivateNeighbors[j] = true;
//        }
//    }
//
//    private void drawHexPattern() {
//        int c = 0;
//        for (int xPos = 20; xPos < xSize - 20; xPos += 20) {
//            for (int yPos = 20; yPos < ySize - 20; yPos += 20) {
//                int offset = (++c % 2 == 0 ? 10 : 0);
//                int j = (int) xPos + offset + (int) yPos * ySize;
//                cellState[j] = true;
//                tryToActivateNeighbors[j] = true;
//            }
//        }
//    }
//
//    private void drawTortoiseShellPattern() {
//
//    }
//    private void drawIrregularHexPattern() {
//        int c = 0;
//        for (int x = 20; x < xSize - 20; x += 20) {
//            for (int y = 20; y < ySize - 20; y += 20) {
//                int xPos = x;
//                int yPos = y;
//                int n = 3;
//                if (Math.random() < 0.33) xPos -= n;
//                else if (Math.random() > 0.67) xPos += n;
//                if (Math.random() < 0.33) yPos -= n;
//                else if (Math.random() > 0.67) yPos += n;
//
//                int offset = (++c % 2 == 0 ? 10 : 0);
//                int j = xPos + offset + yPos * ySize;
//                cellState[j] = true;
//                tryToActivateNeighbors[j] = true;
//            }
//        }
//    }
//
//    private void drawGridPattern() {
//        for (int xPos = 20; xPos < xSize - 20; xPos += 20) {
//            for (int yPos = 20; yPos < ySize - 20; yPos += 20) {
//                int j = xPos + yPos * ySize;
//                cellState[j] = true;
//                tryToActivateNeighbors[j] = true;
//            }
//        }
//    }
//
//    private void drawWeirdPhylloPattern() {
//        double n = 0; // ordering number of object
//        double phi; // divergence phiAngle
//        double r; // radius from origin to object center
//        double xPos, yPos; // shape positions
//        double phiAngle = 137.5; // phiAngle multiplied by n to calculate phi
//        double C = 3; // scaling factor
//        while (n < 1000) {
//            phi = Math.toRadians(n * phiAngle);
//            r = C * Math.sqrt(n);
//            xPos = (300) + (r * Math.cos(phi));
//            yPos = (300) + (r * Math.sin(phi));
//            int j = (int) xPos * (int) yPos / 4;
//            cellState[j] = true;
//            tryToActivateNeighbors[j] = true;
//            n++;
//        }
//    }
//
//    private void drawPhylloPattern() {
//        double n = 0; // ordering number of object
//        double phi; // divergence phiAngle
//        double r; // radius from origin to object center
//        double xPos, yPos; // shape positions
//        double phiAngle = 137.5; // phiAngle multiplied by n to calculate phi
//        double C = 3; // scaling factor
//        while (n < 600) {
//            phi = Math.toRadians(n * phiAngle);
//            r = C * Math.sqrt(n);
//            xPos = (100) + (r * Math.cos(phi));
//            yPos = (100) + (r * Math.sin(phi));
//            int j = (int) xPos + (int) yPos * ySize;
//            cellState[j] = true;
//            tryToActivateNeighbors[j] = true;
//            n++;
//        }
//    }
//
//
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
        int xsize = mControlFrame.getXSize();
        int ysize = mControlFrame.getYSize();

        for (int y = 0; y < ysize; y++) {

            //count cells on in each row
            cellsOn.clear();
            ;
            for (int x = 0; x < xsize; x++) {
                int i = x + y * ySize;
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

//    private void applyLinearGradient(double gradient) {
//        for(int i=0; i<cellCount; i++) {
//            if(cellState[i]) {
//                int xPos = i % xSize;
//                int yPos = i / ySize;
//                //int k = (int) xPos + (int) yPos * ySize;
//                yPos += (int)(yPos * gradient);
//                int j = xPos + yPos * ySize;
//                cellState[i] = false;
//                tryToActivateNeighbors[i] = false;
//                if (j < cellCount) {
//                    cellState[j] = true;
//                    tryToActivateNeighbors[j] = true;
//                }
//            }
//        }
//    }

//    private void applyLinearGradient() {
//        List<Integer> cellsOn = new LinkedList<>();
//        int xsize = mControlFrame.getXSize();
//        int ysize = mControlFrame.getYSize();
//
//        for(int y = ysize-1; y >= 0; y--) {
//
//            //count cells on in each row
//            cellsOn.clear();;
//            for (int x=0; x<xsize; x++) {
//                int i = x + y * ySize;
//                if(cellState[i])
//                    cellsOn.add(i);
//            }
//
//            //determine the percentage of cells to turn off in each row
//            //double pct = (double)(ysize - y) / ysize; //linear
//            double pct = 100-(double)Math.pow((ysize - y), 1.65) / ysize; //exponential
//            for(int p=0; p<Math.round(pct); p++)
//                System.out.print(" ");
////            if(pct > 1.0)
////                pct = 1.0;
//            int numCellsToTurnOff = (int)Math.round(cellsOn.size() * (pct/100.0));
//            System.out.println(String.format("pct=%.1f numCellsToTurnOff=%d", pct, numCellsToTurnOff));
//
//            //turn off a percentage of cells randomly in each row
//            Collections.shuffle(cellsOn);
//            for (int c=0; c<numCellsToTurnOff; c++) {
//                int i = cellsOn.get(c);
//                cellState[i] = false;
//            }
//        }
//    }


//    private void recalculateNeighbors() {
//        //recalculate neighbors, but only if size changed (fixme add size change detection if calculating neighbors is lengthy)
//        cellNeighbor = new int[cellCount][4];
//        cellDiagNeighbor = new int[cellCount][4];
//        for (int x = 1; x < (xSize - 1); x++) { //all the interior cells have all neighbors
//            for (int y = 1; y < (ySize - 1); y++) {
//                int i = (y * xSize) + x;
//                cellNeighbor[i][0] = i - 1;
//                cellNeighbor[i][1] = i + 1;
//                cellNeighbor[i][2] = i - xSize;
//                cellNeighbor[i][3] = i + xSize;
//                cellDiagNeighbor[i][0] = i - (xSize + 1);
//                cellDiagNeighbor[i][1] = i - (xSize - 1);
//                cellDiagNeighbor[i][2] = i + (xSize + 1);
//                cellDiagNeighbor[i][3] = i + (xSize - 1);
//            }
//        }
//        //optimize for special cases
//        //cells on the top row don't have neighbors above
//        for (int i = 1; i < (xSize - 1); i++) {
//            cellNeighbor[i][0] = i - 1;
//            cellNeighbor[i][1] = i + 1;
//            cellNeighbor[i][2] = i + xSize;
//            cellDiagNeighbor[i][0] = i + (xSize + 1);
//            cellDiagNeighbor[i][1] = i + (xSize - 1);
//        }
//        //cells on the bottom row don't have neighbors below
//        int y = (xSize * (ySize - 1));
//        for (int i = 1; i < (xSize - 1); i++) {
//            int z = i + y;
//            cellNeighbor[z][0] = z - 1;
//            cellNeighbor[z][1] = z + 1;
//            cellNeighbor[z][2] = z - xSize;
//            cellDiagNeighbor[z][0] = z - (xSize + 1);
//            cellDiagNeighbor[z][1] = z - (xSize - 1);
//        }
//        //cells on the left row don't have neighbors to the left
//        for (int i = 1; i < (ySize - 1); i++) {
//            int z = (i * xSize);
//            cellNeighbor[z][0] = z + 1;
//            cellNeighbor[z][1] = z - xSize;
//            cellNeighbor[z][2] = z + xSize;
//            cellDiagNeighbor[z][0] = (z - xSize) + 1;
//            cellDiagNeighbor[z][1] = (z + xSize) + 1;
//        }
//        //cells on the right row don't have neighbors to the right
//        for (int i = 1; i < (ySize - 1); i++) {
//            int z = ((i + 1) * xSize) - 1;
//            cellNeighbor[z][0] = z - 1;
//            cellNeighbor[z][1] = z - xSize;
//            cellNeighbor[z][2] = z + xSize;
//            cellDiagNeighbor[z][0] = z - (xSize + 1);
//            cellDiagNeighbor[z][1] = z + (xSize - 1);
//        }
//        //the four corners are also special cases
//        //top left
//        cellNeighbor[0][0] = 1;
//        cellNeighbor[0][1] = xSize;
//        cellDiagNeighbor[0][0] = xSize + 1;
//
//        //top right
//        cellNeighbor[xSize - 1][0] = xSize - 2;
//        cellNeighbor[xSize - 1][1] = (xSize * 2) - 2;
//        cellDiagNeighbor[xSize - 1][0] = (xSize * 2) - 1;
//
//        //bottom left
//        int z = (xSize * (ySize - 1));
//        cellNeighbor[z][0] = z + 1;
//        cellNeighbor[z][1] = z - xSize;
//        cellDiagNeighbor[z][0] = z - (xSize - 1);
//
//        //bottom right
//        z = cellCount - 1;
//        cellNeighbor[z][0] = z - 1;
//        cellNeighbor[z][1] = z - xSize;
//        cellDiagNeighbor[z][0] = z - (xSize + 1);
//    }

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
            double diffRate = DiffusionRate * 0.5; //allow user to use range of 0-1 but optimize calc speed
            double diffRateDiag = DiffusionRate * 0.5 * 0.707;
            double bDiffRate = bDiffusionRate * 0.5; //for B molecule if needed
            double bDiffRateDiag = bDiffusionRate * 0.5 * 0.707;
            double activationRateDiag = activationRate * 0.707;
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


                if (bDiffusionRate > 0) {
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
                            if (Math.random() < activationRate) {
                                for (int i = 0; i < mState.getCellNeighbor(cNum).length; i++) {
                                    int nNum = mState.getCellNeighbor(cNum)[i];
                                    if (!mState.getCellState(nNum) && mState.getCellA(nNum) >= activationThreshold) {//if this neighbor is off, turn it on if it can be
                                        mState.setTryToActivateNeighbors(nNum, true);
                                        mState.setCellState(nNum, true);
                                        mState.setCellActivationDelay(nNum, activationDelay);
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
                                    if (!mState.getCellState(nNum) && mState.getCellA(nNum) >= activationThreshold) {//if this neighbor is off, turn it on if it can be
                                        mState.setTryToActivateNeighbors(nNum, true);
                                        mState.setCellState(nNum, true);
                                        mState.setCellActivationDelay(nNum, activationDelay);
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
                    if ((mState.getCellA(cNum) < shutoffAThreshold) || (mState.getCellB(cNum) > shutoffBThreshold)) {
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
                    double amt = mState.getCellA(cNum) * mState.getCellA(cNum) * reactionRate;
                    mState.decrementCellA(cNum, amt);//convert A into B
                    mState.incrementCellB(cNum, amt);
                }

                //fixme: make cells shut off if their A level is below a certain amount or if B rises above a certain amount.

                //replenish A if needed:
                if ((aReplenish > 0.0) && (mState.getCellA(cNum) < 1.0))
                    mState.incrementCellA(cNum, aReplenish);

                if ((aReplenish < 1.0) && (mState.getCellB(cNum) > 0))
                    mState.multiplyCellB(cNum, bDecay);
            }

//            for(int col=0; col<ySize; col++) {
//                int row = 10;
//                int i = row + col * ySize;
//                System.out.print(String.format("%2d,%-2d %s ", (int)(cellA[i]*10), (int)(cellB[i]*10), (cellState[i]?"*":" ")));
//            }
//            System.out.println();

            if (tickCount % drawEvery == 0 || !doDiffusion) {
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
        pigmentThreshold = mControlFrame.getPigmentThreshold();
        if (lastThreshold != pigmentThreshold) {
            lastThreshold = pigmentThreshold;
        }
        useGiraffeColors = mControlFrame.drawInGiraffeColors();
        drawScaled = mControlFrame.drawScaledToMax();
        showCellStates = mControlFrame.showCellStates();
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
        mGraphic.mainDraw(mState, mSettings);
    }

//    public int getCellCount() {
//        return mState.getCellCount();
//    }
//
//    public boolean getCellState(int i) {
//        return cellState[i];
//    }
//
//    public boolean getTryToActivateNeighbors(int i) {
//        return tryToActivateNeighbors[i];
//    }
//
//    public int getTickCount() {
//        return tickCount;
//    }
//
//    public double getStartA() {
//        return startA;
//    }
//
//    public double getCellA(int i) {
//        return cellA[i];
//    }
//
//    public double getCellB(int i) {
//        return cellB[i];
//    }
}