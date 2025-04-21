package org.raaveinm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class GsonSerializable {

    public GsonSerializable() {}


    public String gsonSerialization(TrackInfo trackInfo) {
        Gson gson = new Gson();
        return gson.toJson(trackInfo);
    }


    public List gsonDeserialization(String jsonArrayInput){
        Gson gson = new Gson();
        Type trackInfoListType = new TypeToken<List<TrackInfo>>() {}.getType();
        return gson.<List<TrackInfo>>fromJson(jsonArrayInput, trackInfoListType);
    }


    public void gsonBuilder(String filePath, TrackInfo input){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Type trackInfoListType = new TypeToken<List<TrackInfo>>() {}.getType();
        String existingJson = null;

        try {
            existingJson = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            e.printStackTrace();
        }

        List<TrackInfo> trackList = gson.fromJson(existingJson, trackInfoListType);
        TrackInfo newTrack = new TrackInfo(input.getName(),input.getArtist(),input.getAlbum(),input.getLength());
        assert trackList != null;
        trackList.add(newTrack);
        String updatedJson = gson.toJson(trackList);

        try {
            Path path = Paths.get(filePath);
            Files.writeString(path, updatedJson);
            System.out.println("Successfully updated " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing updated JSON to file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
