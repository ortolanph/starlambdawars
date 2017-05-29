package org.starlambdawars.collectors;

import org.starlambdawars.beans.ForceAlignment;
import org.starlambdawars.beans.MovieType;
import org.starlambdawars.mapper.StarWarsMovieMapper;
import org.starlambdawars.mapper.TitleFunction;
import org.starlambdawars.utils.DataLoader;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class StarWarsMovieCollectorExample {

    public static void main(String[] args) throws IOException {
        DataLoader loader = new DataLoader(args[0]);
        TitleFunction titleFunction = new TitleFunction();
        StarWarsMovieMapper mapper = new StarWarsMovieMapper(loader, titleFunction);
        StarWarsMovieCollector collector = new StarWarsMovieCollector(loader, mapper);

        System.out.println("Force Alignment");
        System.out.println("---------------");
        Map<ForceAlignment, List<String>> alignmentMap = collector.mapForceByCharacters();
        alignmentMap
                .keySet()
                .forEach(
                        a -> alignmentMap
                                .get(a)
                                .stream()
                                .forEach(c -> System.out.printf("%s\t%s\n", a, c)));
        System.out.println();

        System.out.println("Movie by Type");
        System.out.println("-------------");
        Map<MovieType, List<String>> typeMap = collector.mapTypeByMovies();
        typeMap
                .keySet()
                .forEach(
                        k -> typeMap
                                .get(k)
                                .stream()
                                .forEach(m -> System.out.printf("%s\t%s\n", k, m))
                );
        System.out.println();
    }

}
