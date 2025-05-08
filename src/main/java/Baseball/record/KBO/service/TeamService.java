package Baseball.record.KBO.service;

import Baseball.record.KBO.domain.team.Team;
import Baseball.record.KBO.domain.team.TeamName;
import Baseball.record.KBO.domain.team.TeamRecord;
import Baseball.record.KBO.dto.BatterDto;
import Baseball.record.KBO.dto.PitcherDto;
import Baseball.record.KBO.dto.PlayerDto;
import Baseball.record.KBO.dto.TeamDto;
import Baseball.record.KBO.repository.PlayerRepository;
import Baseball.record.KBO.repository.TeamRecordRepository;
import Baseball.record.KBO.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final TeamRecordRepository teamRecordRepository;

    public Team save(Team team){
        if (teamRepository.existsByName(team.getName())) {
            throw new IllegalStateException("이미 존재하는 팀입니다" + team.getName());
        }

        return teamRepository.save(team);
    }

    public List<Team> findAll(){
        return teamRepository.findAll();
    }

    public void delete(Long id){
        teamRepository.deleteById(id);
    }

    public Team findOne(Long id){
        return teamRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Team id not found : "+id));
    }

    public Team findTeamName(TeamName teamName){
        Team byName = teamRepository.findByName(teamName).orElseThrow(() ->
                new RuntimeException("팀을 찾을 수 없습니다 : " + teamName));

        return byName;

    }

    public List<TeamDto> findByTeamName(TeamName teamName){
        List<Team> findTeam = teamRepository.findTeamByTeamName(teamName);
        return findTeam.stream().map(t -> new TeamDto(t)).collect(toList());
    }


    public Team findOrCreateByKoreanName(String koreanName) {
        TeamName teamName = TeamName.fromKoreanName(koreanName)
                .orElseThrow(() -> new IllegalArgumentException("알 수 없는 팀: " + koreanName));

        return teamRepository.findByName(teamName)
                .orElseGet(() -> {
                    Team team = new Team();
                    team.setName(teamName);
                    return teamRepository.save(team);
                });
    }

    public void saveTeamRecords(List<TeamRecord> records) {
        teamRecordRepository.saveAll(records);
    }
}
