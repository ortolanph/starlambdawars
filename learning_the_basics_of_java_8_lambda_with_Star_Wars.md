# Learning the basics of Java 8 Lambda with Star Wars

## Index

1. Introduction
    1. Java 8 Lambdas
    2. Source Code
    3. Running the examples
2. Filters
   1. Implementing a predicate
3. Mappers
   1. Writing a function
   2. ```flatMap``` and ```distinct```
4. Sorters
   1. Implementing a ```Comparator```
   2. Using a ```Comparator```
   3. Reversing a list
5. Collectors
   1. ```toList()``` and ```toSet```
   2. ```toMap```
6. ```toMap```
7. Conclusion

## Introduction

Star Wars is the most famous brand in this side of the universe. All it's stories occurs a long time ago in a galaxy far far away. It generated tons of fans around the world within many and many generations. With eight movies, seven in the main saga and one spin-off (at the moment I am writing this article there were two upcoming movies, one of the main saga and other spin-off), many things can be done to browse data. I'm ignoring the Holiday Special and the two Ewoks movies. Feel free to input these movies data in the ```all_star_wars_movie.json``` data file.

### Java 8 Lambdas

In the Java language, the lambdas were introduced in the version 8. They are all spread in the language making easy the way we use things. Check the IO package to see new ways to load files. Other thing that was introduced with Java 8 lambdas was the default interface methods to help the lambdas. Check the Comparator interface, there are a bunch of default methods there. It's used in the sorter examples of this article. Many other things were introduced to make easy the way we develop in Java nowadays.

This article is devided into four sections:

1. Filters, in which the ```filter``` method use is demonstrated
2. Mappers, in which the ```map``` and the ```flatMap``` methods are discussed
3. Sorters, in which the ```sort``` method is used
4. Collector, in which the ```collect``` method is used

### Source Code

To write this article, I developed a small project. It's on github, just to be cloned. Address to the [project link](https://github.com/ortolanph/starlambdawars) and have fun. Don't forget to star this project!

### Running the examples

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

See the implemented examples on the ```org.starlambdawars.finder ``` package of the project to see how to implement a ```Predicate```. Feel free to clone the repository and change the code.

## Mappers

Maps in the Java 8 streams are converters. They are not related to the ```Map``` data structure. They receive a ```java.util.function.Function``` object to make this conversion. As the ```Predicate``` interface, the ```Function``` interface have one method to be overrided that is the ```apply``` method, and other default and static methods.

The default methods enhance function composition, but unfortunatelly that will not be discussed in this article. The static method represents a special function that is the identity function.

The examples of this articles are simple. Not that complex. They are here just to introduce the notion of map inside a stream chain.

### Writing a function

It's wanted to have a list of all the movies in a list collection. Prior to Java 8, it could be done creating a ```List``` instance, iterating over all movies with for each statement (or even a traditional for statement) and adding each item to the list.

That seems to much work to do. And it is! Let's make thins easy.

First way to provide a solution is to implement the ```Function``` interface. That's easy! The code below address to this kind of implementation:

```java
public class TitleFunction implements Function<StarWarsMovie, String> {

    @Override
    public String apply(StarWarsMovie starWarsMovie) {
        return starWarsMovie.getTitle();
    }
}
```

And this function can be used in the ```map``` call in the stream chaining like this:

```java
public List<String> allTitles() {
    return movies
            .stream()
            .map(titleFunction)
            .collect(Collectors.toList());
}
```

In the example above, the ```titleFunction``` variable is an instance of the TitleFunction class implemented before. It's possible to use the arrow notation to implement this function. The example below shows this:

```java
public List<String> allTitles() {
    return movies
            .stream()
            .map(m -> m.getTitle())
            .collect(Collectors.toList());
}
```

### ```flatMap``` and ```distinct```

Other way to convert things is to use Flatmaps. It behaves the same as map does, but it will return zero or more occurrencies. The code below uses the flatMap in the stream chain:

```java
public List<StarWarsCharacter> allCharacters() {
    return movies
            .stream()
            .map(l -> l.getMainCharacters())
            .collect(Collectors.toList())
            .stream()
            .flatMap(List::stream)
            .distinct()
            .collect(Collectors.toList());
}
```

That means that in the end there will be a list of distinct characters of all movies. If a movie has no charaters, nothing will be added (in the case of the Qatsi trilogy).

Chaining ```distinct()``` will build a list without repetition. To make this possible it's required to implement ```hashCode()``` and ```equals()``` methods to better accuracy on the results.

That's simple and fun! Another way to implement a custom function is to create a custom interface function using the ```@FunctionalInterface``` annotation. As the examples of this articles are not that complex, it'll not be used here.

Check the implemented examples on the ```org.starlambdawars.mapper``` package. Try to implement a function to retrieve other informations from the dataset.

## Sorters

To sort something in java is the same to implement the ```java.util.Comparator``` interface. The key method in the stream chaining is the ```sort``` method.

It was added to the ```Comparator``` class a bunch of default methods, just make the development of a Compator easy. It'll not be discussed every default method on this article, just the ```comparing``` method.

### Implementing a ```Comparator```

It's already known the implementation of a Comparator and, because of this, it'll not be added a code snippet showing this.

There are two ways to use a Comparator: implementing using the arrow notation or using the ```comparing``` default method.

Let's implement a Comparator using the ```chronologicalOrder``` field. The chronological order will tell us what's the movie order when sorting it by the number of the episodes. The code snippet below shows this:
 
```java
List<StarWarsMovie> myMovies = movies
        .stream()
        .sorted((m1, m2) -> m1.getChronologicalOrder() - m2.getChronologicalOrder())
        .collect(Collectors.toList());
```

The syntax is a bit different from the other implementation because the ```compare``` method takes two parameters, just as if it was implemented.

### Using a ```Comparator```

In the ```Comparator``` class, there are a bunch of static methods that can be used to avoid implementations. One of this static methods is the ```comparing``` method, that takes a ```Function``` as a parameter. It's not necessary to implement a function to make use of this method, just use the double-colon (```::```) notation informing the Class name and the method name, like this: ```Class::method```. It'll convert into a function returning the method return.

The implementation of another example, using the field ```releaseDate``` stays as follows:

```java
List<StarWarsMovie> myMovies = movies
        .stream()
        .sorted(Comparator.comparing(StarWarsMovie::getReleaseDate))
        .collect(Collectors.toList());
```

### Reversing a list

Following the previous example, it's simple to write a code to modify the sort order. It's needed to add only one thing: a call to ```reversed()``` method.    

```java
List<StarWarsMovie> myMovies = movies
        .stream()
        .sorted(Comparator.comparing(StarWarsMovie::getReleaseDate).reversed())
        .collect(Collectors.toList());
```

This makes easy to sort a collection.

Check the ```org.starlambdawars.sorter``` package to see the examples implemented with the Comparator to sort data. Modify the ```personalOrder``` field in the data file to see what outputs in the ```personalOrderSorting()``` method.   

## Collectors

All the examples ends with a call to the ```collect``` method. This is a special method that closes the ```Stream``` opened by the ```stream()``` method and reduces the stream to a collection.

There is a class that makes things easy to use this method, that is the ```Collectors``` class. The main three methods in this class are:

1. ```toList``` that creates a list
2. ```toSet``` that creates a set
3. ```toMap``` that creates a map

### ```toList``` and ```toSet```

They are easy. Just call it and will result in a list or a set. But remember: sets are lists that repetitions are not allowed. So, just remember to implement ```equals``` and ```hashCode``` methods of the containing class before use. 

The following code snippet shows a ```toList``` Collectors call:

```java
private List<String> findByForceAlignment(ForceAlignment alignment, List<StarWarsCharacter> characters) {
    return characters
            .stream()
            .filter(c -> c.getForceAlignment().equals(alignment))
            .map(c -> c.getName())
            .collect(Collectors.toList());
}
```

### ```toMap```

Maps require two things to build: a key type and a value type. The ```toMap``` method requires at last two parameters:
 
1. a ```Function``` that defines the key
2. a ```Function``` that defines the value

In the example below, the resulting map will have a enum key, typed by the ```ForceAlignment``` enum, and a List of the characters names as the value. First, it's needed to transform the ```StarWarsMovie``` stream into a ```ForceAlignment``` enum stream using the ```map``` function. Then it's needed to create a distinct list of them and finally call to ```collector``` to create the map. 

```java
public Map<ForceAlignment, List<String>> mapForceByCharacters() {
    return characters
            .stream()
            .map(f -> f.getForceAlignment())
            .distinct()
            .collect(
                    Collectors
                            .toMap(
                                    f -> f,
                                    f -> findByForceAlignment(f, characters)
                            )
            );
}
```

The key will have the identiy function and it has to be written that way ```f -> f```. The value must have to return a List of String called by the method ```findByForceAlignment```. It's implementation is in the previous section.

## ```forEach```
 
As a last thing, the forEach method implements a for-each statement. It's easy! The code snippet below shows it in action:

```java
sorter
        .personalOrderSorting()
        .forEach(m -> System.out.println(m.getTitle()));
```

In this example, the ```forEach``` is looping over a stream of ```StarWarsMovies``` coming from the ```personalOrderSorting()``` method. The class that is looping this stream doesn't need to know nothing about ```StarWarsMovie``` class or even import it if it'll be used to simple things like print out on the screen. The ```forEach``` method can even be called from a List or a Set without calling the ```stream``` method. 

## Conclusion

Java 8 stream api and java 8 lambdas help programmers to create better and short code. It can be complicated when learning, but everything will make sense after using a little bit more. Some programmers complains that Java is a verbose language, but with streams it's possible to remove this verbosity and make things simple as seen on the examples. Clone this repository and try to learn from it. It's free to modify the data for testing purposes. 
