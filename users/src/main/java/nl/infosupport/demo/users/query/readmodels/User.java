package nl.infosupport.demo.users.query.readmodels;

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
