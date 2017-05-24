package org.starlambdawars.sorter;

import org.starlambdawars.beans.StarWarsMovie;

import java.io.IOException;

public class StarWarsMovieSorterExample {

    public static void main(String[] args) throws IOException {
        StarWarsMovieSorter sorter = new StarWarsMovieSorter();

        System.out.println("Id Order");
        System.out.println("--------");
        sorter
                .idOrderSorting()
                .forEach(StarWarsMovie::getTitle);
        System.out.println();

        System.out.println("Chronological Order");
        System.out.println("-------------------");
        sorter
                .chronologicalOrderSorting()
                .forEach(StarWarsMovie::getTitle);
        System.out.println();

        System.out.println("Personal Order");
        System.out.println("--------------");
        sorter
                .personalOrderSorting()
                .forEach(StarWarsMovie::getTitle);
        System.out.println();

        System.out.println("Release Order");
        System.out.println("-------------");
        sorter
                .releaseOrderSorting()
                .forEach(StarWarsMovie::getTitle);
        System.out.println();
    }

}
