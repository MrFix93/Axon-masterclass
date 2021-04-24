package nl.infosupport.demo.matchmaker.query.readmodels;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class Invite {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    String id;
    String player1;
    String player2;

    @Setter
    String status;

    public Invite(String player1, String player2, String status) {
        this.player1 = player1;
        this.player2 = player2;
        this.status = status;
    }
}
