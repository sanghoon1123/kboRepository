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
import org.springframework.core.annotation.Order;

import javax.annotation.Nullable;
import java.time.LocalDate;

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

    @Enumerated(EnumType.STRING)
    private BatterPosition batterPosition;

    public Batter(String name, LocalDate birthDate, int game, Team team,
                  double average, int hit, int homeRun, int rbi,
                  double ops, @Nullable BatterPosition batterPosition) {
        super(name, birthDate, game, team);
        this.average = average;
        this.hit = hit;
        this.homeRun = homeRun;
        this.rbi = rbi;
        this.ops = ops;
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
            this.batterPosition = batterDto.getBatterPosition();
        } else {
            throw new IllegalArgumentException("잘못된 타입입니다.");
        }
    }

}
