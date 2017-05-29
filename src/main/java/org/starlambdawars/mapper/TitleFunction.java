package org.starlambdawars.mapper;

import org.starlambdawars.beans.StarWarsMovie;

import java.util.function.Function;

public class TitleFunction implements Function<StarWarsMovie, String> {

    @Override
    public String apply(StarWarsMovie starWarsMovie) {
        return starWarsMovie.getTitle();
    }
}
