package Baseball.record.KBO.domain.player;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBatter is a Querydsl query type for Batter
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBatter extends EntityPathBase<Batter> {

    private static final long serialVersionUID = 1909097444L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBatter batter = new QBatter("batter");

    public final QPlayer _super;

    //inherited
    public final NumberPath<Integer> age;

    public final NumberPath<Double> average = createNumber("average", Double.class);

    public final EnumPath<BatterPosition> batterPosition = createEnum("batterPosition", BatterPosition.class);

    //inherited
    public final NumberPath<Integer> game;

    public final NumberPath<Integer> hit = createNumber("hit", Integer.class);

    public final NumberPath<Integer> homeRun = createNumber("homeRun", Integer.class);

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final StringPath name;

    public final NumberPath<Double> ops = createNumber("ops", Double.class);

    //inherited
    public final StringPath playerType;

    public final NumberPath<Integer> rbi = createNumber("rbi", Integer.class);

    public final NumberPath<Integer> steal = createNumber("steal", Integer.class);

    // inherited
    public final Baseball.record.KBO.domain.team.QTeam team;

    public QBatter(String variable) {
        this(Batter.class, forVariable(variable), INITS);
    }

    public QBatter(Path<? extends Batter> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBatter(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBatter(PathMetadata metadata, PathInits inits) {
        this(Batter.class, metadata, inits);
    }

    public QBatter(Class<? extends Batter> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QPlayer(type, metadata, inits);
        this.age = _super.age;
        this.game = _super.game;
        this.id = _super.id;
        this.name = _super.name;
        this.playerType = _super.playerType;
        this.team = _super.team;
    }

}

