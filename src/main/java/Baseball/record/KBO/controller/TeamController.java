package Baseball.record.KBO.controller;

import Baseball.record.KBO.domain.team.Team;
import Baseball.record.KBO.domain.team.TeamName;
import Baseball.record.KBO.domain.team.TeamRecord;
import Baseball.record.KBO.dto.TeamDto;
import Baseball.record.KBO.dto.TeamRecordDto;
import Baseball.record.KBO.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;

    @GetMapping("/name")
    public List<TeamDto> searchTeam(@ModelAttribute TeamName teamName){
        return teamService.findByTeamName(teamName);
    }

    @GetMapping("/record")
    public List<TeamRecordDto> allTeamRecord(){
        return teamService.getAllTeamRecords();
    }
}
