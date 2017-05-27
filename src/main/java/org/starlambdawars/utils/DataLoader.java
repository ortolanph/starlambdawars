package org.starlambdawars.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.starlambdawars.beans.StarWarsMovie;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Loads Star Wars data.
 *
 * @author Paulo Henrique Ortolan
 */
public class DataLoader {
    public DataLoader(String fileName) {
        this.fileName = fileName;
    }

    public List<StarWarsMovie> loadMovies() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        List<StarWarsMovie> movies = mapper.readValue(new File(fileName), new TypeReference<List<StarWarsMovie>>() {
        });

        return movies;
    }

    private String fileName;
}
