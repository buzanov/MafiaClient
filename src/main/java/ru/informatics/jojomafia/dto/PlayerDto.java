package ru.informatics.jojomafia.dto;

public class PlayerDto {
    boolean isAlive;
    String role;
    String name;

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getName() + " " + getRole() + " " + isAlive;
    }
}
