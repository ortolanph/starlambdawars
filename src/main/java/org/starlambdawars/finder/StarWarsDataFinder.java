package org.starlambdawars.finder;

import org.starlambdawars.beans.StarWarsMovie;
import org.starlambdawars.utils.DataLoader;

import java.io.IOException;
import java.util.List;

public class StarWarsDataFinder {

    public StarWarsDataFinder() throws IOException {
        loader = new DataLoader();
        movies = loader.loadMovies();
    }


    public List<String> findMovieByCharacter(String characterName) {
        return null;
    }

    public List<String> findMovieByActor(String actorName) {
        return null;
    }

    private DataLoader loader;
    private List<StarWarsMovie> movies;
}
