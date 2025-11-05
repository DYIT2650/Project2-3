package edu.tric.danielyoo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class LocationLoader {

    public static List<Location> loadFromResource(String resourcePath) {
        List<Location> locations = new ArrayList<>();

        try {
            InputStream inputStream = LocationLoader.class.getResourceAsStream(resourcePath);

            if (inputStream == null) {
                System.err.println("Resource not found: " + resourcePath);
                return locations;
            }

            // Parse JSON using Jackson in order to make editing the locations resource easier
            ObjectMapper objectMapper = new ObjectMapper();
            locations = objectMapper.readValue(inputStream, new TypeReference<List<Location>>() {});

            inputStream.close();

        } catch (IOException e) {
            System.err.println("Error loading locations from resource: " + e.getMessage());
            e.printStackTrace();
        }

        return locations;
    }


    public static boolean saveToFile(List<Location> locations, String filePath) {
        try {
            File file = new File(filePath);

            // Create parent directories if they don't exist
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                if (!parentDir.mkdirs()) {
                    System.err.println("Failed to create directory: " + parentDir);
                    return false;
                }
            }

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT); 
            objectMapper.writeValue(file, locations);

            System.out.println("Locations saved successfully to: " + filePath);
            return true;
        } catch (IOException e) {
            System.err.println("Error saving locations to file: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
