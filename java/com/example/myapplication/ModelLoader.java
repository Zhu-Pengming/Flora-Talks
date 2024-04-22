package com.example.myapplication;

import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ModelLoader {
    public String[] loadModelFile(AssetManager assetManager, String modelPath) throws IOException {
        List<String> strings = new ArrayList<>();
        BufferedReader reader = null;
        try {
            InputStream inputStream = assetManager.open(modelPath);
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                strings.add(line);
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return strings.toArray(new String[0]);
    }
}
