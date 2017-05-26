STAR_JAR=target/starlambdawars-long-time-ago.jar
BASE_CP=org.starlambdawars
DATA_FILE=src/main/resources/formula_one_2010_2012.csv

.PHONY: clean

clean:
	mvn clean

compile:
	mvn clean install -DskipTests

filter: compile
	java -cp $(STAR_JAR) $(BASE_CP).finder.StarWarsFilterExample

mapper: compile
	java -cp $(STAR_JAR) $(BASE_CP).mapper.StarWarsMovieMapperExample

sorter: compile
	java -cp $(STAR_JAR) $(BASE_CP).sorter.StarWarsMovieSorterExample

collector: compile
	java -cp $(STAR_JAR) $(BASE_CP).collectors.StarWarsMovieCollector
