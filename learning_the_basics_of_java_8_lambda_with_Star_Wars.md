# Learning the basics of Java 8 Lambda with Star Wars

## Index

1. Introduction
    1. Java 8 Lambdas
    2. Other acknowledgements
2. Filters
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

Filters mean to select data within a condition. In Java, conditions are predicates and they are addressed to the ```java.util.function.Predicate<T>``` interface. There's only one method that must be implemented: the ```boolean test(T t)``` method, that evaluates an expression and returns a boolean. There are other default methods

Click the following link to the [Predicate](https://docs.oracle.com/javase/8/docs/api/java/util/function/Predicate.html) documentation.

### Implementing a Predicate



## Mappers



## Sorters



## Collectors



## Conclusion

This is only the basics.
