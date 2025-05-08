package Baseball.record.KBO.repository;

import Baseball.record.KBO.domain.player.Batter;
import Baseball.record.KBO.domain.player.BatterPosition;
import Baseball.record.KBO.domain.team.TeamName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BatterRepository extends JpaRepository<Batter, Long> {
    List<Batter> findByTeamName(TeamName teamName);
    List<Batter> findByBatterPosition(BatterPosition batterPosition);
    List<Batter> findByTeamNameAndBatterPosition(TeamName teamName, BatterPosition batterPosition);
}
