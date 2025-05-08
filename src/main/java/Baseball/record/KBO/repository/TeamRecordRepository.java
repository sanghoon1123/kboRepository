package Baseball.record.KBO.repository;

import Baseball.record.KBO.domain.team.TeamRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRecordRepository extends JpaRepository<TeamRecord, Long> {
}
