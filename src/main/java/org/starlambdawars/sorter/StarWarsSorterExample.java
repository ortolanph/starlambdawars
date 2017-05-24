package org.starlambdawars.sorter;

import org.starlambdawars.beans.StarWarsMovie;
import org.starlambdawars.utils.DataLoader;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StarWarsSorterExample {
    public static void main(String[] args) throws IOException {
        DataLoader loader = new DataLoader();
        List<StarWarsMovie> movies = loader.loadMovies();

        System.out.println("Release date order");
        movies
                .stream()
                .sorted(Comparator.comparing(StarWarsMovie::getReleaseDate))
                .collect(Collectors.toList())
                .forEach(m -> System.out.println(m.getTitle()));
    }
}
