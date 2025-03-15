package Baseball.record.KBO.dto;

import Baseball.record.KBO.domain.player.Batter;
import Baseball.record.KBO.domain.player.BatterPosition;
import Baseball.record.KBO.domain.team.Team;
import Baseball.record.KBO.domain.team.TeamName;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;


@Getter
public class BatterDto extends PlayerDto{
    private final double average;
    private final int hit;
    private final int homeRun;
    private final int rbi;
    private final double ops;
    private final int steal;
    private final BatterPosition batterPosition;

    @QueryProjection
    public BatterDto(String name, int age, int game, TeamName teamName,
                     String playerType, double average, int hit, int homeRun,
                     int rbi, double ops, int steal,
                     BatterPosition batterPosition) {
        super(name, age, game, teamName, playerType);
        this.average = average;
        this.hit = hit;
        this.homeRun = homeRun;
        this.rbi = rbi;
        this.ops = ops;
        this.steal = steal;
        this.batterPosition = batterPosition;
    }


    public Batter toEntity(Team team) {
        return new Batter(this.getName(), this.getAge(), this.getGame(), team,
                this.average, this.hit, this.homeRun, this.rbi,
                this.ops, this.steal, this.batterPosition);

    }

    public static BatterDto fromEntity(Batter batter){
        return new BatterDto(batter.getName(), batter.getAge(), batter.getGame(), batter.getTeam().getName(),
                "BATTER", batter.getAverage(), batter.getHit(), batter.getHomeRun(),
                batter.getRbi(), batter.getOps(), batter.getSteal(),
                batter.getBatterPosition());
    }


    @JsonCreator
    public BatterDto(
            @JsonProperty("average") double average,
            @JsonProperty("hit") int hit,
            @JsonProperty("homeRun") int homeRun,
            @JsonProperty("rbi") int rbi,
            @JsonProperty("ops") double ops,
            @JsonProperty("steal") int steal,
            @JsonProperty("batterPosition") BatterPosition batterPosition
    ) {
        super(null, 0, 0, null, null);
        this.average = average;
        this.hit = hit;
        this.homeRun = homeRun;
        this.rbi = rbi;
        this.ops = ops;
        this.steal = steal;
        this.batterPosition = batterPosition;
    }
}
