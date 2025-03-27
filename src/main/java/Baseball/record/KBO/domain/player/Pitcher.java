package Baseball.record.KBO.domain.player;

import Baseball.record.KBO.domain.team.Team;
import Baseball.record.KBO.dto.PitcherDto;
import Baseball.record.KBO.dto.PlayerDto;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@DiscriminatorValue("PITCHER")
@Getter
public class Pitcher extends Player{
    private int win;
    private int lose;
    private double ip;
    private double era;
    private int strikeouts;
    private int hold;
    private int save;

    @Enumerated(EnumType.STRING)
    private PitcherPosition pitcherPosition;

    public Pitcher(String name, int age, int game,Team team,
                   int win, int lose, double ip, double era, int strikeouts, int hold,
                   int save, PitcherPosition pitcherPosition) {
        super(name, age, game   , team);
        this.win = win;
        this.lose = lose;
        this.ip = ip;
        this.era = era;
        this.strikeouts = strikeouts;
        this.hold = hold;
        this.save = save;
        this.pitcherPosition = pitcherPosition;
    }

    protected Pitcher() {
    }

    @Override
    public void updateFields(PlayerDto playerDto, Team team) {
        super.updateCommonFields(playerDto, team);
        if (playerDto instanceof PitcherDto pitcherDto) {
            this.win = pitcherDto.getWin();
            this.lose = pitcherDto.getLose();
            this.ip = pitcherDto.getIp();
            this.era = pitcherDto.getEra();
            this.strikeouts = pitcherDto.getStrikeouts();
            this.hold = pitcherDto.getHold();
            this.save = pitcherDto.getSave();
            this.pitcherPosition = pitcherDto.getPitcherPosition();
        } else {
            throw new IllegalArgumentException("잘못된 DTO 타입입니다.");
        }
    }
}
