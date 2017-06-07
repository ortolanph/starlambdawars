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

Mappers no Java 8 são conversores. Não estão relacionados com a estutura de dados ```Map```. O método ```map``` recebe uma implementação da interface ```java.util.function.Function``` que realiza a conversão. Assim como ```Predicate```, a interface ```Function``` possui um método para ser implmentado que é o método ```apply``` e outros métodos default e estáticos.

Os métodos default são utilizados para composição, mas infelizmente isso não será discutido nesse artigo. O método de classe se refere à uma função especial que é a função identidade (identity).

Os exemplos desse artigo são simples. Não são complexos. Estão aqui somente para introduzir a noção de mapas dentro de uma encadeamento de streams.

### Escrevendo uma Function

Se faz necessário obter uma lista de todos os filmes em uma coleção. Antes de Java 8, isso podia ser feito criando uma instância de ```List```, interando sobre a lista de todos filmes com um for-each (ou até um loop for tradicional) e adicionando cada item à lista criada.

Isso está parecendo muito trabalho, de fato!  Vamos deixar mais fácil.

A primeira maneira para solucionar, é implementar a interface ```Function```. Fácil! O código abaixo trata dessa abordagem:

```java
public class TitleFunction implements Function<StarWarsMovie, String> {

    @Override
    public String apply(StarWarsMovie starWarsMovie) {
        return starWarsMovie.getTitle();
    }
}
```

E essa função pode ser utilizada na chamada do método ```map``` no encadeamento de strems dessa maneira:

```java
public List<String> allTitles() {
    return movies
            .stream()
            .map(titleFunction)
            .collect(Collectors.toList());
}
```

No exemplo acima, a variável ```titleFunction``` é uma instância da classe ```TitleFunction``` implementada antes. Para facilitar as coisas, é possível utilizar a notação arrow function para implementar essa função. Observe o código abaixo:

```java
public List<String> allTitles() {
    return movies
            .stream()
            .map(m -> m.getTitle())
            .collect(Collectors.toList());
}
```

### ```flatMap``` e ```distinct```

Outra maneira de converter objetos é usar Flatmaps. O comportamento é parecido com o do map, mas retornará zero ou mais ocorrências. O código abaixo ilustra o uso de Flatmaps:

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

O código retorna uma lista de todos os personagens de todos os filmes distintamente. Se um filme não possui personagens, nada será adicionado (como é o caso dos filmes da trilogia Qatsi). Star Wars possui muitos personagens memoráveis e a lista de personagens nunca retornará vazia.

Encadeando ```distinct()``` irá resultar em uma lista sem repetições. Para isso ser possível, o desenvolvedor deve implementar os métodos ```hashCode()``` e ```equals()``` com o intuito de obter melhores resultados.

Simples e divertido! Outra maneira de implementar uma função customizada é criar uma interface anotada com ```@FunctionalInterface```. Como os exemplos desse artigo não possui muitos exemplos complexos, não há exemplo desse recurso.

Veja os exemplos implementados no pacote ```org.starlambdawars.mapper```. Tente implementar funções para obter outras informações do conjunto de dados.

## Sorters

Quando se fala em ordenação, se pensa na interface ```java.util.Comparator```. O método chave no encadeamento de streams é o método ```sort```.

Foi adicionado à interface ```Comparator``` vários métodos default para deixar o desenvolvimento de um Comparator mais fácil. Não serão discutidos todos os métodos default nesse artigo, somente o método ```comparing```.

### Implementando um ```Comparator```

Implementar um Comparator não é uma tarefa complexa. Quem programa em Java há um tempo já deve ter implementado um. Por causa disso não será mostrada nesse artigo uma implmenetação dessa interface.

Existem duas maneiras de usar um Comparator: implementar usando a arrow notation ou usando o método default ```comparing```.

Vamos implementar um Comparator usando o campo ```chronologicalOrder```. Ordenando os filmes pela ordem cronológica, colocará os episódio em ordem crescente numérica de episódios do Star Wars. Veja o código abaixo:

```java
List<StarWarsMovie> myMovies = movies
        .stream()
        .sorted((m1, m2) -> m1.getChronologicalOrder() - m2.getChronologicalOrder())
        .collect(Collectors.toList());
```

A síntaxe é um pouco difereten de outras implementações, pois o método ```compare``` necessita de dois parâmetros, assim como se fosse implementada.

### Usando um ```Comparator```

Na interface ```Comparator``` existem vários métodos de classe que podem ser utilizados para evitar implementações desnecessárias. Um desses métodos é o método ```comparing```, que espera um objeto do tipo ```Function``` como parâmetro. Não é necessário implementar uma função para utilizá-lo, pode ser utilizada a notação double-colon (```::```) informando a classe e o nome do método, exemplo: ```Class::method```. Isso será convertido em função.

A implementação de outro exemplo, utilizando o campo ```releaseDate``` fica conforme trecho de código a seguir:

```java
List<StarWarsMovie> myMovies = movies
        .stream()
        .sorted(Comparator.comparing(StarWarsMovie::getReleaseDate))
        .collect(Collectors.toList());
```

### Invertendo uma lista

Seguindo o exemplo anterior, fica simples escrever um código para modificar a ordenação dos dados. Se faz necessário somente uma coisa: uma chamada para o método ```reversed()```.

```java
List<StarWarsMovie> myMovies = movies
        .stream()
        .sorted(Comparator.comparing(StarWarsMovie::getReleaseDate).reversed())
        .collect(Collectors.toList());
```

Com isso, ordenar dados fica fácil!

Veja o pacote ```org.starlambdawars.sorter``` para ver os exemplos implementados com Comparator para odenar dados. Modifique o campo ```personalOrder``` no arquivo de dados para ver qual o output do método ```personalOrderSorting()```.

## Collectors

Todos os exemplos terminam com uma chamada ao método ```collect```. Este é um método especial que encerra o ```Stream``` aberto pelo método ```stream()``` reduzindo para uma coleção.

Existe uma classe que facilita o uso desse método que é a classe utilitária ```Collectors```. Os três principais métodos dessa classe são:

1. ```toList``` que cria um ```List```
2. ```toSet``` que cria um ```Set```
3. ```toMap``` que cria um ```Map```

### ```toList``` e ```toSet```

Estes são métodos fáceis de usar. É só chamar um dos dois que irá resultar em um ```List``` ou um ```Set```. Mas lembre-se: ```Set``` é um tipo de coleção que **não** permite repetição, então, é necessário de implementar os métodos ```equals``` e ```hashCode``` na classe parametrizada antes de usar esses dois métodos.

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

Para construir um ```Map``` são necessárias duas coisas: um tipo para a chave e um tipo para o valor. O método ```toMap``` necessita pelo menos de dois parâmetros:

1. uma ```Function``` que defina a chave
2. uma ```Function``` que defina o valor

No exemplo a seguir, o ```Map``` resultante irá conter uma chave enum, do enum ```ForceAlignment```, e o valor será uma lista de personagens. Primeiro é preciso transformar o stream ```StarWarsMovie```em um stream de ```ForceAlignment``` usando o método ```map```. Por fim, é necessário criar um stream distinto de ```ForceAlignment``` e realizar a chamada para o método ```collect``` para criar o ```Map```.

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

A chave será construída com uma função identidade que pode ser escrita como ```f -> f```. O valor deve ser uma lista de ```String``` criada pelo método ```findByForceAlignment```. A implementação do mesmo está na seção anterior.

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
