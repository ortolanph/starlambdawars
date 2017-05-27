package org.starlambdawars.collectors;

import org.starlambdawars.beans.ForceAlignment;
import org.starlambdawars.beans.MovieType;
import org.starlambdawars.beans.StarWarsCharacter;
import org.starlambdawars.beans.StarWarsMovie;
import org.starlambdawars.mapper.StarWarsMovieMapper;
import org.starlambdawars.utils.DataLoader;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StarWarsMovieCollector {

    public StarWarsMovieCollector(DataLoader loader, StarWarsMovieMapper mapper) throws IOException {
        movies = loader.loadMovies();
        this.characters = mapper.allCharacters();
    }

    public Map<ForceAlignment, List<String>> mapForceByCharacters() {
        return characters
                .stream()
                .map(f -> f.getForceAlignment())
                .distinct()
                .collect(
                        Collectors
                                .toMap(
                                        f -> f,
                                        f -> findByForceAlignment(f, characters)
                                )
                );
    }

    public Map<MovieType, List<String>> mapTypeByMovies() {
        return movies
                .stream()
                .map(t -> t.getType())
                .distinct()
                .collect(
                        Collectors
                                .toMap(
                                        t -> t,
                                        t -> findByType(t, movies)
                                )
                );
    }

    private List<String> findByForceAlignment(ForceAlignment alignment, List<StarWarsCharacter> characters) {
        return characters
                .stream()
                .filter(c -> c.getForceAlignment().equals(alignment))
                .map(c -> c.getName())
                .collect(Collectors.toList());
    }

    private List<String> findByType(MovieType type, List<StarWarsMovie> movies) {
        return movies
                .stream()
                .filter(m -> m.getType().equals(type))
                .map(m -> m.getTitle())
                .collect(Collectors.toList());
    }

    private List<StarWarsMovie> movies;
    private List<StarWarsCharacter> characters;
}
