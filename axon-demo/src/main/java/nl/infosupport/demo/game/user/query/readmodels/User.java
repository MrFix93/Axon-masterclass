package nl.infosupport.demo.game.user.query.readmodels;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class User {

    private String email;
    private String name;
    private String country;
    private String biography;

}
