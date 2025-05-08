package Baseball.record.KBO.dto;

import Baseball.record.KBO.domain.team.Team;
import Baseball.record.KBO.domain.team.TeamName;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.util.List;

@Getter
public class TeamDto {


    private final TeamName name;

    @QueryProjection
    public TeamDto(int win, int lose, int draw, TeamName name) {
        this.name = name;
    }

    public TeamDto(Team team) {
        name = team.getName();
    }


}
