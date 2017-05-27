package org.starlambdawars.finder;

import org.starlambdawars.beans.StarWarsCharacter;
import org.starlambdawars.beans.StarWarsMovie;
import org.starlambdawars.utils.DataLoader;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StarWarsFilterExample {

    public static void main(String[] args) throws IOException {
        DataLoader loader = new DataLoader(args[0]);
        List<StarWarsMovie> movies = loader.loadMovies();

        System.out.println("Find movies directed by George Lucas with Luke SkyWalker in the 80's");
        System.out.println("--------------------------------------------------------------------");

        String director = "George Lucas";

        movies
                .stream()
                .filter(m -> m.getDirector().equals(director))
                .filter(m -> m.getMainCharacters().contains(lukeSkywalker()))
                .filter(inThe80s())
                .collect(Collectors.toList())
                .forEach(m -> System.out.println(m.getTitle()));
    }

    private static StarWarsCharacter lukeSkywalker() {
        StarWarsCharacter lukeSkywalker = new StarWarsCharacter();
        lukeSkywalker.setName("Luke Skywalker");

        return lukeSkywalker;
    }

    private static Predicate<StarWarsMovie> inThe80s() {
        LocalDate startDate = LocalDate.of(1980, Month.JANUARY, 1);
        LocalDate endDate = LocalDate.of(1989, Month.DECEMBER, 31);

        return m -> (m.getReleaseDate().isEqual(startDate) || m.getReleaseDate().isAfter(startDate))
                || (m.getReleaseDate().isEqual(endDate) || m.getReleaseDate().isBefore(endDate));
    }
}
