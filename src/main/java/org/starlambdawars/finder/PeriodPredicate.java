package org.starlambdawars.finder;

import org.starlambdawars.beans.StarWarsMovie;

import java.time.LocalDate;
import java.util.function.Predicate;

public class PeriodPredicate implements Predicate<StarWarsMovie> {

    public PeriodPredicate(LocalDate startingDate, LocalDate finishingDate) {
        this.startingDate = startingDate;
        this.finishinDate = finishingDate;
    }

    @Override
    public boolean test(StarWarsMovie m) {
        return (m.getReleaseDate().isEqual(startingDate) ||
                m.getReleaseDate().isAfter(startingDate))
                && (m.getReleaseDate().isEqual(finishinDate) ||
                m.getReleaseDate().isBefore(finishinDate));
    }

    private LocalDate startingDate;
    private LocalDate finishinDate;
}
