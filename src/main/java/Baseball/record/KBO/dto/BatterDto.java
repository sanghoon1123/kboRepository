package Baseball.record.KBO.dto;

import Baseball.record.KBO.domain.player.Batter;
import Baseball.record.KBO.domain.player.BatterPosition;
import Baseball.record.KBO.domain.team.Team;
import Baseball.record.KBO.domain.team.TeamName;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDate;


@Getter
public class BatterDto extends PlayerDto{
    private double average;
    private int hit;
    private int homeRun;
    private int rbi;
    private double ops;
    private BatterPosition batterPosition;

    @QueryProjection
    public BatterDto(String name, LocalDate birthDate, int game, TeamName teamName,
                     String playerType, double average, int hit, int homeRun,
                     int rbi, double ops,
                     BatterPosition batterPosition) {
        super(name, birthDate, game, teamName, playerType);
        this.average = average;
        this.hit = hit;
        this.homeRun = homeRun;
        this.rbi = rbi;
        this.ops = ops;
        this.batterPosition = batterPosition;
    }

    public BatterDto(Batter batter) {
        super(batter); // PlayerDto2 생성자 호출
        this.average = batter.getAverage();
        this.hit = batter.getHit();
        this.homeRun = batter.getHomeRun();
        this.rbi = batter.getRbi();
        this.ops = batter.getOps();
        this.batterPosition = batter.getBatterPosition();
    }


    public Batter toEntity(Team team) {
        return new Batter(this.getName(), this.getBirthDate(), this.getGame(), team,
                this.average, this.hit, this.homeRun, this.rbi,
                this.ops, this.batterPosition);

    }

    public static BatterDto fromEntity(Batter batter){
        return new BatterDto(batter.getName(), batter.getBirthDate(), batter.getGame(), batter.getTeam().getName(),
                "BATTER", batter.getAverage(), batter.getHit(), batter.getHomeRun(),
                batter.getRbi(), batter.getOps(),
                batter.getBatterPosition());
    }


    @JsonCreator
    public BatterDto(
            @JsonProperty("average") double average,
            @JsonProperty("hit") int hit,
            @JsonProperty("homeRun") int homeRun,
            @JsonProperty("rbi") int rbi,
            @JsonProperty("ops") double ops,
            @JsonProperty("batterPosition") BatterPosition batterPosition
    ) {
        super(null, null, 0, null, null);
        this.average = average;
        this.hit = hit;
        this.homeRun = homeRun;
        this.rbi = rbi;
        this.ops = ops;
        this.batterPosition = batterPosition;
    }

    public void setOps(double ops) {
        this.ops = ops;
    }

    public void setPosition(BatterPosition batterPosition) {
        this.batterPosition = batterPosition;
    }


}
