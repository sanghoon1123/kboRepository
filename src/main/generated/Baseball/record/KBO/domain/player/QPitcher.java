package Baseball.record.KBO.domain.player;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPitcher is a Querydsl query type for Pitcher
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPitcher extends EntityPathBase<Pitcher> {

    private static final long serialVersionUID = -1178842379L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPitcher pitcher = new QPitcher("pitcher");

    public final QPlayer _super;

    //inherited
    public final NumberPath<Integer> age;

    public final NumberPath<Double> era = createNumber("era", Double.class);

    //inherited
    public final NumberPath<Integer> game;

    public final NumberPath<Integer> hold = createNumber("hold", Integer.class);

    //inherited
    public final NumberPath<Long> id;

    public final NumberPath<Double> ip = createNumber("ip", Double.class);

    public final NumberPath<Integer> lose = createNumber("lose", Integer.class);

    //inherited
    public final StringPath name;

    public final EnumPath<PitcherPosition> pitcherPosition = createEnum("pitcherPosition", PitcherPosition.class);

    //inherited
    public final StringPath playerType;

    public final NumberPath<Integer> save = createNumber("save", Integer.class);

    public final NumberPath<Integer> strikeouts = createNumber("strikeouts", Integer.class);

    // inherited
    public final Baseball.record.KBO.domain.team.QTeam team;

    public final NumberPath<Integer> win = createNumber("win", Integer.class);

    public QPitcher(String variable) {
        this(Pitcher.class, forVariable(variable), INITS);
    }

    public QPitcher(Path<? extends Pitcher> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPitcher(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPitcher(PathMetadata metadata, PathInits inits) {
        this(Pitcher.class, metadata, inits);
    }

    public QPitcher(Class<? extends Pitcher> type, PathMetadata metadata, PathInits inits) {
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

