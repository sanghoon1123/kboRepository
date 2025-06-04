package Baseball.record.KBO.service;

import Baseball.record.KBO.domain.team.Team;
import Baseball.record.KBO.domain.team.TeamName;
import Baseball.record.KBO.domain.team.TeamRecord;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TeamCrawlerServiceTest {

    @Autowired TeamService teamService;

    @Value("${chrome.driver.path}")
    private String chromeDriverPath;

    public void crawlAndSaveTeamData() throws Exception {
        System.out.println("=== 크롤링 테스트 시작 ===");
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\eun04\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");

        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get("https://www.koreabaseball.com/Record/TeamRank/TeamRankDaily.aspx");
            Thread.sleep(2000);

            WebElement tbody = driver.findElement(By.cssSelector(".tData tbody"));
            List<WebElement> rows = tbody.findElements(By.tagName("tr"));

            LocalDate today = LocalDate.now();
            List<TeamRecord> teamRecords = new ArrayList<>();

            for (WebElement row : rows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));
                if (cells.size() < 8) continue;

                String teamNameKor = cells.get(1).getText();
                int rank = Integer.parseInt(cells.get(0).getText());
                int game = Integer.parseInt(cells.get(2).getText());
                int win = Integer.parseInt(cells.get(3).getText());
                int lose = Integer.parseInt(cells.get(4).getText());
                int draw = Integer.parseInt(cells.get(5).getText());
                double winRate = Double.parseDouble(cells.get(6).getText());
                double gamesBehind = Double.parseDouble(cells.get(7).getText());

                Team team = teamService.findOrCreateByKoreanName(teamNameKor);
                TeamName teamNameEnum = TeamName.fromKoreanName(teamNameKor)
                        .orElseThrow(() -> new IllegalArgumentException("알 수 없는 팀: " + teamNameKor));

                TeamRecord record = new TeamRecord();
                record.setTeam(team);
                record.setTeamName(teamNameEnum);
                record.setDate(today);
                record.setTeamRank(rank);
                record.setGame(game);
                record.setWin(win);
                record.setLose(lose);
                record.setDraw(draw);
                record.setWinningRate(winRate);
                record.setGamesBehind(gamesBehind);

                teamRecords.add(record);
            }

            teamService.saveTeamRecords(teamRecords);
            System.out.println("✅ 팀 기록 저장 완료");

        } finally {
            driver.quit();
        }
    }
}
