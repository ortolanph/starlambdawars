package org.starlambdawars.mapper;

import org.starlambdawars.beans.StarWarsCharacter;
import org.starlambdawars.beans.StarWarsMovie;
import org.starlambdawars.utils.DataLoader;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class StarWarsMovieMapper {

    public StarWarsMovieMapper(DataLoader loader, TitleFunction titleFunction) throws IOException {
        movies = loader.loadMovies();
        this.titleFunction = titleFunction;
    }

    public List<String> allDirectors() {
        return movies
                .stream()
                .map(m -> m.getDirector())
                .distinct()
                .collect(Collectors.toList());
    }

    public List<String> allTitles() {
        return movies
                .stream()
                .map(titleFunction)
                .collect(Collectors.toList());
    }

    public List<StarWarsCharacter> allCharacters() {
        return movies
                .stream()
                .map(l -> l.getMainCharacters())
                .flatMap(List::stream)
                .distinct()
                .collect(Collectors.toList());
    }

    private List<StarWarsMovie> movies;
    private TitleFunction titleFunction;
}
