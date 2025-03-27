package Baseball.record.KBO.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * Baseball.record.KBO.dto.QPitcherDto is a Querydsl Projection type for PitcherDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QPitcherDto extends ConstructorExpression<PitcherDto> {

    private static final long serialVersionUID = 753481174L;

    public QPitcherDto(com.querydsl.core.types.Expression<String> name, com.querydsl.core.types.Expression<Integer> age, com.querydsl.core.types.Expression<Integer> game, com.querydsl.core.types.Expression<Baseball.record.KBO.domain.team.TeamName> teamName, com.querydsl.core.types.Expression<String> playerType, com.querydsl.core.types.Expression<Integer> win, com.querydsl.core.types.Expression<Integer> lose, com.querydsl.core.types.Expression<Double> ip, com.querydsl.core.types.Expression<Double> era, com.querydsl.core.types.Expression<Integer> strikeouts, com.querydsl.core.types.Expression<Integer> hold, com.querydsl.core.types.Expression<Integer> save, com.querydsl.core.types.Expression<Baseball.record.KBO.domain.player.PitcherPosition> pitcherPosition) {
        super(PitcherDto.class, new Class<?>[]{String.class, int.class, int.class, Baseball.record.KBO.domain.team.TeamName.class, String.class, int.class, int.class, double.class, double.class, int.class, int.class, int.class, Baseball.record.KBO.domain.player.PitcherPosition.class}, name, age, game, teamName, playerType, win, lose, ip, era, strikeouts, hold, save, pitcherPosition);
    }

}

