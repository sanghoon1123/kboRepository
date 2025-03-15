package Baseball.record.KBO.domain.player;

import Baseball.record.KBO.domain.team.Team;
import Baseball.record.KBO.dto.BatterDto;
import Baseball.record.KBO.dto.PlayerDto;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "player_type")
@Getter
public abstract class Player {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int age;
    private int game;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Column(name = "player_type", insertable = false, updatable = false)
    private String playerType;

    public Player(String name, int age, int game, Team team) {
        this.name = name;
        this.age = age;
        this.game = game;
        this.team = team;
    }

    protected Player() {
    }

    public void updateCommonFields(PlayerDto playerDto, Team team) {
        this.name = playerDto.getName();
        this.age = playerDto.getAge();
        this.game = playerDto.getGame();
        this.team = team;
    }

    public abstract void updateFields(PlayerDto playerDto, Team team);

}
