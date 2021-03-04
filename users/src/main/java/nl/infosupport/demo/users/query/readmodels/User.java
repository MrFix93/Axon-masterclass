package nl.infosupport.demo.users.query.readmodels;

import lombok.*;
import org.springframework.data.annotation.Id;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class User {

    @Id
    private String email;
    private String name;
    private String country;
    private String biography;

}
