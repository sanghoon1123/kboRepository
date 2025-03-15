package Baseball.record.KBO.domain.team;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
public class Team {

    @Id @GeneratedValue
    private Long id;

    private int win;
    private int lose;
    private int draw;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private TeamName name;

    public Team(TeamName name) {
        this.name = name;
        this.win = name.getWin();
        this.lose = name.getLose();
        this.draw = name.getDraw();
    }

    protected Team() {
    }
}
