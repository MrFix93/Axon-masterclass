package nl.infosupport.demo.matchmaker.query.readmodels;

import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Invite {

    @Id
    String id;
    String player1;
    String player2;

    @Setter
    String status;
}
