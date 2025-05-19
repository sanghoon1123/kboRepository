package Baseball.record.KBO.dto;

import Baseball.record.KBO.domain.player.Pitcher;
import Baseball.record.KBO.domain.player.PitcherPosition;
import Baseball.record.KBO.domain.team.Team;
import Baseball.record.KBO.domain.team.TeamName;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PitcherDto extends PlayerDto{
    private final int win;
    private final int lose;
    private final double ip;
    private final double era;
    private final int strikeouts;
    private final int hold;
    private final int save;
    private final PitcherPosition position;
    private Boolean qualifiedInnings;



    @QueryProjection
    public PitcherDto(String name, LocalDate birthDate, int game, TeamName teamName,
                      String playerType, int win, int lose, double ip,
                      double era, int strikeouts, int hold, int save, PitcherPosition position, boolean qualifiedInnings) {
        super(name, birthDate, game, teamName, playerType);
        this.win = win;
        this.lose = lose;
        this.ip = ip;
        this.era = era;
        this.strikeouts = strikeouts;
        this.hold = hold;
        this.save = save;
        this.position = position;
        this.qualifiedInnings = qualifiedInnings;
    }

    public Pitcher toEntity(Team team){
        return new Pitcher(this.getName(), this.getBirthDate(), this.getGame(), team,
                this.win, this.lose, this.ip, this.era, this.strikeouts, this.hold, this.save,
                this.position, this.qualifiedInnings);
    }

    @JsonCreator
    public PitcherDto(
            @JsonProperty("name") String name,
            @JsonProperty("birthDate") LocalDate birthDate,
            @JsonProperty("game") int game,
            @JsonProperty("teamName") String teamName,
            @JsonProperty("playerType") String playerType,
            @JsonProperty("win") int win,
            @JsonProperty("lose") int lose,
            @JsonProperty("ip") double ip,
            @JsonProperty("era") double era,
            @JsonProperty("strikeouts") int strikeouts,
            @JsonProperty("hold") int hold,
            @JsonProperty("save") int save,
            @JsonProperty("position") PitcherPosition position,
            @JsonProperty("qualifiedInnings") boolean qualifiedInnings
    ) {
        super(name, birthDate, game, TeamName.valueOf(teamName), playerType); // 부모 생성자 호출
        this.win = win;
        this.lose = lose;
        this.ip = ip;
        this.era = era;
        this.strikeouts = strikeouts;
        this.hold = hold;
        this.save = save;
        this.position = position;
        this.qualifiedInnings = qualifiedInnings;
    }

}
