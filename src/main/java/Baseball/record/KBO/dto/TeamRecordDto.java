package Baseball.record.KBO.dto;

import Baseball.record.KBO.domain.team.TeamRecord;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class TeamRecordDto {

    private String teamName;       // 한글 이름
    private LocalDate date;
    private int teamRank;
    private int game;
    private int win;
    private int lose;
    private int draw;
    private double winningRate;
    private double gamesBehind;

    public TeamRecordDto(TeamRecord record) {
        this.teamName = record.getTeam().getName().getKoreanName(); // enum -> 한글
        this.date = record.getDate();
        this.teamRank = record.getTeamRank();
        this.game = record.getGame();
        this.win = record.getWin();
        this.lose = record.getLose();
        this.draw = record.getDraw();
        this.winningRate = record.getWinningRate();
        this.gamesBehind = record.getGamesBehind();
    }
}