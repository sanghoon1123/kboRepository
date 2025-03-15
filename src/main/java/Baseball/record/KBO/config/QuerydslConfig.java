package Baseball.record.KBO.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuerydslConfig {

    private final EntityManager entityManager;

    @Autowired
    public QuerydslConfig(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Bean
    public JPAQueryFactory queryFactory(){
        return new JPAQueryFactory(entityManager);
    }
}
