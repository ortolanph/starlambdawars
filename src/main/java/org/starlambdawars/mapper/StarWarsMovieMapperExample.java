package org.starlambdawars.mapper;

import org.starlambdawars.utils.DataLoader;

import java.io.IOException;

public class StarWarsMovieMapperExample {

    public static void main(String[] args) throws IOException {
        DataLoader loader = new DataLoader(args[0]);
        TitleFunction titleFunction = new TitleFunction();
        StarWarsMovieMapper mapper = new StarWarsMovieMapper(loader, titleFunction);

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
                .forEach(c -> System.out.printf("%s\t%s\t%s\n", c.getName(), c.getPlayedBy(), c.getForceAlignment()));
    }

}
