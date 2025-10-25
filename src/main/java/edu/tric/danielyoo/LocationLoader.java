package edu.tric.danielyoo;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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
}
