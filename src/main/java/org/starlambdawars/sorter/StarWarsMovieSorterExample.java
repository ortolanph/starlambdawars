package org.starlambdawars.sorter;

import org.starlambdawars.utils.DataLoader;

import java.io.IOException;

public class StarWarsMovieSorterExample {

    public static void main(String[] args) throws IOException {
        DataLoader loader = new DataLoader(args[0]);
        StarWarsMovieOderer sorter = new StarWarsMovieOderer(loader);

        System.out.println("Id Order");
        System.out.println("--------");
        sorter
                .idOrderSorting()
                .forEach(m -> System.out.println(m.getTitle()));
        System.out.println();

        System.out.println("Chronological Order");
        System.out.println("-------------------");
        sorter
                .chronologicalOrderSorting()
                .forEach(m -> System.out.println(m.getTitle()));
        System.out.println();

        System.out.println("Personal Order");
        System.out.println("--------------");
        sorter
                .personalOrderSorting()
                .forEach(m -> System.out.println(m.getTitle()));
        System.out.println();

        System.out.println("Release Order");
        System.out.println("-------------");
        sorter
                .releaseOrderSorting()
                .forEach(m -> System.out.println(m.getTitle()));
        System.out.println();
    }

}
