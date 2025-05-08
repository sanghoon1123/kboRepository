package Baseball.record.KBO.domain.player;

import Baseball.record.KBO.domain.team.Team;
import Baseball.record.KBO.dto.BatterDto;
import Baseball.record.KBO.dto.PlayerDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "player_type")
@Getter
public abstract class Player {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate birthDate;

    private int game;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    @JsonBackReference
    private Team team;

    @Column(name = "player_type", insertable = false, updatable = false)
    private String playerType;

    public Player(String name, LocalDate birthDate, int game, Team team) {
        this.name = name;
        this.birthDate = birthDate;
        this.game = game;
        this.team = team;
    }

    protected Player() {}

    public void updateCommonFields(PlayerDto playerDto, Team team) {
        this.name = playerDto.getName();
        this.birthDate = playerDto.getBirthDate(); // 변경
        this.game = playerDto.getGame();
        this.team = team;
    }

    public abstract void updateFields(PlayerDto playerDto, Team team);


}

