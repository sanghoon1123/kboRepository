package Baseball.record.KBO.repository;

import Baseball.record.KBO.domain.player.Pitcher;
import Baseball.record.KBO.domain.player.PitcherPosition;
import Baseball.record.KBO.domain.team.TeamName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PitcherRepository extends JpaRepository<Pitcher, Long> {
    List<Pitcher> findByTeamName(TeamName teamName);
    List<Pitcher> findByPosition(PitcherPosition position);
    List<Pitcher> findByTeamNameAndPosition(TeamName teamName, PitcherPosition position);

}
