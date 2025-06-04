package Baseball.record.KBO.repository;

import Baseball.record.KBO.domain.player.QBatter;
import Baseball.record.KBO.domain.player.QPitcher;
import Baseball.record.KBO.domain.player.QPlayer;
import Baseball.record.KBO.domain.team.QTeam;
import Baseball.record.KBO.domain.team.TeamName;
import Baseball.record.KBO.dto.BatterDto;
import Baseball.record.KBO.dto.DefaultPlayerDto;
import Baseball.record.KBO.dto.PitcherDto;
import Baseball.record.KBO.dto.PlayerDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class PlayerRepositoryImpl implements PlayerRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<PlayerDto> findPlayerByName(String playerName) {
        QPlayer player = QPlayer.player;
        QTeam team = QTeam.team;
        QBatter batter = QBatter.batter;
        QPitcher pitcher = QPitcher.pitcher;

        BatterDto batterResult = queryFactory
                .select(Projections.constructor(
                        BatterDto.class,
                        player.name,
                        player.birthDate,
                        player.game,
                        team.name,
                        player.playerType.stringValue(),
                        batter.average,
                        batter.hit,
                        batter.homeRun,
                        batter.rbi,
                        batter.ops,
                        batter.batterPosition
                ))
                .from(batter)
                .join(player).on(player.id.eq(batter.id))
                .join(player.team, team)
                .where(player.name.eq(playerName))
                .fetchOne();

        if (batterResult != null){
            return Optional.of(batterResult);
        }

        PitcherDto pitcherResult = queryFactory
                .select(Projections.constructor(
                        PitcherDto.class,
                        player.name,
                        player.birthDate,
                        player.game,
                        team.name,
                        player.playerType.stringValue(),
                        pitcher.win,
                        pitcher.lose,
                        pitcher.ip,
                        pitcher.era,
                        pitcher.strikeouts,
                        pitcher.hold,
                        pitcher.save,
                        pitcher.position,
                        pitcher.qualifiedInnings
                ))
                .from(pitcher)
                .join(player).on(player.id.eq(pitcher.id))
                .join(player.team, team)
                .where(player.name.eq(playerName))
                .fetchOne();

        return Optional.ofNullable(pitcherResult);
    }

    @Override
    public Page<? extends PlayerDto> findAllPlayersWithDetails(TeamName teamName, String playerType, Pageable pageable) {
        QPlayer player = QPlayer.player;
        QTeam team = QTeam.team;
        QBatter batter = QBatter.batter;
        QPitcher pitcher = QPitcher.pitcher;

        BooleanBuilder builder = new BooleanBuilder();
        if (teamName != null){
            builder.and(player.team.name.eq(teamName));
        }
        if (playerType != null){
            builder.and(player.playerType.stringValue().eq(playerType));
        }

        List<Tuple> tuples = queryFactory
                .select(player.name, player.birthDate, player.game, player.playerType, team.name,
                        batter.average, batter.hit, batter.homeRun, batter.rbi, batter.ops, batter.batterPosition,
                        pitcher.win, pitcher.lose, pitcher.ip, pitcher.era, pitcher.strikeouts,
                        pitcher.hold, pitcher.save, pitcher.position, pitcher.qualifiedInnings)
                .from(player)
                .leftJoin(player.team, team)
                .leftJoin(batter).on(player.id.eq(batter.id))
                .leftJoin(pitcher).on(player.id.eq(pitcher.id))
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 결과 변환
        List<PlayerDto> results = new ArrayList<>();
        for (Tuple tuple : tuples) {
            String playerTypeValue = tuple.get(player.playerType.stringValue());

            if ("BATTER".equals(playerTypeValue)) {
                results.add(new BatterDto(
                        tuple.get(player.name),
                        tuple.get(player.birthDate),
                        tuple.get(player.game),
                        tuple.get(team.name),
                        playerTypeValue,
                        tuple.get(batter.average),
                        tuple.get(batter.hit),
                        tuple.get(batter.homeRun),
                        tuple.get(batter.rbi),
                        tuple.get(batter.ops),
                        tuple.get(batter.batterPosition)
                ));
            } else if ("PITCHER".equals(playerTypeValue)) {
                results.add(new PitcherDto(
                        tuple.get(player.name),
                        tuple.get(player.birthDate),
                        tuple.get(player.game),
                        tuple.get(team.name),
                        playerTypeValue,
                        tuple.get(pitcher.win),
                        tuple.get(pitcher.lose),
                        tuple.get(pitcher.ip),
                        tuple.get(pitcher.era),
                        tuple.get(pitcher.strikeouts),
                        tuple.get(pitcher.hold),
                        tuple.get(pitcher.save),
                        tuple.get(pitcher.position),
                        tuple.get(pitcher.qualifiedInnings)
                ));
            } else {
                results.add(new DefaultPlayerDto(
                        tuple.get(player.name),
                        tuple.get(player.birthDate),
                        tuple.get(player.game),
                        tuple.get(team.name),
                        playerTypeValue
                ));
            }
        }

        long total = queryFactory
                .select(player.count())
                .from(player)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(results, pageable, total);
    }
}










