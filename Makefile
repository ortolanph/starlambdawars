STAR_JAR=target/starlambdawars-jar-with-dependencies.jar
BASE_CP=org.starlambdawars
DATA_FILE=src/main/resources/all_star_wars_movies.json

.PHONY: clean

clean:
	@mvn clean

compile:
	@mvn clean install -DskipTests

assemble:
	@mvn assembly:assembly

filter:
	@java -cp $(STAR_JAR) $(BASE_CP).finder.StarWarsFilterExample $(DATA_FILE)

finder:
	@java -cp $(STAR_JAR) $(BASE_CP).finder.StarWarsDataFinderExample $(DATA_FILE)

mapper:
	@java -cp $(STAR_JAR) $(BASE_CP).mapper.StarWarsMovieMapperExample $(DATA_FILE)

sorter:
	@java -cp $(STAR_JAR) $(BASE_CP).sorter.StarWarsSorterExample $(DATA_FILE)

order:
	@java -cp $(STAR_JAR) $(BASE_CP).sorter.StarWarsMovieSorterExample $(DATA_FILE)

collector:
	@java -cp $(STAR_JAR) $(BASE_CP).collectors.StarWarsMovieCollectorExample $(DATA_FILE)
