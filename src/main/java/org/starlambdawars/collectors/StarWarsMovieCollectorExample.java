package org.starlambdawars.collectors;

import org.starlambdawars.beans.ForceAlignment;
import org.starlambdawars.beans.MovieType;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class StarWarsMovieCollectorExample {

    public static void main(String[] args) throws IOException {
        StarWarsMovieCollector collector = new StarWarsMovieCollector();

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
