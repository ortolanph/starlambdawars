package org.starlambdawars.collectors;

import org.starlambdawars.beans.ForceAlignment;
import org.starlambdawars.beans.MovieType;
import org.starlambdawars.beans.StarWarsCharacter;
import org.starlambdawars.beans.StarWarsMovie;
import org.starlambdawars.mapper.StarWarsMovieMapper;
import org.starlambdawars.utils.DataLoader;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StarWarsMovieCollector {

    public StarWarsMovieCollector() throws IOException {
        loader = new DataLoader();
        movies = loader.loadMovies();
        mapper = new StarWarsMovieMapper();
    }

    public Map<ForceAlignment, List<String>> mapForceByCharacters() {
        List<StarWarsCharacter> characters = mapper.allCharacters();

        Map<ForceAlignment, List<String>> result = new HashMap<>();

        Stream
                .of(ForceAlignment.values())
                .forEach(a -> result.put(a, findByForceAlignment(a, characters)));

        return result;
    }

    public Map<MovieType, List<String>> mapTypeByMovies() {
        Map<MovieType, List<String>> result = new HashMap<>();

        Stream
                .of(MovieType.values())
                .forEach(k -> result.put(k, findByKind(k, movies)));

        return result;
    }

    private List<String> findByForceAlignment(ForceAlignment alignment, List<StarWarsCharacter> characters) {
        return characters
                .stream()
                .filter(c -> c.getForceAlignment().equals(alignment))
                .map(c -> c.getName())
                .collect(Collectors.toList());
    }

    private List<String> findByKind(MovieType kind, List<StarWarsMovie> movies) {
        return movies
                .stream()
                .filter(m -> m.getKind().equals(kind))
                .map(m -> m.getTitle())
                .collect(Collectors.toList());
    }

    private DataLoader loader;
    private List<StarWarsMovie> movies;
    private StarWarsMovieMapper mapper;
}
