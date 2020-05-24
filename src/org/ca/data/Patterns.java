package org.ca.data;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Patterns {
    ModelSettings mSettings;
    ModelState mState;

    public Patterns(ModelSettings settings, ModelState state) {
        mSettings = settings;
        mState = state;
    }

    //deterministic number generator so we can see varied results with identical starting conditions but different settings
    private double myRandom() {
        return Math.random();
    }

    public void getPattern() {
        if (mSettings.getDrawGiraffePattern())
            drawGiraffePattern();
        else if (mSettings.getDrawSingletonPattern())
            drawSingletonPattern();
        else if (mSettings.getDrawHexPattern())
            drawHexPattern();
        else if (mSettings.getDrawIrregularHexPattern())
            drawIrregularHexPattern();
        else if (mSettings.getDrawGridPattern())
            drawGridPattern();
        else if (mSettings.getDrawWeirdPhylloPattern())
            drawWeirdPhylloPattern();
//        else if (mSettings.getDrawConcentricCirclePattern())
//            drawConcentricCirclePattern();
        else if (mSettings.getDrawPhylloPattern())
            drawPhylloPattern();

        if (mSettings.getApplyGradient())
            applyGradient();
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

}
