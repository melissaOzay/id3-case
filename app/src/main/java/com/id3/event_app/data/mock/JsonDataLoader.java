package com.id3.event_app.data.mock;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class JsonDataLoader {

    private final Context context;
    private final Gson gson;

    public JsonDataLoader(Context context) {
        this.context = context.getApplicationContext();
        this.gson = new Gson();
    }

    public <T> List<T> loadList(String fileName, Class<T> clazz) {
        String json = readJsonFromAssets(fileName);
        if (json == null || json.isEmpty()) {
            return new ArrayList<>();
        }
        Type listType = TypeToken.getParameterized(List.class, clazz).getType();
        List<T> result = gson.fromJson(json, listType);
        return result != null ? result : new ArrayList<>();
    }

    public List<String> loadStringList(String fileName) {
        String json = readJsonFromAssets(fileName);
        if (json == null || json.isEmpty()) {
            return new ArrayList<>();
        }
        Type listType = new TypeToken<List<String>>(){}.getType();
        List<String> result = gson.fromJson(json, listType);
        return result != null ? result : new ArrayList<>();
    }

    private String readJsonFromAssets(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            reader.close();
            inputStream.close();
        } catch (IOException e) {
            return null;
        }
        return stringBuilder.toString();
    }
}
