package nl.infosupport.demo.game.models.users;

import javax.validation.constraints.NotNull;

public class User {

    @NotNull
    private String name;
    @NotNull
    private String email;
    @NotNull
    private String country;
    @NotNull
    private String shortBio;

    public User(String name, String email, String country, String shortBio) {
        this.name = name;
        this.email = email;
        this.country = country;
        this.shortBio = shortBio;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getCountry() {
        return this.country;
    }

    public String getShortBio() {
        return this.shortBio;
    }
}
