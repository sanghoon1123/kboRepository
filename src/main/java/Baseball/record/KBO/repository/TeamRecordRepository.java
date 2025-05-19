package Baseball.record.KBO.repository;

import Baseball.record.KBO.domain.team.TeamName;
import Baseball.record.KBO.domain.team.TeamRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface TeamRecordRepository extends JpaRepository<TeamRecord, Long> {

    Optional<TeamRecord> findTopByTeamNameOrderByDateDesc(TeamName teamName);
}
