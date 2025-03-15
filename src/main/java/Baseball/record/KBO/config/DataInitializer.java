package Baseball.record.KBO.config;

import Baseball.record.KBO.domain.team.Team;
import Baseball.record.KBO.domain.team.TeamName;
import Baseball.record.KBO.repository.PlayerRepository;
import Baseball.record.KBO.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final TeamRepository teamRepository;

    @Override
    public void run(String... args) throws Exception {
        saveTeamIfNotExists(TeamName.KIA);
        saveTeamIfNotExists(TeamName.SAMSUNG);
        saveTeamIfNotExists(TeamName.LG);
        saveTeamIfNotExists(TeamName.DOOSAN);
        saveTeamIfNotExists(TeamName.KT);
        saveTeamIfNotExists(TeamName.SSG);
        saveTeamIfNotExists(TeamName.LOTTE);
        saveTeamIfNotExists(TeamName.HANWHA);
        saveTeamIfNotExists(TeamName.NC);
        saveTeamIfNotExists(TeamName.KIWOOM);

    }

    private void saveTeamIfNotExists(TeamName teamName){
        if(!teamRepository.existsByName(teamName)){
            teamRepository.save(new Team(teamName));
        } else {
            System.out.println("중복 저장입니다 : " + teamName);
        }
    }
}
