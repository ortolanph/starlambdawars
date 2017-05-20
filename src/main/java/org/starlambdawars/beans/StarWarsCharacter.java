package org.starlambdawars.beans;

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

    public String getPortrayedBy() {
        return portrayedBy;
    }

    public void setPortrayedBy(String portrayedBy) {
        this.portrayedBy = portrayedBy;
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
        sb.append(", portrayedBy='").append(portrayedBy).append('\'');
        sb.append(", forceAlignment=").append(forceAlignment);
        sb.append('}');
        return sb.toString();
    }

    private String name;
    private String portrayedBy;
    private ForceAlignment forceAlignment;
}