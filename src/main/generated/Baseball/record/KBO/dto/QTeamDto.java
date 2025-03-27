package Baseball.record.KBO.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * Baseball.record.KBO.dto.QTeamDto is a Querydsl Projection type for TeamDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QTeamDto extends ConstructorExpression<TeamDto> {

    private static final long serialVersionUID = -536774242L;

    public QTeamDto(com.querydsl.core.types.Expression<Integer> win, com.querydsl.core.types.Expression<Integer> lose, com.querydsl.core.types.Expression<Integer> draw, com.querydsl.core.types.Expression<Baseball.record.KBO.domain.team.TeamName> name) {
        super(TeamDto.class, new Class<?>[]{int.class, int.class, int.class, Baseball.record.KBO.domain.team.TeamName.class}, win, lose, draw, name);
    }

}

