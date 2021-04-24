package nl.infosupport.demo.users.query.readmodels;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
public class User {

    @Id
    private String id;
    private String email;
    private String name;
    private String country;
    private String biography;

}
