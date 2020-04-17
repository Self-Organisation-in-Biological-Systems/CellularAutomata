package org.ca;

//http://serpwidgets.com/giraffes/giraffealgorithm.htm

import java.util.Timer;
import java.util.TimerTask;

public class Tick {
    private ControlFrame mControlFrame;
    private GraphicFrame mGraphic;
    private Timer mMainTimer;
    private Timer mCheckThresholdTimer;

    public Tick(ControlFrame controlFrame, GraphicFrame graphic) {
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
    private int cellCount = xSize * ySize;//total number of cells being used in the simulation
    private double[] cellA;//level of molecule concentration in each cell
    private double[] cellB;
    private double[] cellC;
    private int[] lifeTime;
    private boolean[] cellState; //true if on
    private double[] cellActivationDelay;
    private int[] cellX; //xy drawing position, if an array lookup is faster than calculating while drawing it
    int[] cellY;
    private String[] cellColor;//to store fate of cell as a color if needed
    private int[][] cellNeighbor; //array of indices of neighboring adjacent cells
    private int[][] cellDiagNeighbor; //array of indices of neighboring diagonal cells
    private boolean[] tryToActivateNeighbors; //true for only the first time it is turned on, then set to false
    private int isRunning = 0;
    private int tickCount;//how many cycles have elapsed
    private boolean useGiraffeColors = true;
    private boolean drawScaled = false;
    private int histoBanding = 0;
    private int pigmentThreshold;

    private void getUserVars() {
        histoBanding = 0;
        xSize = mControlFrame.getXSize();
        ySize = mControlFrame.getYSize();
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
    }

    //deterministic number generator so we can see varied results with identical starting conditions but different settings
    private double myRandom() {
        return Math.random();
    }

    public void init() {
        getUserVars();

        //get number of cells needed
        cellCount = xSize * ySize;
        //empty out the arrays and randomize their contents
        cellA = new double[cellCount];
        cellB = new double[cellCount];
        cellC = new double[cellCount];
        cellX = new int[cellCount];
        cellY = new int[cellCount];
        lifeTime = new int[cellCount];
        cellState = new boolean[cellCount];
        cellColor = new String[cellCount];
        tryToActivateNeighbors = new boolean[cellCount];
        cellActivationDelay = new double[cellCount];

        for (int i = 0; i < cellCount; i++) {
            cellX[i] = i % xSize;
            cellY[i] = (int)Math.floor(i / ySize);
            cellA[i] = startA;
            cellB[i] = 0;
            cellC[i] = 0;
            lifeTime[i] = maxLifeTime;
            cellActivationDelay[i] = activationDelay;

            if (myRandom() < startOnPercent) {
                cellState[i] = true;
                tryToActivateNeighbors[i] = true;
            } else {
                cellState[i] = false;
                tryToActivateNeighbors[i] = false;
            }

            cellColor[i] = "#000000";
        }

        //recalculate neighbors, but only if size changed (fixme add size change detection if calculating neighbors is lengthy)
        cellNeighbor = new int[cellCount][4];
        cellDiagNeighbor = new int[cellCount][4];
        for (int x = 1; x < (xSize - 1); x++) { //all the interior cells have all neighbors
            for (int y = 1; y < (ySize - 1); y++) {
                int i = (y * xSize) + x;
                cellNeighbor[i][0] = i - 1;
                cellNeighbor[i][1] = i + 1;
                cellNeighbor[i][2] = i - xSize;
                cellNeighbor[i][3] = i + xSize;
                cellDiagNeighbor[i][0] = i - (xSize + 1);
                cellDiagNeighbor[i][1] = i - (xSize - 1);
                cellDiagNeighbor[i][2] = i + (xSize + 1);
                cellDiagNeighbor[i][3] = i + (xSize - 1);
            }
        }
        //optimize for special cases
        //cells on the top row don't have neighbors above
        for (int i = 1; i < (xSize - 1); i++) {
            cellNeighbor[i][0] = i - 1;
            cellNeighbor[i][1] = i + 1;
            cellNeighbor[i][2] = i + xSize;
            cellDiagNeighbor[i][0] = i + (xSize + 1);
            cellDiagNeighbor[i][1] = i + (xSize - 1);
        }
        //cells on the bottom row don't have neighbors below
        int y = (xSize * (ySize - 1));
        for (int i = 1; i < (xSize - 1); i++) {
            int z = i + y;
            cellNeighbor[z][0] = z - 1;
            cellNeighbor[z][1] = z + 1;
            cellNeighbor[z][2] = z - xSize;
            cellDiagNeighbor[z][0] = z - (xSize + 1);
            cellDiagNeighbor[z][1] = z - (xSize - 1);
        }
        //cells on the left row don't have neighbors to the left
        for (int i = 1; i < (ySize - 1); i++) {
            int z = (i * xSize);
            cellNeighbor[z][0] = z + 1;
            cellNeighbor[z][1] = z - xSize;
            cellNeighbor[z][2] = z + xSize;
            cellDiagNeighbor[z][0] = (z - xSize) + 1;
            cellDiagNeighbor[z][1] = (z + xSize) + 1;
        }
        //cells on the right row don't have neighbors to the right
        for (int i = 1; i < (ySize - 1); i++) {
            int z = ((i + 1) * xSize) - 1;
            cellNeighbor[z][0] = z - 1;
            cellNeighbor[z][1] = z - xSize;
            cellNeighbor[z][2] = z + xSize;
            cellDiagNeighbor[z][0] = z - (xSize + 1);
            cellDiagNeighbor[z][1] = z + (xSize - 1);
        }
        //the four corners are also special cases
        //top left
        cellNeighbor[0][0] = 1;
        cellNeighbor[0][1] = xSize;
        cellDiagNeighbor[0][0] = xSize + 1;

        //top right
        cellNeighbor[xSize - 1][0] = xSize - 2;
        cellNeighbor[xSize - 1][1] = (xSize * 2) - 2;
        cellDiagNeighbor[xSize - 1][0] = (xSize * 2) - 1;

        //bottom left
        int z = (xSize * (ySize - 1));
        cellNeighbor[z][0] = z + 1;
        cellNeighbor[z][1] = z - xSize;
        cellDiagNeighbor[z][0] = z - (xSize - 1);

        //bottom right
        z = cellCount - 1;
        cellNeighbor[z][0] = z - 1;
        cellNeighbor[z][1] = z - xSize;
        cellDiagNeighbor[z][0] = z - (xSize + 1);

        paused = false;
    }

    private void resetTimers() {
        mMainTimer = new Timer();
        mMainTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() { tick(); }
        },0,10);

        mCheckThresholdTimer = new Timer();
        mCheckThresholdTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() { checkThreshold(); }
        },0,500);
    }

    public void start() {
        init();
        resetTimers();
        tickCount = 0;
        running = true;
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
            resetTimers();
            paused = false;
        }
    }

    private void tick() {
        if(paused)
            return;

        try {
            System.out.println("tick " + tickCount++);
            getUserVars();//fetches values so they can be changed as it's running
            double diffRate = DiffusionRate * 0.5; //allow user to use range of 0-1 but optimize calc speed
            double diffRateDiag = DiffusionRate * 0.5 * 0.707;
            double bDiffRate = bDiffusionRate * 0.5; //for B molecule if needed
            double bDiffRateDiag = bDiffusionRate * 0.5 * 0.707;
            double activationRateDiag = activationRate * 0.707;
            for (int t = 0; t < cellCount; t++) {
                //pick a cell
                int cNum = (int) Math.floor(Math.random() * cellCount);
                //diffuse A
                int totalNeighbors = cellNeighbor[cNum].length + cellDiagNeighbor[cNum].length;
                double dSelf = (cellA[cNum]) / totalNeighbors;
                for (int i = 0; i < cellDiagNeighbor[cNum].length; i++) {
                    int nNum = cellDiagNeighbor[cNum][i];
                    double dN = (cellA[nNum]) / totalNeighbors;//get diffusable value in neighbor
                    double swap = (dSelf - dN) * diffRateDiag; //determine amount to be exchanged
                    cellA[cNum] -= swap;//exchange it
                    cellA[nNum] += swap;
                }
                for (int i = 0; i < cellNeighbor[cNum].length; i++) {
                    int nNum = cellNeighbor[cNum][i];
                    double dN = (cellA[nNum]) / totalNeighbors;//get diffusable value in neighbor
                    double swap = (dSelf - dN) * diffRate; //determine amount to be exchanged
                    cellA[cNum] -= swap;//exchange it
                    cellA[nNum] += swap;
                }


                if (bDiffusionRate > 0) {
                    //diffuse molecule B at its own rate if necessary
                    dSelf = (cellB[cNum]) / totalNeighbors;
                    for (int i = 0; i < cellNeighbor[cNum].length; i++) {
                        int nNum = cellNeighbor[cNum][i];
                        double dN = (cellB[nNum]) / totalNeighbors;//get diffusable value in neighbor
                        double swap = (dSelf - dN) * bDiffRate; //determine amount to be exchanged
                        cellB[cNum] -= swap;//exchange it
                        cellB[nNum] += swap;
                    }
                    for (int i = 0; i < cellDiagNeighbor[cNum].length; i++) {
                        int nNum = cellDiagNeighbor[cNum][i];
                        double dN = (cellB[nNum]) / totalNeighbors;//get diffusable value in neighbor
                        double swap = (dSelf - dN) * bDiffRateDiag; //determine amount to be exchanged
                        cellB[cNum] -= swap;//exchange it
                        cellB[nNum] += swap;
                    }
                }

                if (cellState[cNum]) {
                    //if on, turn on any neighbors that are off
                    if (tryToActivateNeighbors[cNum]) {
                        if (cellActivationDelay[cNum] > 0) {
                            cellActivationDelay[cNum]--;
                        } else {
                            if (Math.random() < activationRate) {
                                for (int i = 0; i < cellNeighbor[cNum].length; i++) {
                                    int nNum = cellNeighbor[cNum][i];
                                    if (!cellState[nNum] && cellA[nNum] >= activationThreshold) {//if this neighbor is off, turn it on if it can be
                                        tryToActivateNeighbors[nNum] = true;
                                        cellState[nNum] = true;
                                        cellActivationDelay[nNum] = activationDelay;
                                        //lifeTime[nNum]=maxLifeTime;
                                    }
                                }
                                tryToActivateNeighbors[cNum] = false;
                                for (int i = 0; i < cellNeighbor[cNum].length; i++) { //if any inactivated neighbors remain, flag this cell to try and activate them again
                                    if (!cellState[cellNeighbor[cNum][i]])
                                        tryToActivateNeighbors[cNum] = true;
                                }
                            }
                            if (Math.random() < activationRateDiag) {
                                for (int i = 0; i < cellDiagNeighbor[cNum].length; i++) {
                                    int nNum = cellDiagNeighbor[cNum][i];
                                    if (!cellState[nNum] && cellA[nNum] >= activationThreshold) {//if this neighbor is off, turn it on if it can be
                                        tryToActivateNeighbors[nNum] = true;
                                        cellState[nNum] = true;
                                        cellActivationDelay[nNum] = activationDelay;
                                        //lifeTime[nNum]=maxLifeTime;
                                    }
                                }
                                for (int i = 0; i < cellDiagNeighbor[cNum].length; i++) { //if any inactivated neighbors remain, flag this cell to try and activate them again
                                    if (!cellState[cellDiagNeighbor[cNum][i]])
                                        tryToActivateNeighbors[cNum] = true;
                                }

                            }
                        }
                    }//try to activate neighbors

                    //shut off cell if it passes concentration thresholds or (fixme) if it has been on for a certain amount of time?
                    if ((cellA[cNum] < shutoffAThreshold) || (cellB[cNum] > shutoffBThreshold)) {
                        cellState[cNum] = false;
                        tryToActivateNeighbors[cNum] = false;
                    } else {
                        lifeTime[cNum]--;
                        if (lifeTime[cNum] < 0)
                            cellState[cNum] = false;
                    }
                }//cell state on

                //react A into B if present
                if (cellState[cNum] && cellA[cNum] > 0) {
                    double amt = cellA[cNum] * cellA[cNum] * reactionRate;
                    cellA[cNum] -= amt;//convert A into B
                    cellB[cNum] += amt;
                }

                //fixme: make cells shut off if their A level is below a certain amount or if B rises above a certain amount.

                //replenish A if needed:
                if ((aReplenish > 0.0) && (cellA[cNum] < 1.0))
                    cellA[cNum] += aReplenish;

                if ((aReplenish < 1.0) && (cellB[cNum] > 0))
                    cellB[cNum] *= bDecay;
            }

            if (tickCount % drawEvery == 0) {
                mainDraw();
                //also check to see if all cells are off, if not a single cell is on, the simulation is over
                boolean simEnded = true;
                for (int i = 0; i < cellCount; i++) {
                    if (cellState[i]) {
                        simEnded = false;
                        break;
                    }
                }
                if (simEnded) {
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int lastThreshold = 0;

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
        for (int t = 0; t < cellCount; t++) {
            int cNum = (int)Math.floor(Math.random() * cellCount);//pick a cell
            int totalNeighbors = cellNeighbor[cNum].length + cellDiagNeighbor[cNum].length;
            double dSelf = (cellB[cNum]) / totalNeighbors; //TODO dSelf might be global??

            for (int i = 0; i < cellNeighbor[cNum].length; i++) {
                int nNum = cellNeighbor[cNum][i];
                double dN = (cellB[nNum]) / totalNeighbors;//get diffusable value in neighbor
                double swap = (dSelf - dN) * dRate; //determine amount to be exchanged
                cellB[cNum] -= swap;//exchange it
                cellB[nNum] += swap;
            }
            for (int i = 0; i < cellDiagNeighbor[cNum].length; i++) {
                int nNum = cellDiagNeighbor[cNum][i];
                double dN = (cellB[nNum]) / totalNeighbors;//get diffusable value in neighbor
                double swap = (dSelf - dN) * dRateDiag; //determine amount to be exchanged
                cellB[cNum] -= swap;//exchange it
                cellB[nNum] += swap;
            }
        }
        mainDraw();
    }

    public void mainDraw() {
        mGraphic.mainDraw(this);
    }

    public int getCellCount() {
        return cellCount;
    }

    public boolean getCellState(int i) {
        return cellState[i];
    }

    public boolean getTryToActivateNeighbors(int i) {
        return tryToActivateNeighbors[i];
    }

    public int getTickCount() {
        return tickCount;
    }

    public double getStartA() {
        return startA;
    }

    public double getCellA(int i) {
        return cellA[i];
    }

    public double getCellB(int i) {
        return cellB[i];
    }
}