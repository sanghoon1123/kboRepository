package Baseball.record.KBO.dto;

import Baseball.record.KBO.domain.player.Pitcher;
import Baseball.record.KBO.domain.player.PitcherPosition;
import Baseball.record.KBO.domain.team.Team;
import Baseball.record.KBO.domain.team.TeamName;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class PitcherDto extends PlayerDto{
    private final int win;
    private final int lose;
    private final double ip;
    private final double era;
    private final int strikeouts;
    private final int hold;
    private final int save;
    private final PitcherPosition pitcherPosition;

    @QueryProjection
    public PitcherDto(String name, int age, int game, TeamName teamName,
                      String playerType, int win, int lose, double ip,
                      double era, int strikeouts, int hold, int save,
                      PitcherPosition pitcherPosition) {
        super(name, age, game, teamName, playerType);
        this.win = win;
        this.lose = lose;
        this.ip = ip;
        this.era = era;
        this.strikeouts = strikeouts;
        this.hold = hold;
        this.save = save;
        this.pitcherPosition = pitcherPosition;
    }

    public Pitcher toEntity(Team team){
        return new Pitcher(this.getName(), this.getAge(), this.getGame(), team,
                this.win, this.lose, this.ip, this.era, this.strikeouts, this.hold, this.save,
                this.pitcherPosition);
    }

    @JsonCreator
    public PitcherDto(
            @JsonProperty("win") int win,
            @JsonProperty("lose") int lose,
            @JsonProperty("ip") double ip,
            @JsonProperty("era") double era,
            @JsonProperty("strikeouts") int strikeouts,
            @JsonProperty("hold") int hold,
            @JsonProperty("save") int save,
            @JsonProperty("pitcherPosition") PitcherPosition pitcherPosition
    ) {
        super(null, 0, 0, null, null);
        this.win = win;
        this.lose = lose;
        this.ip = ip;
        this.era = era;
        this.strikeouts = strikeouts;
        this.hold = hold;
        this.save = save;
        this.pitcherPosition = pitcherPosition;
    }
}
