package org.starlambdawars.finder;

import org.starlambdawars.beans.StarWarsCharacter;
import org.starlambdawars.utils.DataLoader;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;

public class StarWarsDataFinderExample {

    public static void main(String[] args) throws IOException {
        DataLoader loader = new DataLoader(args[0]);
        DirectorPredicate predicate = new DirectorPredicate();
        StarWarsDataFinder finder = new StarWarsDataFinder(loader, predicate);

        System.out.println("Movies directed by George Lucas");
        System.out.println("-------------------------------");
        finder
                .findMovieByDirector("George Lucas")
                .forEach(m -> System.out.println(m.getTitle()));
        System.out.println();

        System.out.println("Movies starred at 1977-05-25");
        System.out.println("----------------------------");
        finder
                .findMovieByDate(LocalDate.of(1977, 5, 25))
                .forEach(m -> System.out.println(m.getTitle()));
        System.out.println();

        System.out.println("Movies in the 80's ");
        System.out.println("-------------------");
        finder
                .findMovieBetweenTwoDates(
                        LocalDate.of(1980, Month.JANUARY, 1),
                        LocalDate.of(1990, Month.DECEMBER, 31))
                .forEach(m -> System.out.println(m.getTitle()));
        System.out.println();

        System.out.println("Movies where we find Jar Jar Binks, :(");
        System.out.println("--------------------------------------");
        StarWarsCharacter jarJarBinks = new StarWarsCharacter();
        jarJarBinks.setName("Jar Jar Binks");
        finder
                .findMovieByCharacter(jarJarBinks)
                .forEach(m -> System.out.println(m.getTitle()));
        System.out.println();
    }

}
