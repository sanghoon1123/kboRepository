package Baseball.record.KBO.domain.player;

import Baseball.record.KBO.domain.team.Team;
import Baseball.record.KBO.dto.PitcherDto;
import Baseball.record.KBO.dto.PlayerDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import javax.annotation.Nullable;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("PITCHER")
@Getter
public class Pitcher extends Player {

    private int win;
    private int lose;
    private double ip;
    private double era;
    private int strikeouts;
    private int hold;
    private int save;
    @Enumerated(EnumType.STRING) @Nullable
    private PitcherPosition position;

    public Pitcher(String name, LocalDate birthDate, int game, Team team,
                   int win, int lose, double ip, double era,
                   int strikeouts, int hold, int save, PitcherPosition position) {
        super(name, birthDate, game, team);
        this.win = win;
        this.lose = lose;
        this.ip = ip;
        this.era = era;
        this.strikeouts = strikeouts;
        this.hold = hold;
        this.save = save;
        this.position = position;
    }

    protected Pitcher() {}

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
            this.position = position;
        } else {
            throw new IllegalArgumentException("잘못된 DTO 타입입니다.");
        }
    }

}

