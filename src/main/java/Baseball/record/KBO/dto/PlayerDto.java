package Baseball.record.KBO.dto;

import Baseball.record.KBO.domain.player.Batter;
import Baseball.record.KBO.domain.team.TeamName;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;


import java.time.LocalDate;

@Getter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "playerType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = BatterDto.class, name = "BATTER"),
        @JsonSubTypes.Type(value = PitcherDto.class, name = "PITCHER")
})
public abstract class PlayerDto {

    private String name;
    private LocalDate birthDate;
    private int game;
    private TeamName teamName;
    private String playerType;

    public PlayerDto(String name, LocalDate birthDate, int game, TeamName teamName, String playerType) {
        this.name = name;
        this.birthDate = birthDate;
        this.game = game;
        this.teamName = teamName;
        this.playerType = playerType;
    }

    public PlayerDto(Batter batter) {
    }

    @Override
    public String toString() {
        return "PlayerDto{" +
                "name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", game=" + game +
                ", teamName=" + teamName +
                ", playerType='" + playerType + '\'' +
                '}';
    }


}
