package org.ca.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import java.io.*;

public class ModelSettingsIO {
    private ModelSettings mSettings;
    private Gson mGson;
    private ObjectMapper mMapper;

    public ModelSettingsIO(ModelSettings settings) {
        mSettings = settings;
        mGson = new Gson();
        mMapper = new ObjectMapper();
    }

    public void read(File file) {
        try {
            mSettings = mMapper.readValue(file.getAbsolutePath(), ModelSettings.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save(File file) {
        try {
            mMapper.writeValue(file, mSettings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
