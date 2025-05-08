package Baseball.record.KBO.domain.team;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class TeamRecord {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Team team;

    @Enumerated(EnumType.STRING)
    private TeamName teamName;

    private LocalDate date;
    @Column(name = "team_rank")
    private int teamRank;
    private int win;
    private int lose;
    private int draw;
    private double winningRate;

}
