package Baseball.record.KBO.chrome;

import Baseball.record.KBO.domain.team.Team;
import Baseball.record.KBO.domain.team.TeamName;
import Baseball.record.KBO.domain.team.TeamRecord;
import Baseball.record.KBO.repository.TeamRecordRepository;
import Baseball.record.KBO.repository.TeamRepository;
import Baseball.record.KBO.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.swing.text.Document;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Order(1)
public class TeamCrawler implements CommandLineRunner {

    private final TeamService teamService;

    @Override
    public void run(String... args) {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\eun04\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new"); // 최신 버전 대응
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");

        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get("https://www.koreabaseball.com/Record/TeamRank/TeamRankDaily.aspx");
            Thread.sleep(2000); // 페이지 로딩 대기

            WebElement tbody = driver.findElement(By.cssSelector(".tData tbody"));
            List<WebElement> rows = tbody.findElements(By.tagName("tr"));

            LocalDate today = LocalDate.now();
            List<TeamRecord> teamRecords = new ArrayList<>();

            for (WebElement row : rows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));
                if (cells.size() < 8) continue;

                String teamNameKor = cells.get(1).getText();
                int rank = Integer.parseInt(cells.get(0).getText());
                int win = Integer.parseInt(cells.get(3).getText());
                int lose = Integer.parseInt(cells.get(4).getText());
                int draw = Integer.parseInt(cells.get(5).getText());
                double winRate = Double.parseDouble(cells.get(6).getText());

                Team team = teamService.findOrCreateByKoreanName(teamNameKor);
                TeamName teamNameEnum = TeamName.fromKoreanName(teamNameKor)
                        .orElseThrow(() -> new IllegalArgumentException("알 수 없는 팀: " + teamNameKor));

                TeamRecord record = new TeamRecord();
                record.setTeam(team);
                record.setTeamName(teamNameEnum);
                record.setDate(today);
                record.setTeamRank(rank);
                record.setWin(win);
                record.setLose(lose);
                record.setDraw(draw);
                record.setWinningRate(winRate);

                teamRecords.add(record);
            }

            teamService.saveTeamRecords(teamRecords);
            System.out.println("✅ 팀 기록 저장 완료");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
