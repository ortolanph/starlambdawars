package org.starlambdawars.finder;

import org.starlambdawars.beans.StarWarsMovie;

import java.time.LocalDate;
import java.time.Month;
import java.util.function.Predicate;

public class The80sPredicate implements Predicate<StarWarsMovie> {
    @Override
    public boolean test(StarWarsMovie m) {
        return (m.getReleaseDate().isEqual(BEGIN_OF_80S) ||
                m.getReleaseDate().isAfter(BEGIN_OF_80S))
                && (m.getReleaseDate().isEqual(END_OF_80S) ||
                m.getReleaseDate().isBefore(END_OF_80S));
    }

    private static final LocalDate BEGIN_OF_80S =
            LocalDate.of(1980, Month.JANUARY, 1);
    private static final LocalDate END_OF_80S =
            LocalDate.of(1989, Month.DECEMBER, 31);
}
