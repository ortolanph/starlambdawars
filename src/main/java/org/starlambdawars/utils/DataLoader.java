package org.starlambdawars.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.starlambdawars.beans.StarWarsMovie;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Loads Star Wars data.
 *
 * @author Paulo Henrique Ortolan
 */
public class DataLoader {
    public List<StarWarsMovie> loadMovies() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        String json = null;
        try {
            json = new String(Files.readAllBytes(Paths.get(getFileURI())));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        List<StarWarsMovie> movies  = mapper.readValue(json, new TypeReference<List<StarWarsMovie>>(){});

        return movies;
    }

    private URI getFileURI() throws URISyntaxException {
        return getClass().getResource("/all_star_wars_movies.json").toURI();
    }
}
