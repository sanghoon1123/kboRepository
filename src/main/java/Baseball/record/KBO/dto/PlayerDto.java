package Baseball.record.KBO.dto;

import Baseball.record.KBO.domain.player.Player;
import Baseball.record.KBO.domain.team.Team;
import Baseball.record.KBO.domain.team.TeamName;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "playerType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = BatterDto.class, name = "BATTER"),
        @JsonSubTypes.Type(value = PitcherDto.class, name = "PITCHER")
})
public abstract class PlayerDto {

    private String name;
    private int age;
    private int game;
    private TeamName teamName;
    private String playerType;

    public PlayerDto(String name, int age, int game, TeamName teamName, String playerType) {
        this.name = name;
        this.age = age;
        this.game = game;
        this.teamName = teamName;
        this.playerType = playerType;
    }

}
