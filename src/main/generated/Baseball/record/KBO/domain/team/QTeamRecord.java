package Baseball.record.KBO.domain.team;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTeamRecord is a Querydsl query type for TeamRecord
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTeamRecord extends EntityPathBase<TeamRecord> {

    private static final long serialVersionUID = 409918154L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTeamRecord teamRecord = new QTeamRecord("teamRecord");

    public final DatePath<java.time.LocalDate> date = createDate("date", java.time.LocalDate.class);

    public final NumberPath<Integer> draw = createNumber("draw", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> lose = createNumber("lose", Integer.class);

    public final QTeam team;

    public final EnumPath<TeamName> teamName = createEnum("teamName", TeamName.class);

    public final NumberPath<Integer> teamRank = createNumber("teamRank", Integer.class);

    public final NumberPath<Integer> win = createNumber("win", Integer.class);

    public final NumberPath<Double> winningRate = createNumber("winningRate", Double.class);

    public QTeamRecord(String variable) {
        this(TeamRecord.class, forVariable(variable), INITS);
    }

    public QTeamRecord(Path<? extends TeamRecord> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTeamRecord(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTeamRecord(PathMetadata metadata, PathInits inits) {
        this(TeamRecord.class, metadata, inits);
    }

    public QTeamRecord(Class<? extends TeamRecord> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.team = inits.isInitialized("team") ? new QTeam(forProperty("team")) : null;
    }

}

