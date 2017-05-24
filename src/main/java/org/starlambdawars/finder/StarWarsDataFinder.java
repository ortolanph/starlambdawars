package org.starlambdawars.finder;

import org.starlambdawars.beans.StarWarsCharacter;
import org.starlambdawars.beans.StarWarsMovie;
import org.starlambdawars.utils.DataLoader;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StarWarsDataFinder {

    public StarWarsDataFinder() throws IOException {
        loader = new DataLoader();
        movies = loader.loadMovies();
    }

    public List<StarWarsMovie> findMovieByDirector(String director) {
        return movies
                .stream()
                .filter(m -> m.getDirector().equals(director))
                .collect(Collectors.toList());
    }

    public List<StarWarsMovie> findMovieByDate(LocalDate year) {
        return movies
                .stream()
                .filter(m -> m.getReleaseDate().equals(year))
                .collect(Collectors.toList());
    }

    public List<StarWarsMovie> findMovieBetweenTwoDates(LocalDate date1, LocalDate date2) {
        return movies
                .stream()
                .filter(m -> m.getReleaseDate().isEqual(date1) || m.getReleaseDate().isAfter(date1))
                .filter(m -> m.getReleaseDate().isEqual(date2) || m.getReleaseDate().isBefore(date2))
                .collect(Collectors.toList());
    }

    public List<StarWarsMovie> findMovieByCharacter(StarWarsCharacter character) {
        return movies
                .stream()
                .filter(m -> m.getMainCharacters().contains(character))
                .collect(Collectors.toList());
    }

    private DataLoader loader;
    private List<StarWarsMovie> movies;
}
