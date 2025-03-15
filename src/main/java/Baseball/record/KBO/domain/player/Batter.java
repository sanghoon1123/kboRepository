package Baseball.record.KBO.domain.player;

import Baseball.record.KBO.domain.team.Team;
import Baseball.record.KBO.dto.BatterDto;
import Baseball.record.KBO.dto.PlayerDto;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("BATTER")
@Getter
@Setter
public class Batter extends Player{
    private double average;
    private int hit;
    private int homeRun;
    private int rbi;
    private double ops;
    private int steal;

    @Enumerated(EnumType.STRING)
    private BatterPosition batterPosition;

    public Batter(String name, int age, int game, Team team,
                  double average, int hit, int homeRun, int rbi,
                  double ops, int steal, BatterPosition batterPosition) {
        super(name, age, game, team);
        this.average = average;
        this.hit = hit;
        this.homeRun = homeRun;
        this.rbi = rbi;
        this.ops = ops;
        this.steal = steal;
        this.batterPosition = batterPosition;
    }

    protected Batter(){

    }

    @Override
    public void updateFields(PlayerDto playerDto, Team team) {
        super.updateCommonFields(playerDto, team);
        if (playerDto instanceof BatterDto batterDto){
            this.average = batterDto.getAverage();
            this.hit = batterDto.getHit();
            this.homeRun = batterDto.getHomeRun();
            this.rbi = batterDto.getRbi();
            this.ops = batterDto.getOps();
            this.steal = batterDto.getSteal();
            this.batterPosition = batterDto.getBatterPosition();
        } else {
            throw new IllegalArgumentException("잘못된 타입입니다.");
        }
    }

}
