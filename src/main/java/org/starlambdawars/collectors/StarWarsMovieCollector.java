package org.starlambdawars.collectors;

import org.starlambdawars.beans.ForceAlignment;
import org.starlambdawars.beans.MovieType;
import org.starlambdawars.beans.StarWarsCharacter;
import org.starlambdawars.beans.StarWarsMovie;
import org.starlambdawars.mapper.StarWarsMovieMapper;
import org.starlambdawars.utils.DataLoader;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StarWarsMovieCollector {

    public StarWarsMovieCollector() throws IOException {
        loader = new DataLoader();
        movies = loader.loadMovies();
        mapper = new StarWarsMovieMapper();
    }

//    public Map<ForceAlignment, List<StarWarsCharacter>> mapForceByCharacters() {
//        List<StarWarsCharacter> characters = mapper.allCharacters();
//
//        return characters
//                .stream()
//                .map(a -> a.getForceAlignment())
//                .collect(
//                        Collectors.toMap(
//                                ,
//                                findByForceAlignment(a, characters)
//                        )
//                );
//    }
//
//    public Map<MovieType, List<StarWarsMovie>> mapTypeByMovies() {
//        return movies
//                .stream()
//                .map(k -> k.getKind())
//                .collect(
//                        Collectors.toMap(
//                                Function.identity(),
//                                findByKind(k, movies)
//                        )
//                );
//    }

    private List<StarWarsCharacter> findByForceAlignment(ForceAlignment alignment, List<StarWarsCharacter> characters) {
        return characters
                .stream()
                .filter(c -> c.getForceAlignment().equals(alignment))
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
