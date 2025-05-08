package Baseball.record.KBO.dto;

import Baseball.record.KBO.domain.player.Batter;
import Baseball.record.KBO.domain.player.BatterPosition;
import Baseball.record.KBO.domain.player.Player;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// BatterDto2.java
@Getter
@Setter
@ToString(callSuper = true)
public class BatterDto2 extends PlayerDto2 {
    private double average;
    private int hit;
    private int homeRun;
    private int rbi;
    private double ops;
    private BatterPosition batterPosition;

    public BatterDto2() {
        super();
    }

    public BatterDto2(String name) {
        super(name);
    }

    public BatterDto2(Player player, Batter batter) {
        super(player);
        average = batter.getAverage();
        hit = batter.getHit();
        homeRun = batter.getHomeRun();
        rbi = batter.getRbi();
        ops = batter.getOps();
        batterPosition = batter.getBatterPosition();
    }

    public BatterDto2(Batter batter) {
        super(batter); // PlayerDto2 생성자 호출
        this.average = batter.getAverage();
        this.hit = batter.getHit();
        this.homeRun = batter.getHomeRun();
        this.rbi = batter.getRbi();
        this.ops = batter.getOps();
        this.batterPosition = batter.getBatterPosition();
    }

    public void setOps(double ops) {
        this.ops = ops;
    }

    public void setPosition(BatterPosition batterPosition) {
        this.batterPosition = batterPosition;
    }
}
