# Learning the basics of Java 8 Lambda with Star Wars

## Index

1. Introduction
    1. Java 8 Lambdas
    2. Other acknowledgements
2. Filters
   1. Implementing a predicate
3. Mappers
4. Sorters
5. Collectors
6. Conclusion

## Introduction

Star Wars is the most famous brand in this side of the universe. All it's stories occurs a long time ago in a galaxy far far away. It generated tons of fans around the world within many and many generations. With eight movies, seven in the main saga and one spin-off (at the moment I am writing this article there were two upcoming movies, one of the main saga and other spin-off), many things can be done to browse data. I'm ignoring the Holiday Special and the two Ewoks movies. Feel free to input these movies data in the ```all_star_wars_movie.json``` data file.

### Java 8 Lambdas

In the Java language, the lambdas were introduced in the version 8. They are all spread in the language making easy the way we use things. Check the IO package to see new ways to load files. Other thing that was introduced with Java 8 lambdas was the default interface methods to help the lambdas. Check the Comparator interface, there are a bunch of default methods there. It's used in the sorter examples of this article. Many other things were introduced to make easy the way we develop in Java nowadays.

This article is devided into four sections:

1. Filters, in which the ```filter``` method use is demonstrated
2. Mappers, in which the ```map``` and the ```flatMap``` methods are discussed
3. Sorters, in which the ```sort``` method is used
4. Collector, in which the ```collect``` method is used

### Other acknowledgements

Address to the README.md file to understand how to run the examples of this article.

## Filters

Filters mean to select data within a condition. In Java, conditions are predicates and they are addressed to the ```java.util.function.Predicate<T>``` interface. There's only one method that must be implemented: the ```boolean test(T t)``` method, that evaluates an expression and returns a boolean. There are other default methods that can be used when implementing this interface.

Click the following link to the [Predicate](https://docs.oracle.com/javase/8/docs/api/java/util/function/Predicate.html) documentation.

### Implementing a Predicate

There are mainly two ways to use a Predicate. The first is to implement the interface in a concrete class. The developer must implement all abstract methods, in this case the ```test``` method. It's possible to override the default methods, but it's not the main target of this article.

Let's begin an example with our magnificent data set. It's desired to find movies that were directed by a certain director, let's say George Lucas. Yeah, the mind behind the saga.

```java
package org.starlambdawars.finder;

import org.starlambdawars.beans.StarWarsMovie;

import java.util.function.Predicate;

public class DirectorPredicate implements Predicate<StarWarsMovie> {

    public DirectorPredicate(String director) {
        this.director = director;
    }

    @Override
    public boolean test(StarWarsMovie starWarsMovie) {
        return starWarsMovie.getDirector().equals(director);
    }

    private String director;
}
```

The class above shows the implementation of a ```Predicate```. The method test was implemented receiving a ```StarWarMovie``` object, containing all the movie information, including the director information. It's needed to create an instance of this class everytime when it's desired to use.

```java
public List<StarWarsMovie> findMovieByDirector(String director) {
    DirectorPredicate predicate = new DirectorPredicate(director);

    return movies
            .stream()
            .filter(m -> predicate.test(m))
            .collect(Collectors.toList());
}
```

To begin using streams in Java 8, the method ```stream()``` starts the magic. It will return a ```Stream<R>``` instance representing the object stream to be manipulated. Cool, huh?

The method ```filter``` receives the implementation of ```Predicate``` as a parameter and returns a ```Stream<R>```. Sounds a little complicated, but when looking at the code above, it seems better. It can be added to the custom predicate a setter method to set the director name everytime the predicate is called.

There's another way to implement the ```Predicate``` interface. In Java 8, interfaces that have one method to implemented can be direct implemented. How? It's not needed to implement that on a separate concrete class, it can be implemented directly on the method call with the Java 8 lambda notation. Yay! Making real progress here! The code below shows it:

```java
public List<StarWarsMovie> findMovieByDirector(String director) {
    return movies
            .stream()
            .filter(m -> m.getDirector().equals(director))
            .collect(Collectors.toList());
}
```
What just happened here? Pure magic! In the filter method it's used the Java 8 arrow notation to implement a ```Predicate```. The symbol ```m``` represents an instance of the ```StarWarsMovie``` class, so it can call all the methods that are implemented there including the ```getDirector``` that retrieves the director information. This way, it's possible to call the ```equals``` method that can be used to find our movies filtering by directors.

It's possible to chain multiple ```filter``` methods to best select our data. The code below show this chaining in action:

```java
movies
        .stream()
        .filter(m -> m.getDirector().equals(director))
        .filter(m -> m.getMainCharacters().contains(lukeSkywalker()))
        .filter(inThe80s())
        .collect(Collectors.toList())
        .forEach(m -> System.out.println(m.getTitle()));
```

The ```lukeSkywalker()``` methods creates an instance of StarWarsCharacter containing the name "Luke Skywalker" The implementation of the ```inThe80s()```, selects the movies between the 80's as follows:

```java
private static Predicate<StarWarsMovie> inThe80s() {
    LocalDate startDate = LocalDate.of(1980, Month.JANUARY, 1);
    LocalDate endDate = LocalDate.of(1989, Month.DECEMBER, 31);

    return m -> (m.getReleaseDate().isEqual(startDate) || m.getReleaseDate().isAfter(startDate))
            || (m.getReleaseDate().isEqual(endDate) || m.getReleaseDate().isBefore(endDate));
}
```

It can be seem that the methods ```collect``` and  ```forEach``` are used. It'll be discussed further in the article.

## Mappers



## Sorters



## Collectors



## Conclusion

This is only the basics.
