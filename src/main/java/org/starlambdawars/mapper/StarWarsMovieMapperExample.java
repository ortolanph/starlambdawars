package org.starlambdawars.mapper;

import java.io.IOException;

public class StarWarsMovieMapperExample {

    public static void main(String[] args) throws IOException {
        StarWarsMovieMapper mapper = new StarWarsMovieMapper();

        System.out.println("All directors");
        System.out.println("-------------");
        mapper
                .allDirectors()
                .forEach(System.out::println);
        System.out.println();


        System.out.println("All titles");
        System.out.println("----------");
        mapper
                .allTitles()
                .forEach(System.out::println);
        System.out.println();

        System.out.println("All characters");
        System.out.println("--------------");
        mapper
                .allCharacters()
                .forEach(System.out::println);
    }

}
