package Baseball.record.KBO.dto;

import Baseball.record.KBO.domain.player.Batter;
import Baseball.record.KBO.domain.player.BatterPosition;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BatterResponseDto {
    private String name;
    private LocalDate birthDate;
    private int game;
    private double average;
    private int hit;
    private int homeRun;
    private int rbi;
    private double ops;
    private BatterPosition batterPosition;
    private String teamName;

    public BatterResponseDto(Batter batter) {
        this.name = batter.getName();
        this.birthDate = batter.getBirthDate();
        this.game = batter.getGame();
        this.average = batter.getAverage();
        this.hit = batter.getHit();
        this.homeRun = batter.getHomeRun();
        this.rbi = batter.getRbi();
        this.ops = batter.getOps();
        this.batterPosition = batter.getBatterPosition();
        this.teamName = batter.getTeam().getName().toString();
    }

    public BatterResponseDto() {
    }
}
