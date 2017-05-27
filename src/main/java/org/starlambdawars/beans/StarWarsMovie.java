package org.starlambdawars.beans;

import java.time.LocalDate;
import java.util.List;

/**
 * Star Wars Movie Bean.
 *
 * @author Paulo Henrique Ortolan
 */
public class StarWarsMovie {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getChronologicalOrder() {
        return chronologicalOrder;
    }

    public void setChronologicalOrder(int chronologicalOrder) {
        this.chronologicalOrder = chronologicalOrder;
    }

    public int getPersonalOrder() {
        return personalOrder;
    }

    public void setPersonalOrder(int personalOrder) {
        this.personalOrder = personalOrder;
    }

    public MovieType getType() {
        return type;
    }

    public void setType(MovieType type) {
        this.type = type;
    }

    public List<StarWarsCharacter> getMainCharacters() {
        return mainCharacters;
    }

    public void setMainCharacters(List<StarWarsCharacter> mainCharacters) {
        this.mainCharacters = mainCharacters;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StarWarsMovie{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", releaseDate=").append(releaseDate);
        sb.append(", director='").append(director).append('\'');
        sb.append(", chronologicalOrder=").append(chronologicalOrder);
        sb.append(", personalOrder=").append(personalOrder);
        sb.append(", type=").append(type);
        sb.append(", mainCharacters=").append(mainCharacters);
        sb.append('}');
        return sb.toString();
    }

    private int id;
    private String title;
    private LocalDate releaseDate;
    private String director;
    private int chronologicalOrder;
    private int personalOrder;
    private MovieType type;
    private List<StarWarsCharacter> mainCharacters;
}
