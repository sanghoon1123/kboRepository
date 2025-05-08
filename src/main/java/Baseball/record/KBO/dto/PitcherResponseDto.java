package Baseball.record.KBO.dto;

import Baseball.record.KBO.domain.player.Pitcher;
import Baseball.record.KBO.domain.player.PitcherPosition;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PitcherResponseDto {
    private String name;
    private LocalDate birthDate;
    private int game;
    private int win;
    private int lose;
    private double ip;
    private double era;
    private int strikeouts;
    private int hold;
    private int save;
    private PitcherPosition position;
    private String teamName;

    public PitcherResponseDto(Pitcher pitcher) {
        this.name = pitcher.getName();
        this.birthDate = pitcher.getBirthDate();
        this.game = pitcher.getGame();
        this.win = pitcher.getWin();
        this.lose = pitcher.getLose();
        this.ip = pitcher.getIp();
        this.era = pitcher.getEra();
        this.strikeouts = pitcher.getStrikeouts();
        this.hold = pitcher.getHold();
        this.save = pitcher.getSave();
        this.position = pitcher.getPosition();
        this.teamName = pitcher.getTeam().getName().toString();
    }

    public PitcherResponseDto() {
    }
}
