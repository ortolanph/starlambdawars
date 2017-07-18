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

        PeriodPredicate the80s = new PeriodPredicate(
                LocalDate.of(1980, Month.JANUARY, 1),
                LocalDate.of(1989, Month.DECEMBER, 31)
        );

        StarWarsCharacter lukeSkywalker = new StarWarsCharacter();
        lukeSkywalker.setName("Luke Skywalker");

        System.out.println("Find movies directed by Irvin Kershner with Luke SkyWalker in the 80's");
        System.out.println("----------------------------------------------------------------------");

        movies
                .stream()
                .filter(the80s
                        .and(starWarsCharacterPredicate(lukeSkywalker)
                                .and(m -> m.getDirector().equals("Irvin Kershner"))))
                .collect(Collectors.toList())
                .forEach(m -> System.out.println(m.getTitle()));

    }

    private static Predicate<StarWarsMovie> starWarsCharacterPredicate(StarWarsCharacter character) {
        return m -> m.getMainCharacters().contains(character);
    }

}
