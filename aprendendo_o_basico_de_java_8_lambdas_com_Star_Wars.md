# Aprendendo o básico de java 9 lambdas com Star Wars

## Índice

1. Introdução
    1. Java 8 Lambdas
    2. Código Fonte
    3. Executando os exemplos
2. Filters
   1. Implementando um predicate
3. Mappers
   1. Escrevendo uma function
   2. ```flatMap``` e ```distinct```
4. Sorters
   1. Implementando um ```Comparator```
   2. Usando um ```Comparator```
   3. Revertendo uma lista
5. Collectors
   1. ```toList()``` e ```toSet```
   2. ```toMap```
6. ```toMap```
7. Conclusão

## Introdução

Star Wars é uma das franquias mais famosas deste lado do universo. Todas a trama ocorre há muito tempo atrás em uma galáxia muito muito distante. Foi responsável por gerar incontáveis fãs pelo planeta entre muitas gerações. Com oito filmes, sete da saga principal e um spin-off (até o momento de encerramento desse artigo existe um filme da saga sendo filmado e um outro spin-off) muitas coisas podem ser feitas para minerar seus dados. Esse artigo está ignorando o Star Wars Holiday Special e os filmes dos Ewoks. Sinta-se livre para inseri-los no arquivo ```all_star_wars_movie.json```.

### Java 8 Lambdas

Na linguagem de programação Java, os lambdas foram introduzidos na versão 8 e estão espalhadas pela linguagem facilitando o jeito pelo qual usamos as coisas. Verifique no pacote de IO para ver novas maneiras de carregar arquivos. Outra característica que foi introduzida com os lambdas, foram os métodos default nas interfaces. Verifique a interface Comparator, lá existem muitos métodos default. Eles serão utilizados nos exemplos desse artigo para ordenar coisas. Muitas outras coisas foram introduzidas na versão 8 para facilitar o desenvolvimento de aplicações em Java.

Esse artigo está dividido em quatro seções:

1. Filters, no qual o método ```filter``` é demonstrado.
2. Mappers, no qual os métodos ```map``` e ```flatMap``` serão discutidos
3. Sorters, no qual o método ```sort``` é usado
4. Collector, no qual o método ```collect``` é usado

### Código Fonta

Para escrever esse arquivo, foi desenvolvido um pequeno projeto. Ele está no github pronto para ser clonado. Veja a e [página do projeto](https://github.com/ortolanph/starlambdawars) e se divirta. Não se esqueça de marcar o projeto com uma estrela!

### Executando os exemplos

Leia o arquivo README.md para entender como executar os exemplos desse artigo.

## Filters

Filters significam que é desejado selecionar dados sob uma condição. Em java, condições são predicados e os mesmos estão endereçados para a interface ```java.util.function.Predicate<T>```. Há somente um método que deve ser implementado: o método ```boolean test(T t)```, que avalia a expressão e retorna um booleano. Existem ainda outros métodos default que podem ser usados ao implementar essa interface.

Acesse o link para a classe [Predicate](https://docs.oracle.com/javase/8/docs/api/java/util/function/Predicate.html) da documentação da Oracle.

### Implmentando um Predicate

Existem duas maneiras de se usar um Predicate. A primeira é implementar a interface em uma classe concreta. O desenvolvedor deve implementar todos os métodos abstratos, nesse caso, o método ```test```. É possivel ainda sobrescrever todos os métodos default, mas esse não é foco para esse artigo.

Ilustrando um exemplo com os magníficos dados, é desejado encontrar filmes que foram dirigidos por um certo diretor, George Lucas, a mente por trás da saga.

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

A classe acima mostra a implementação de um ```Predicate```.  O método test foi implementado e recebe um objeto do tipo ```StarWarMovie```, contendo todas as informações de um filme, incluindo o diretor. É necessário criar uma instância dessa classe toda vez que for desejado usá-la.

```java
public List<StarWarsMovie> findMovieByDirector(String director) {
    DirectorPredicate predicate = new DirectorPredicate(director);

    return movies
            .stream()
            .filter(m -> predicate.test(m))
            .collect(Collectors.toList());
}
```

Para começar a usar streams no Java8, o método ```stream()``` inicia a mágica. Por sua vez o método irá retornar uma instânca de ```Stream<R>``` representando o stream de objeto para ser manipulado. Legal, né?

O método ```filter``` recebe uma implementação de um ```Predicate``` como um parâmetro e retorna um ```Stream<R>```. Parece um  pouco complicado no começo, mas ao verificar os dois códigos, verifica-se que ambos combinam. Pode-se utilizar esse filtro customizado, utilizando o método ```test```.

Existe um outro modo de implementar a interface ```Predicate```. No Java 8, interfaces que possuem um método
podem ser implementados diretamente. Mas, como? Não é mais necessário criar mais uma classe concreta para isso, a implementação pode ocorrer diretamente na chamada do método usando a notação lambda do Java 8. Isso! Progredindo mais e mais! O código abaixo ilustra esse método:

```java
public List<StarWarsMovie> findMovieByDirector(String director) {
    return movies
            .stream()
            .filter(m -> m.getDirector().equals(director))
            .collect(Collectors.toList());
}
```

O que está ocorrendo aqui? Mágica pura! No método filter, temos uma arrow notation sendo utilizada para implementar um ```Predicate```. O símbolo ```m``` representa uma instância da classe ```StarWarsMovie``` e, por isso, pode-se ser chamado todos os métodos implementados, inclusive o método ```getDirector``` que obtém informação de diretor. Desse modo é possível usar o método ```equals``` para filtrar pelo diretor.

Ainda é possível encadear múltiplas chamadas de ```filter``` com o intuito de filtrar mais os dados. O código abaixo mostra o encadeamento em ação:

```java
movies
        .stream()
        .filter(m -> m.getDirector().equals(director))
        .filter(m -> m.getMainCharacters().contains(lukeSkywalker()))
        .filter(inThe80s())
        .collect(Collectors.toList())
        .forEach(m -> System.out.println(m.getTitle()));
```

O método ```lukeSkywalker()``` cria uma instância de StarWarsCharacter contendo o nome "Luke Skywalker". A implementação do método ```inThe80s()```, filtra por filmes que foram estreados na década de 80. A implementação dele seria:

```java
private static Predicate<StarWarsMovie> inThe80s() {
    LocalDate startDate = LocalDate.of(1980, Month.JANUARY, 1);
    LocalDate endDate = LocalDate.of(1989, Month.DECEMBER, 31);

    return m -> (m.getReleaseDate().isEqual(startDate) || m.getReleaseDate().isAfter(startDate))
            || (m.getReleaseDate().isEqual(endDate) || m.getReleaseDate().isBefore(endDate));
}
```

Nota-se o uso dos métodos ```collect``` e ```forEach```. Ambos serão discutidos mais para frente nesse artigo.

Veja os exemplos implementados no pacote ```org.starlambdawars.finder ``` para verificar a implmentação de um ```Predicate```. Sinta-se livre para clonar o repositório e realizar mudanças no código.

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

Para finalizar o artigo, o método ```forEach``` implementa uma estrutura for-each. É muito simples de usar! O código abaixo mostra ele em ação:

```java
sorter
        .personalOrderSorting()
        .forEach(m -> System.out.println(m.getTitle()));
```

Nesse exemplo, o método ```forEach``` faz um loop em um stream de ```StarWarsMovies``` vindo do método ```personalOrderSorting()```. A classe que realizar esse loop, não necessita conhecer nada sobre a classe ```StarWarsMovie``` ou até mesmo importá-la se for utilizada por coisas simples. Como uma facilidade da versão 8, este método pode ser chamado por uma List ou um Set sem mesmo chamar o método ```stream()```.

## Conclusão

A API de streams, juntamente com os lambdas do Java 8, ajudam programadores a criar um código melhor e mais enxuto. Pode ser complicado no começo para assimilar, mas tudo ficará mais claro com um pouco mais de uso. Alguns programadores reclamam que Java é uma linguagem verbosa, mas com streams é possível simplificar o código como vistos nos exemplos. Sinta-se livre para clonar o repositório e aprender a partir dele Modifique os dados para realizar seus testes.
