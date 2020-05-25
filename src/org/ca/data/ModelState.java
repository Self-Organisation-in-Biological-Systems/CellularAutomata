package org.ca.data;

public class ModelState {
    private int cellCount;
    private double[] cellA;//level of molecule concentration in each cell
    private double[] cellB;
    private double[] cellC;
    private int[] lifeTime;
    private boolean[] cellState; //true if on
    private double[] cellActivationDelay;
    private int[] cellX; //xy drawing position, if an array lookup is faster than calculating while drawing it
    private int[] cellY;
    private String[] cellColor;//to store fate of cell as a color if needed
    private int[][] cellNeighbor; //array of indices of neighboring adjacent cells
    private int[][] cellDiagNeighbor; //array of indices of neighboring diagonal cells
    private boolean[] tryToActivateNeighbors; //true for only the first time it is turned on, then set to false

    public ModelState(ModelSettings modelSettings) {
        reset(modelSettings);
    }

    public void reset(ModelSettings modelSettings) {
        cellCount = modelSettings.getXSize() * modelSettings.getYSize();
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
            cellX[i] = i % modelSettings.getXSize();
            cellY[i] = (int) Math.floor(i / modelSettings.getYSize());
            cellA[i] = modelSettings.getStartA();
            cellB[i] = 0;
            cellC[i] = 0;
            lifeTime[i] = modelSettings.getMaxLifeTime();
            cellActivationDelay[i] = modelSettings.getActivationDelay();
        }

        recalculateNeighbors(modelSettings);
    }

    private void recalculateNeighbors(ModelSettings modelSettings) {
        //recalculate neighbors, but only if size changed (fixme add size change detection if calculating neighbors is lengthy)
        cellNeighbor = new int[cellCount][4];
        cellDiagNeighbor = new int[cellCount][4];
        int xSize = modelSettings.getXSize();
        int ySize = modelSettings.getYSize();
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
    }

    public int getLifeTime(int i) {
        return lifeTime[i];
    }

    public void setLifeTime(int i, int amount) { lifeTime[i] = amount; }

    public void decrementLifeTime(int i) {
        lifeTime[i]--;
    }

    public int getCellCount() {
        return cellCount;
    }

    public boolean getCellState(int i) {
        return cellState[i];
    }

    public void setCellState(int i, boolean state) {
        cellState[i] = state;
    }

    public double getCellA(int i) {
        return cellA[i];
    }

    public void setCellA(int i, double amount) { cellA[i] = amount; }

    public double getCellB(int i) {
        return cellB[i];
    }

    public void setCellB(int i, double amount) { cellB[i] = amount; }

    public void incrementCellA(int i, double amount) {
        cellA[i] += amount;
    }

    public void decrementCellA(int i, double amount) {
        cellA[i] -= amount;
    }

    public void incrementCellB(int i, double amount) {
        cellB[i] += amount;
    }

    public void decrementCellB(int i, double amount) {
        cellB[i] -= amount;
    }

    public void multiplyCellB(int i, double amount) {
        cellB[i] *= amount;
    }

    public int[] getCellNeighbor(int i) {
        return cellNeighbor[i];
    }

    public int[] getCellDiagNeighbor(int i) {
        return cellDiagNeighbor[i];
    }

    public boolean tryToActivateNeighbors(int i) {
        return tryToActivateNeighbors[i];
    }

    public void setTryToActivateNeighbors(int i, boolean activate) {
        tryToActivateNeighbors[i] = activate;
    }

    public double getCellActivationDelay(int i) {
        return cellActivationDelay[i];
    }

    public void setCellActivationDelay(int i, double activationDelay) {
        cellActivationDelay[i] = activationDelay;
    }

    public void decrementCellActivationDelay(int i) {
        cellActivationDelay[i]--;
    }

    public int getCellX(int i) {
        return cellX[i];
    }

    public void setCellX(int i, int value) {
        cellX[i] = value;
    }

    public int getCellY(int i) {
        return cellY[i];
    }

    public void setCellY(int i, int value) {
        cellY[i] = value;
    }

    public String getCellColor(int i) {
        return cellColor[i];
    }

    public void setCellColor(int i, String color) {
        cellColor[i] = color;
    }
}
