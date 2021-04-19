package org.ca.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
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
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder settingsJson = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                settingsJson.append(line);
            }
            reader.close();
            ModelSettings loadedSettings = mMapper.readValue(settingsJson.toString(), ModelSettings.class);
            mSettings.load(loadedSettings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save(File file) {
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String settingsJson = ow.writeValueAsString(mSettings);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(settingsJson);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
