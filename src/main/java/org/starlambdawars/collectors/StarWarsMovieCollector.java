package org.starlambdawars.collectors;

import org.starlambdawars.beans.ForceAlignment;
import org.starlambdawars.beans.StarWarsCharacter;
import org.starlambdawars.beans.StarWarsMovie;
import org.starlambdawars.mapper.StarWarsMovieMapper;
import org.starlambdawars.utils.DataLoader;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
//                .collect(
//                        Collectors.toMap(
//                                a -> a.getForceAlignment(),
//                                characters.stream().filter(c -> c.getForceAlignment().equals(a)).collect(Collectors.toList())
//                        )
//                );
//    }
//
//    public Map<MovieType, List<String>> mapTypeByMovies() {
//        return Arrays
//                .stream(MovieType.values())
//                .collect(
//                        Collectors.toMap(
//                                ,
//                                movies.stream().filter(m -> m.getKind().equals(t)).collect(Collectors.toList())
//                        )
//                );
//    }

    private DataLoader loader;
    private List<StarWarsMovie> movies;
    private StarWarsMovieMapper mapper;
}
