package Baseball.record.KBO.dto;

import Baseball.record.KBO.domain.player.PitcherPosition;
import Baseball.record.KBO.domain.team.TeamName;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PitcherDto2 extends PlayerDto2{
    private int win;
    private int lose;
    private double ip;
    private double era;
    private int strikeouts;
    private int hold;
    private int save;
    private PitcherPosition position;
    private boolean qualifiedInnings;



    public PitcherDto2(String name, LocalDate birthDate, int game, String teamName, int win, int lose, double ip, double era, int strikeouts, int hold, int save, PitcherPosition position, boolean qualifiedInnings) {
        super();  // PlayerDto2의 기본 생성자 호출
        this.setName(name);
        this.setBirthDate(birthDate);
        this.setGame(game);
        this.setTeamName(TeamName.valueOf(teamName)); // teamName (String) → TeamName (Enum) 변환
        this.win = win;
        this.lose = lose;
        this.ip = ip;
        this.era = era;
        this.strikeouts = strikeouts;
        this.hold = hold;
        this.save = save;
        this.position = position;
        this.qualifiedInnings = qualifiedInnings;
    }


    public PitcherDto2(String name, LocalDate birthDate, int game, Long teamId, int win, int lose, double ip, double era, int strikeouts, int hold, int save, PitcherPosition position, boolean qualifiedInnings)  {
    }


}
