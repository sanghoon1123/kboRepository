package Baseball.record.KBO.repository;

import Baseball.record.KBO.domain.team.Team;
import Baseball.record.KBO.domain.team.TeamName;
import Baseball.record.KBO.dto.BatterDto;
import Baseball.record.KBO.dto.PitcherDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    boolean existsByName(TeamName name);
    Optional<Team> findByName(TeamName name);

    @Query("select t from Team t where t.name =:name")
    List<Team> findTeamByTeamName(@Param("name") TeamName name);



}
