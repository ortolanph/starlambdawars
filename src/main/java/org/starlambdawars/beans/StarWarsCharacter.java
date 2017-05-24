package org.starlambdawars.beans;

import java.util.Objects;

/**
 * Star Wars Character Bean.
 *
 * @author Paulo Henrique Ortolan
 */
public class StarWarsCharacter {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlayedBy() {
        return playedBy;
    }

    public void setPlayedBy(String playedBy) {
        this.playedBy = playedBy;
    }

    public ForceAlignment getForceAlignment() {
        return forceAlignment;
    }

    public void setForceAlignment(ForceAlignment forceAlignment) {
        this.forceAlignment = forceAlignment;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StarWarsCharacter{");
        sb.append("name='").append(name).append('\'');
        sb.append(", playedBy='").append(playedBy).append('\'');
        sb.append(", forceAlignment=").append(forceAlignment);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StarWarsCharacter that = (StarWarsCharacter) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    private String name;
    private String playedBy;
    private ForceAlignment forceAlignment;
}