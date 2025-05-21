package Baseball.record.KBO.chrome;

import Baseball.record.KBO.domain.team.Team;
import Baseball.record.KBO.domain.team.TeamName;
import Baseball.record.KBO.domain.team.TeamRecord;
import Baseball.record.KBO.service.TeamCrawlerService;
import Baseball.record.KBO.service.TeamService;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@Order(1)
public class TeamCrawlerTest {

    @Autowired
    private TeamCrawlerService teamCrawlerService;

    @Test
    public void crawlAndSaveTeamData() throws Exception {
        System.out.println("=== 크롤링 테스트 시작 ===");
        teamCrawlerService.crawlAndSaveTeamData();
    }
}

