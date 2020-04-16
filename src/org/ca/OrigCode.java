/*


package com.ca;


import javax.swing.*;
import java.awt.*;

import static javax.swing.GroupLayout.Alignment.*;

//http://serpwidgets.com/giraffes/giraffealgorithm.htm

//TODO myRandom - maybe ok
//TODO setTimeout - maybe ok in code, need in getVars
//TODO document.get, put
//TODO draw functions

public class OrigCode {

    public static void main(String[] args) {
        OrigCode m = new OrigCode();
        m.initUI();
    }

    // FIXME: add the capability to have a user-defined list of starting "on" cells so that a specific pattern can be replicated//add histogram view
    //add a-replenish rate as an option

    //vars that can be changed by the user as settings
    private int xSize = 400;
    private int ySize = 100;
    private int startA = 1;
    private int aReplenish = 0;
    private double bDecay = 1.0;
    private double startOnPercent = 0.001;
    private double DiffusionRate = 1.0;
    private int bDiffusionRate = 0;
    private double reactionRate = 0.5;
    private double activationRate = 0.1;//chance of an active cell turning on its neighbors
    private double activationThreshold = 0.1; //min A value that a cell must have in order to be switched on, otherwise it will stay off
    private int activationDelay = 0;
    private int drawEvery = 10;
    private int maxLifeTime = 1000;
    private int shutoffAThreshold = -1; //thee values will keep them from shutting off from thresholds
    private int shutoffBThreshold = 100;
    private boolean showCellStates = false;

    //fixme: add U replenish and V decay options just for the fun of it
    //fixme: once this works exactly like a regular UV R-D system, also add migrating activation to try and generate corn pattens
    //fixme: add "load in a starting picture" to set starting UV values
    //fixme: add "load in another starting picture to set usable cells" (user can specify a non-rectangular picture)

    private boolean paused = false;
    //vars that are used internally
    private int cellCount = xSize * ySize;//total number of cells being used in the simulation
    private double[] cellA;//level of molecule concentration in each cell
    private double[] cellB;
    private double[] cellC;
    private int[] lifeTime;
    private boolean[] cellState; //true if on
    private int[] cellActivationDelay;
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
    private int histoBanding = 4;
    private int pigmentThreshold;

    private static void createAndShowGUI() {
        // Function to set the Default Look
        // And Feel Decorated of JFrame.
        JFrame.setDefaultLookAndFeelDecorated(true);

        // Creating Object of
        // "JFrame" class
        JFrame frame = new JFrame("GroupLayoutExample");

        // Function to set the Default
        // Close Operation of JFrame.
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Creating Object of "JLabel" class
        JLabel label = new JLabel("Label:");

        // Creating Object of
        // "JTextField" class
        JTextField textField = new JTextField();

        // Creating Object of
        // "JCheckBox" class
        JCheckBox checkBox1 = new JCheckBox("CheckBox1");

        // Creating Object of "JCheckBox" class
        JCheckBox checkBox2 = new JCheckBox("CheckBox2");

        // Creating Object of "JButton" class
        JButton findButton = new JButton("Button 1");

        // Creating Object of "JButton" class
        JButton cancelButton = new JButton("Button 2");

        // used to set the Border of a checkBox1
        checkBox1.setBorder(BorderFactory.createEmptyBorder(0, 0,
                0, 0));

        // used to set the Border of a checkBox2
        checkBox2.setBorder(BorderFactory.createEmptyBorder(0, 0,
                0, 0));

        // Creating Object of "GroupLayout" class
        GroupLayout layout = new GroupLayout(frame.getContentPane());

        // to get the content pane
        frame.getContentPane().setLayout(layout);

        // it used to set Auto Create Gaps
        layout.setAutoCreateGaps(true);

        // it used to set Auto Create Container Gaps
        layout.setAutoCreateContainerGaps(true);

        // it used to set the horizontal group
        layout.setHorizontalGroup(layout.createSequentialGroup()

                // Adding the label
                .addComponent(label)

                // Adding the Parallel Group
                .addGroup(layout.createParallelGroup(LEADING)

                        // Adding the textfield
                        .addComponent(textField)

                        // Adding the Sequential Group
                        .addGroup(layout.createSequentialGroup()

                                // Adding the Parallel Group
                                .addGroup(layout.createParallelGroup(LEADING)

                                        // Adding the checkBox1
                                        .addComponent(checkBox1))

                                // Adding the Parallel Group
                                .addGroup(layout.createParallelGroup(LEADING)

                                        // Adding the checkBox2
                                        .addComponent(checkBox2))))

                // Adding the Parallel Group
                .addGroup(layout.createParallelGroup(LEADING)

                        // Adding the findButton
                        .addComponent(findButton)

                        // Adding the CancelButton
                        .addComponent(cancelButton)));

        layout.linkSize(SwingConstants.HORIZONTAL,
                findButton, cancelButton);

        layout.setVerticalGroup(layout.createSequentialGroup()

                // Adding the Parallel Group
                .addGroup(layout.createParallelGroup(BASELINE)

                        // Adding the label
                        .addComponent(label)

                        // Adding the textField
                        .addComponent(textField)

                        // Adding the findButton
                        .addComponent(findButton))

                // Adding the Parallel Group
                .addGroup(layout.createParallelGroup(LEADING)

                        // Adding the sequential Group
                        .addGroup(layout.createSequentialGroup()

                                // Adding the Parallel Group
                                .addGroup(layout.createParallelGroup(BASELINE)

                                        // Adding the checkBox1
                                        .addComponent(checkBox1)

                                        // Adding the checkBox2
                                        .addComponent(checkBox2))

                                // Adding the Parallel Group
                                .addGroup(layout.createParallelGroup(BASELINE)))

                        // Adding the CancelButton
                        .addComponent(cancelButton)));

        frame.pack();
        frame.setVisible(true);
    }

    private static void createAndShowGUIy() {
        JFrame frame = new JFrame("GroupLayoutExample");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container myPanel = frame.getContentPane();

        GroupLayout groupLayout = new GroupLayout(myPanel);
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);
        myPanel.setLayout(groupLayout);

        JButton b1 = new JButton("Restart from zero");
        JButton b2 = new JButton("pause/unpause/end");
        JButton b3 = new JButton("Redraw picture");
        JButton b4 = new JButton("Smooth Edges");

        JCheckBox drawInGiraffeColors = new JCheckBox("Draw in Giraffe colors");


        groupLayout.setHorizontalGroup(groupLayout.createSequentialGroup()
                .addGroup(groupLayout.createParallelGroup(LEADING).addComponent(b1))
                .addGroup(groupLayout.createParallelGroup(TRAILING).addComponent(b2).addComponent(b3).addComponent(b4)));

        groupLayout.setVerticalGroup(groupLayout.createSequentialGroup()
                .addGroup(groupLayout.createParallelGroup(BASELINE).addComponent(b1))
                .addGroup(groupLayout.createParallelGroup(BASELINE).addComponent(b2).addComponent(b3).addComponent(b4)));

        frame.pack();
        frame.setVisible(true);
    }

    private static void createAndShowGUIx() {
        JPanel panel = new JPanel(new SpringLayout());

        //ticks text box
        //4 buttons
        //2 check boxes, one textbox
        //1 checkbox
        //17 label/textbox/label


//        histoBanding = document.getElementById("histoBanding").value - 0;
//        aReplenish = document.getElementById("aReplenish").value - 0;
//        bDecay = 1.0 - document.getElementById("bDecay").value;
//        DiffusionRate = document.getElementById("DiffusionRate").value - 0;
//        reactionRate = document.getElementById("reactionRate").value - 0;
//        activationRate = document.getElementById("activationRate").value - 0;//chance of an active cell turning on its neighbors
//        activationThreshold = document.getElementById("activationThreshold").value - 0;
//        activationDelay = document.getElementById("activationDelay").value - 0;
//        drawEvery = document.getElementById("drawEvery").value - 0;
//        bDiffusionRate = document.getElementById("bDiffusionRate").value - 0;
//        useGiraffeColors = document.getElementById("useGiraffeColors").checked;
//        drawScaled = document.getElementById("drawScaled").checked;
//        pigmentThreshold = document.getElementById("pigmentThreshold").value - 0;
//        maxLifeTime = document.getElementById("maxLifeTime").value - 0;
//        shutoffAThreshold = document.getElementById("shutoffAThreshold").value - 0;
//        shutoffBThreshold = document.getElementById("shutoffBThreshold").value - 0;
//        showCellStates = document.getElementById("showCellStates").checked;

//<div style="border: 2px dotted darkgray;">
//<h2>The Simulation</h2>
//<span id="ticks">ticks</span><br>
//<input type="submit" value="Restart from zero" onclick="setTimeout(function(){init();},10);">
//<input type="submit" value="pause/unpause/end" onclick="paused=!paused; if(!paused) setTimeout(function(){tick();},10);">
//<input type="submit" value="Redraw picture" onclick="setTimeout(function(){mainDraw();},10);">
//<input type="submit" value="Smooth Edges" onclick="setTimeout(function(){diffuseB();},10);"><br>
//
//<input type="checkbox" checked="checked" id="useGiraffeColors" onclick="setTimeout(function(){mainDraw();},10);"> Draw in Giraffe colors<br>
//<input type="checkbox" id="drawScaled" onclick="setTimeout(function(){mainDraw();},10);"> Draw scaled to max <span id="maxLabel"></span> [Banding <input style="width: 40px;" type="text" id="histoBanding" value="0">]<br>
//<canvas id="skin" width="200" height="200" style="border:1px dotted red;cursor: crosshair;"></canvas><br>
//
//<table>
//<tbody><tr><td><input type="checkbox" checked="checked" id="showCellStates"> Show Cell States</td><td>Cyan = On. Red = On and able to activate neighbors.</td></tr>
//<tr><td>pigmentThreshold<input style="width: 40px;" type="text" id="pigmentThreshold" value="0"></td><td><em>(0=ignore thresholds, typical values range from 0.1 to 2.0)</em></td></tr>
//<tr><td>drawEvery<input style="width: 40px;" type="text" id="drawEvery" value="10">th cycle</td><td>Runs faster if you don't draw every cycle.</td></tr>
//                <tr><td>xSize<input style="width: 40px;" type="text" id="xSize" value="200"></td><td>Width of simulation in cells</td></tr>
//<tr><td>ySize<input style="width: 40px;" type="text" id="ySize" value="200"></td><td>Height of simulation in cells</td></tr>
//<tr><td>startA<input style="width: 40px;" type="text" id="startA" value="1.0"></td><td>Cells start out with this much of molecule A</td></tr>
//<tr><td>startOnPercent<input style="width: 40px;" type="text" id="startOnPercent" value="0.001"></td><td>Fraction of cells that start in the On state</td></tr>
//<tr><td>A Replenish<input style="width: 40px;" type="text" id="aReplenish" value="0"></td><td>Cells replenish this much of molecule A each tick</td></tr>
//
//<tr><td>DiffusionRate<input style="width: 40px;" type="text" id="DiffusionRate" value="1.0"></td><td>How quickly A diffuses through tissues</td></tr>
//<tr><td>BDiffusionRate<input style="width: 40px;" type="text" id="bDiffusionRate" value="0"></td><td>How quickly B diffuses through tissues. If set to 0, simulation will run more quickly, too.</td></tr>
//<tr><td>B Decay Rate<input style="width: 40px;" type="text" id="bDecay" value="0"></td><td>(0.0 to 1.0) This proportion of molecule B decays each tick into molecule C.</td></tr>
//<tr><td>reactionRate<input style="width: 40px;" type="text" id="reactionRate" value="1.0"></td><td>How quickly a cell can convert A into B in one cycle. Values over 2.0 may cause bugs or unrealistic results.</td></tr>
//<tr><td>activationRate<input style="width: 40px;" type="text" id="activationRate" value="1"></td><td>How often a cell is successful at activating a neighbor cell.</td></tr>
//<tr><td>activationThreshold<input style="width: 40px;" type="text" id="activationThreshold" value="0.25"></td><td>A cell must have an A value of this much or higher to be activated.</td></tr>
//<tr><td>activationDelay<input style="width: 40px;" type="text" id="activationDelay" value="10"></td><td>A cell cannot activate its neighbors until this many cycles after it was first switched on.</td></tr>
//<tr><td>maxLifeTime<input style="width: 40px;" type="text" id="maxLifeTime" value="1000"></td><td>A cell can only stay in the On state for this long, and then will turn itself off.</td></tr>
//<tr><td>shutoffAThreshold<input style="width: 40px;" type="text" id="shutoffAThreshold" value="-1"></td><td>A cell will switch off if its A value falls below this number.</td></tr>
//<tr><td>shutoffBThreshold<input style="width: 40px;" type="text" id="shutoffBThreshold" value="10"></td><td>A cell will switch off if its B value exceeds this number.</td></tr>
//</tbody></table>
//</div>

        String[] labels = {
                "pigmentThreshold: ",
                "drawEvery Nth cycle: ",
                "xSize: ",
                "ySize: ",
                "startA: ",
                "startOnPercent: ",
                "A Replenish: ",
                "DiffusionRate: ",
                "BDiffusionRate: ",
                "B Decay Rate: ",
                "reactionRate: ",
                "activationRate: ",
                "activationThreshold: ",
                "activationDelay: ",
                "maxLifeTime: ",
                "shutoffAThreshold: ",
                "shutoffBThreshold: "};

        int numPairs = labels.length;

        for (int i = 0; i < numPairs; i++) {
            //JLabel ll = new JLabel("this is a test", JLabel.LEADING);
            JLabel lt = new JLabel(labels[i], JLabel.TRAILING);
            //panel.add(ll);
            panel.add(lt);
            JTextField textField = new JTextField(10);
            lt.setLabelFor(textField);
            //ll.setLabelFor(textField);
            panel.add(textField);
        }

//Lay out the panel.
        SpringUtilities.makeCompactGrid(panel,
                numPairs, 2, //rows, cols
                6, 6,        //initX, initY
                6, 0);       //xPad, yPad

        //Create and set up the window.
        JFrame frame = new JFrame("SpringCompactGrid");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        panel.setOpaque(true); //content panes must be opaque
        frame.setContentPane(panel);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    private void initUI() {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private void getUserVars() {
//        histoBanding = document.getElementById("histoBanding").value - 0;
//        aReplenish = document.getElementById("aReplenish").value - 0;
//        bDecay = 1.0 - document.getElementById("bDecay").value;
//        DiffusionRate = document.getElementById("DiffusionRate").value - 0;
//        reactionRate = document.getElementById("reactionRate").value - 0;
//        activationRate = document.getElementById("activationRate").value - 0;//chance of an active cell turning on its neighbors
//        activationThreshold = document.getElementById("activationThreshold").value - 0;
//        activationDelay = document.getElementById("activationDelay").value - 0;
//        drawEvery = document.getElementById("drawEvery").value - 0;
//        bDiffusionRate = document.getElementById("bDiffusionRate").value - 0;
//        useGiraffeColors = document.getElementById("useGiraffeColors").checked;
//        drawScaled = document.getElementById("drawScaled").checked;
//        pigmentThreshold = document.getElementById("pigmentThreshold").value - 0;
//        maxLifeTime = document.getElementById("maxLifeTime").value - 0;
//        shutoffAThreshold = document.getElementById("shutoffAThreshold").value - 0;
//        shutoffBThreshold = document.getElementById("shutoffBThreshold").value - 0;
//        showCellStates = document.getElementById("showCellStates").checked;
    }

    private int rX = 2; //0.5;
    private int rY = 2; //0.5;

    //deterministic number generator so we can see varied results with identical starting conditions but different settings
    private double myRandom() { // Get the next random number between 0 and 1 in the current sequence.
        // don't let them get stuck
        if (rX == 0) rX = -1;
        if (rY == 0) rY = -1;
        // Mix the bits.
        rX = 36969 * (rX & 0xFFFF) + (rX >> 16);
        rY = 18273 * (rY & 0xFFFF) + (rY >> 16);
//        return ((rX << 16) + (rY & 0xFFFF)) / 0xFFFFFFFF + 0.5;
        return (int)(((rX << 16) + (rY & 0xFFFF)) / 0xFFFFFFFF + 0.5); //TODO maybe this is ok
    }

    //initializes the arrays and vars using parameters from the page
    private void init() {
        tickCount = 0;

        //get the user-configured vars from the html form
//        xSize = document.getElementById("xSize").value - 0;
//        ySize = document.getElementById("ySize").value - 0;
//        startA = document.getElementById("startA").value - 0;
//        histoBanding = document.getElementById("histoBanding").value - 0;
//        aReplenish = document.getElementById("aReplenish").value - 0;
//        bDecay = 1.0 - document.getElementById("bDecay").value;
//        startOnPercent = document.getElementById("startOnPercent").value - 0;
        getUserVars();//fetches values from html form

        //size the canvas appropriately
//        document.getElementById("skin").width = xSize;
//        document.getElementById("skin").height = ySize;
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
        cellActivationDelay = new int[cellCount];

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

        //setTimeout(function() { tick(); } , 10);
        setTimeout(() -> tick(), 10);


        paused = false;
    }//end of init function


    //handles one cycle of the CA
    int ticks=0;
    private void tick() {
        System.out.println("tick" + ticks++);
        getUserVars();//fetches values from html form, so they can be changed as it's running
        double diffRate = DiffusionRate * 0.5; //allow user to use range of 0-1 but optimize calc speed
        double diffRateDiag = DiffusionRate * 0.5 * 0.707;
        double bDiffRate = bDiffusionRate * 0.5; //for B molecule if needed
        double bDiffRateDiag = bDiffusionRate * 0.5 * 0.707;
        double activationRateDiag = activationRate * 0.707;
        tickCount++;
        for (int t = 0; t < cellCount; t++) {
            //pick a cell
            int cNum = (int)Math.floor(Math.random() * cellCount);
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
                    if (cellActivationDelay[cNum] > 0) cellActivationDelay[cNum]--;
                    else {
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
                                if (!cellState[cellNeighbor[cNum][i]]) tryToActivateNeighbors[cNum] = true;
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
                                if (!cellState[cellDiagNeighbor[cNum][i]]) tryToActivateNeighbors[cNum] = true;
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
                    if (lifeTime[cNum] < 0) cellState[cNum] = false;
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
            if ((aReplenish > 0) && (cellA[cNum] < 1.0)) cellA[cNum] += aReplenish;

            if ((aReplenish < 1) && (cellB[cNum] > 0)) cellB[cNum] *= bDecay;
        }

//        document.getElementById("ticks").innerHTML = tickCount;
        if (tickCount % drawEvery == 0) {
            mainDraw();//draw everything's state, as often as specified
            //also check to see if all cells are off, if not a single cell is on, the simulation is over
            boolean simEnded = true;
            for (int i = 0; i < cellCount; i++) {
                if (cellState[i]) {
                    simEnded = false;
                    break;
                }
            }
            if (simEnded) {
//                document.getElementById("ticks").innerHTML = tickCount + " Finished!";
                return;
            }
        }

        //set a timer to tick again
        //if (!paused) setTimeout(function() { tick(); },10);
        setTimeout(() -> tick(), 10);

    }


    //main drawing routine, will branch to the selected rawing mode based on checkboxes and stuff
    private void mainDraw() {
        if (useGiraffeColors) {
            drawAsGiraffe();
        } else if (drawScaled) {
            drawAsScaled();
        } else {
//            var canvas = document.getElementById('skin');
//            var ctx = canvas.getContext('2d');
//            int x = 0;
//            int y = 0;
//
//            if (showCellStates) {
//                for (int i = 0; i < cellCount; i++) {
//                    if (cellState[i]) {
//                        if (!tryToActivateNeighbors[i]) ctx.fillStyle = "rgb(0,255,255)";
//                        else ctx.fillStyle = "rgb(255,0,0)";
//                    } else ctx.fillStyle = "rgb(0,0,0)";
//                    ctx.fillRect(x, y, 1, 1);
//                    x++; //track cell location where we are drawing
//                    if (x >= xSize) {
//                        x = 0;
//                        y++;
//                    }
//                }
//            } else {
//                ///rgb for a giraffe brownish hcolor = 209 111 46
//                if (tickCount % 2 == 0) c = 0;
//                else c = 254;
//                int mult = 255 / startA;//scale blue/yellow values by starting value so they don't overdrive
//                for (int i = 0; i < cellCount; i++) {
//                    int a = (int)Math.floor(cellA[i] * mult); //get colors to draw A and B values graphically
//                    if (a > 255) a = 255;
//                    //var b=0;
//                    int b = (int)Math.floor(cellB[i] * mult);
//                    //if (b>255) b=255;
//                    //if (tryToActivateNeighbors[i]) ctx.fillStyle = "rgb("+a+",255,"+b+")";
//                    //else if (cellState[i])   ctx.fillStyle = "rgb("+a+","+(c/2)+","+b+")";
//                    //else                     ctx.fillStyle = "rgb("+a+",0,"+b+")";
//                    ctx.fillStyle = "rgb(" + a + "," + a + "," + b + ")";
//                    ctx.fillRect(x, y, 1, 1);
//                    x++; //track cell location where we are drawing
//                    if (x >= xSize) {
//                        x = 0;
//                        y++;
//                    }
//                }
//            }
        }
    }


    private void drawAsGiraffe() {
//        if (pigmentThreshold > 0) {
//            drawWithThresholds();
//            return;
//        }
//        var canvas = document.getElementById('skin');
//        var ctx = canvas.getContext('2d');
//        int x = 0;
//        int y = 0;
//        ///rgb for a giraffe brownish hcolor = 209 111 46
//        for (int i = 0; i < cellCount; i++) {
//            int r = 255 - (int)(Math.floor(cellB[i] * 46));
//            if (r < 209) r = 209;
//            int g = 255 - (int)(Math.floor(cellB[i] * 145));
//            if (g < 111) g = 111;
//            int b = 255 - (int)(Math.floor(cellB[i] * 209));
//            if (b < 46) b = 46;
//            ctx.fillStyle = "rgb(" + r + "," + g + "," + b + ")";
//            ctx.fillRect(x, y, 1, 1);
//            x++; //track cell location where we are drawing
//            if (x >= xSize) {
//                x = 0;
//                y++;
//            }
//        }
    }

    private void drawAsScaled() {
//        histoBanding = document.getElementById("histoBanding").value - 0;
//        if (histoBanding > 0) {
//            drawAsHisto();
//        } else {
//            var canvas = document.getElementById('skin');
//            var ctx = canvas.getContext('2d');
//            int x = 0;
//            int y = 0;
//            //create table based on max B value
//            double bMax = 0; //find biggest B value
//            double colorMult;
//            for (int i = 0; i < cellCount; i++) {
//                if (cellB[i] > bMax) bMax = cellB[i];
//            }
//            if (bMax > 0) colorMult = 255 / bMax;
//            else colorMult = 0;
//            document.getElementById('maxLabel').innerHTML = "(" + bMax.toFixed(2) + ")";
//
//            for (int i = 0; i < cellCount; i++) {
//                int g = Math.round(cellB[i] * colorMult).toString();
//                ctx.fillStyle = "rgb(" + g + "," + g + "," + g + ")";
//                ctx.fillRect(x, y, 1, 1);
//                x++; //track cell location where we are drawing
//                if (x >= xSize) {
//                    x = 0;
//                    y++;
//                }
//            }
//        }
    }

    //draw a histogram with only every so many steps banded
    private void drawAsHisto() {
//        var canvas = document.getElementById('skin');
//        var ctx = canvas.getContext('2d');
//        int x = 0;
//        int y = 0;
//        //create table based on max B value
//        double bMax = 0; //find biggest B value
//        double colorMult;
//        for (int i = 0; i < cellCount; i++) {
//            if (cellB[i] > bMax) bMax = cellB[i];
//        }
//        if (bMax > 0) colorMult = 255 / bMax;
//        else colorMult = 0;
//        document.getElementById('maxLabel').innerHTML = "(" + bMax.toFixed(2) + ")";
//
//        for (int i = 0; i < cellCount; i++) {
//            int g = Math.round(cellB[i] * colorMult);
//            if (g == 0) {
//                ctx.fillStyle = "rgb(0,255,0)";
//            } else {
//                if (g % histoBanding == 0) {
//                    ctx.fillStyle = "rgb(255,255,255)";
//                } else {
//                    ctx.fillStyle = "rgb(0,0,0)";
//                }
//            }
//            ctx.fillRect(x, y, 1, 1);
//            x++; //track cell location where we are drawing
//            if (x >= xSize) {
//                x = 0;
//                y++;
//            }
//        }
    }

    //draw a two-color giraffe pattern picture using threshold
    private void drawWithThresholds() {
//        var canvas = document.getElementById('skin');
//        var ctx = canvas.getContext('2d');
//        int x = 0;
//        int y = 0;
//        for (int i = 0; i < cellCount; i++) {
//            if (cellB[i] > pigmentThreshold)
//                ctx.fillStyle = "rgb(209,111,46)";
//            else
//                ctx.fillStyle = "rgb(255,255,255)";
//            ctx.fillRect(x, y, 1, 1);
//            x++; //track cell location where we are drawing
//            if (x >= xSize) {
//                x = 0;
//                y++;
//            }
//        }
    }


    int lastx = 0;
    int lasty = 0;

//    private void drawNeighbors(e) { //TODO e is event
//        var canvas = document.getElementById('skin');
//        var ctx = canvas.getContext('2d');
//        int x = e.clientX - 8;
//        int y = e.clientY - 8;
//        int cNum = (y * xSize) + x;
//        document.getElementById("ticks").innerHTML = x + "," + y + " (" + cNum + ")";
//        ///rgb for a giraffe brownish hcolor = 209 111 46
//        ctx.fillStyle = "rgb(255,255,255)";
//        ctx.fillRect(lastx - 3, lasty - 3, 7, 7);
//        lastx = x;
//        lasty = y;
//        ctx.fillStyle = "rgb(255,0,0)";
//        ctx.fillRect(x, y, 1, 1);
//
//        ctx.fillStyle = "rgb(0,255,0)";
//        for (int i = 0; i < cellNeighbor[cNum].length; i++) {
//            var nNum = cellNeighbor[cNum][i];
//            x = nNum % xSize;
//            y = Math.floor(nNum / ySize);
//            ctx.fillRect(x, y, 1, 1);
//        }
//        ctx.fillStyle = "rgb(0,0,255)";
//        for (int i = 0; i < cellDiagNeighbor[cNum].length; i++) {
//            var nNum = cellDiagNeighbor[cNum][i];
//            x = nNum % xSize;
//            y = Math.floor(nNum / ySize);
//            ctx.fillRect(x, y, 1, 1);
//        }
//    }

    //add the next line to the canvas element to visualize neighboring cells in google chrome
    // onmousemove="drawNeighbors(event);"

    private int lastThreshold = 0;

//    setInterval(function () {
//        checkThreshold();
//    },500);

    private void checkThreshold() {
//        pigmentThreshold = document.getElementById("pigmentThreshold").value - 0;
//        if (lastThreshold != pigmentThreshold) {
//            lastThreshold = pigmentThreshold;
//            //setTimeout(function() {mainDraw();},10);
//            setTimeout(() -> mainDraw(), 10);
//        }
//        useGiraffeColors = document.getElementById("useGiraffeColors").checked;
//        drawScaled = document.getElementById("drawScaled").checked;
//        showCellStates = document.getElementById("showCellStates").checked;
    }

    //will diffuse the B molecule once to smooth out edges a little
    private void diffuseB() {
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
        mainDraw();//show the new result
    }

    //PK added from https://stackoverflow.com/questions/26311470/what-is-the-equivalent-of-javascript-settimeout-in-java
    public static void setTimeout(Runnable runnable, int delay){
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            }
            catch (Exception e){
                System.err.println(e);
            }
        }).start();
    }
}

*/