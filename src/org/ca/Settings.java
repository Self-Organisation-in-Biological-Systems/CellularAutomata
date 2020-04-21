package org.ca;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

//https://stackoverflow.com/questions/31728446/write-a-json-file-in-java

public class Settings {
    private JSettings jsettings;
    private Gson gson;

    public Settings(Path settingsPath) {
        try {
            gson = new Gson();
            File jSettingsFile = new File(settingsPath.toString());
            if (!jSettingsFile.exists())
                System.out.println(String.format("%s %s", settingsPath.toString(), "settings file doesn't exist"));
            readJSettings(new BufferedReader(new FileReader(jSettingsFile)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readJSettings(BufferedReader br) {
        try {
            StringBuilder jsonString = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                jsonString.append(line).append('\n');
            }

            jsettings = gson.fromJson(jsonString.toString(), JSettings.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeJSettings() {
        //gson.toJson();
    }
    
    public Integer getXSize() {
        return jsettings.xSize;
    }

    public Integer getYSize() {
        return jsettings.ySize;
    }
    
    class JSettings {
        private Integer xSize;
        private Integer ySize;

        JSettings() {
        }
    }

//    private void getUserVars() {
//        histoBanding = 0;
//        xSize = mControlFrame.getXSize();
//        ySize = mControlFrame.getYSize();
//        aReplenish = mControlFrame.getAReplenish();
//        bDecay = 1.0 - mControlFrame.getBDecayRate();
//        DiffusionRate = mControlFrame.getDiffusionRate();
//        reactionRate = mControlFrame.getReactionRate();
//        activationRate = mControlFrame.getActivationRate();
//        activationThreshold = mControlFrame.getActivationThreshold();
//        activationDelay = mControlFrame.getActivationDelay();
//        drawEvery = mControlFrame.getDawEveryNthCycle();
//        bDiffusionRate = mControlFrame.getBDiffusionRate();
//        useGiraffeColors = mControlFrame.drawInGiraffeColors();
//        drawScaled = mControlFrame.drawScaledToMax();
//        pigmentThreshold = mControlFrame.getPigmentThreshold();
//        maxLifeTime = mControlFrame.getMaxLifeTime();
//        shutoffAThreshold = mControlFrame.getShutoffAThreshold();
//        shutoffBThreshold = mControlFrame.getShutoffBThreshold();
//        showCellStates = mControlFrame.showCellStates();
//        startOnPercent = mControlFrame.getStartOnPercent();
//        startA = mControlFrame.getStartA();
//    }
}
