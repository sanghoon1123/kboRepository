package Baseball.record.KBO.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * Baseball.record.KBO.dto.QBatterDto is a Querydsl Projection type for BatterDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QBatterDto extends ConstructorExpression<BatterDto> {

    private static final long serialVersionUID = -737352017L;

    public QBatterDto(com.querydsl.core.types.Expression<String> name, com.querydsl.core.types.Expression<Integer> age, com.querydsl.core.types.Expression<Integer> game, com.querydsl.core.types.Expression<Baseball.record.KBO.domain.team.TeamName> teamName, com.querydsl.core.types.Expression<String> playerType, com.querydsl.core.types.Expression<Double> average, com.querydsl.core.types.Expression<Integer> hit, com.querydsl.core.types.Expression<Integer> homeRun, com.querydsl.core.types.Expression<Integer> rbi, com.querydsl.core.types.Expression<Double> ops, com.querydsl.core.types.Expression<Integer> steal, com.querydsl.core.types.Expression<Baseball.record.KBO.domain.player.BatterPosition> batterPosition) {
        super(BatterDto.class, new Class<?>[]{String.class, int.class, int.class, Baseball.record.KBO.domain.team.TeamName.class, String.class, double.class, int.class, int.class, int.class, double.class, int.class, Baseball.record.KBO.domain.player.BatterPosition.class}, name, age, game, teamName, playerType, average, hit, homeRun, rbi, ops, steal, batterPosition);
    }

}

