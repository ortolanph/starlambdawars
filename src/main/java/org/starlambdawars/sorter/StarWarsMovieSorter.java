package org.starlambdawars.sorter;

import org.starlambdawars.beans.StarWarsMovie;
import org.starlambdawars.utils.DataLoader;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StarWarsMovieSorter {

    public StarWarsMovieSorter() throws IOException {
        loader = new DataLoader();
        movies = loader.loadMovies();
    }

    public List<StarWarsMovie> releaseOrderSorting() {
        return createOrderedList(Comparator.comparing(StarWarsMovie::getReleaseDate));
    }

    public List<StarWarsMovie> idOrderSorting() {
        return createOrderedList(Comparator.comparing(StarWarsMovie::getId));
    }

    public List<StarWarsMovie> chronologicalOrderSorting() {
        return createOrderedList(Comparator.comparing(StarWarsMovie::getChronologicalOrder));
    }

    public List<StarWarsMovie> personalOrderSorting() {
        return createOrderedList(Comparator.comparing(StarWarsMovie::getPersonalOrder));
    }

    private List<StarWarsMovie> createOrderedList(Comparator<StarWarsMovie> comparator) {
        List<StarWarsMovie> myMovies = movies
                .stream()
                .sorted(comparator)
                .collect(Collectors.toList());

        return myMovies;
    }

    private DataLoader loader;
    private List<StarWarsMovie> movies;
}
