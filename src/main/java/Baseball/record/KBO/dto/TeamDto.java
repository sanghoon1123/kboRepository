package Baseball.record.KBO.dto;

import Baseball.record.KBO.domain.team.Team;
import Baseball.record.KBO.domain.team.TeamName;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.util.List;

@Getter
public class TeamDto {

    private final int win;
    private final int lose;
    private final int draw;
    private final TeamName name;

    @QueryProjection
    public TeamDto(int win, int lose, int draw, TeamName name) {
        this.win = win;
        this.lose = lose;
        this.draw = draw;
        this.name = name;
    }

    public TeamDto(Team team) {
        win = team.getWin();
        lose = team.getLose();
        draw = team.getDraw();
        name = team.getName();
    }


}
