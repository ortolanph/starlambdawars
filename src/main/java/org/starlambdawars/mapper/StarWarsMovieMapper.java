package org.starlambdawars.mapper;

import org.starlambdawars.beans.StarWarsCharacter;
import org.starlambdawars.beans.StarWarsMovie;
import org.starlambdawars.utils.DataLoader;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class StarWarsMovieMapper {

    public StarWarsMovieMapper(DataLoader loader) throws IOException {
        movies = loader.loadMovies();
    }

    public List<String> allDirectors() {
        return movies
                .stream()
                .map(d -> d.getDirector())
                .distinct()
                .collect(Collectors.toList());
    }

    public List<String> allTitles() {
        return movies
                .stream()
                .map(m -> m.getTitle())
                .collect(Collectors.toList());
    }

    public List<StarWarsCharacter> allCharacters() {
        return movies
                .stream()
                .map(l -> l.getMainCharacters())
                .collect(Collectors.toList())
                .stream()
                .flatMap(List::stream)
                .distinct()
                .collect(Collectors.toList());
    }

    private List<StarWarsMovie> movies;
}
