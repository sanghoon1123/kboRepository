package Baseball.record.KBO.chrome.crawlerService;

import Baseball.record.KBO.domain.team.Team;
import Baseball.record.KBO.domain.team.TeamName;
import Baseball.record.KBO.dto.PitcherDto2;
import Baseball.record.KBO.repository.TeamRepository;
import Baseball.record.KBO.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PitcherCrawlerService {
    private final TeamRepository teamRepository;
    private final PlayerService playerService;

    @Value("${chrome.driver.path}")
    private String chromeDriverPath;


    public void crawl() {
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://www.koreabaseball.com/Record/Player/PitcherBasic/Basic1.aspx");

        Map<TeamName, Team> teamMap = teamRepository.findAll().stream()
                .collect(Collectors.toMap(Team::getName, team -> team));

        String[] teamValues = { "WO", "OB", "NC", "LT", "SS", "SK", "HT", "LG", "KT", "HH" };
        Map<String, PitcherDto2> playerMap = new HashMap<>();

        try {
            for (String teamValue : teamValues) {
                // ✅ teamSelect는 한 번만 선언
                Select teamSelect = new Select(driver.findElement(
                        By.id("cphContents_cphContents_cphContents_ddlTeam_ddlTeam")));

                WebElement oldTable = driver.findElement(By.className("tData01"));
                teamSelect.selectByValue(teamValue);

                // ✅ DOM 변경 대기
                wait.until(ExpectedConditions.stalenessOf(oldTable));
                wait.until(ExpectedConditions.presenceOfElementLocated(By.className("tData01")));

                WebElement table = driver.findElement(By.className("tData01"));
                List<WebElement> rows = table.findElements(By.tagName("tr"));

                // ✅ 컬럼 인덱스 매핑
                Map<String, Integer> colIndex = new HashMap<>();
                List<WebElement> headers = driver.findElements(By.cssSelector(".tData01 thead tr th"));
                for (int i = 0; i < headers.size(); i++) {
                    colIndex.put(headers.get(i).getText().trim(), i);
                }

                // ✅ teamKor만 새롭게 Select 객체로 가져오기
                String teamKor = new Select(driver.findElement(
                        By.id("cphContents_cphContents_cphContents_ddlTeam_ddlTeam")
                )).getFirstSelectedOption().getText();

                TeamName teamNameEnum = TeamName.fromKoreanName(teamKor)
                        .orElseThrow(() -> new IllegalArgumentException("알 수 없는 팀: " + teamKor));
                Team team = teamMap.get(teamNameEnum);

                if (team == null) throw new IllegalArgumentException("DB에 없는 팀: " + teamKor);

                for (int i = 1; i < rows.size(); i++) {
                    try {
                        List<WebElement> cols = rows.get(i).findElements(By.tagName("td"));
                        WebElement nameCell = cols.get(colIndex.get("선수명"));
                        String name = nameCell.getText();
                        String playerUrl = nameCell.findElement(By.tagName("a")).getAttribute("href");

                        LocalDate birthDate = fetchBirthDate(playerUrl);
                        if (birthDate == null) continue;

                        PitcherDto2 dto = new PitcherDto2(
                                name, birthDate,
                                parseIntSafe(cols.get(colIndex.get("G")).getText()),
                                team.getName().getKoreanName(),
                                parseIntSafe(cols.get(colIndex.get("W")).getText()),
                                parseIntSafe(cols.get(colIndex.get("L")).getText()),
                                parseInningsBaseballStyle(cols.get(colIndex.get("IP")).getText()),
                                parseDoubleSafe(cols.get(colIndex.get("ERA")).getText()),
                                parseIntSafe(cols.get(colIndex.get("SO")).getText()),
                                parseIntSafe(cols.get(colIndex.get("HLD")).getText()),
                                parseIntSafe(cols.get(colIndex.get("SV")).getText()),
                                null,
                                false
                        );

                        String key = name + "_" + team.getName() + "_" + birthDate;
                        playerMap.put(key, dto);
                        System.out.println("✅ 크롤링 완료: " + name);
                    } catch (Exception e) {
                        System.out.println("❗ 크롤링 실패: " + e.getMessage());
                    }
                }
            }
        } finally {
            driver.quit();
        }

// 후처리 서비스 호출
        playerService.pitcherPositionFilter(playerMap);
        playerService.markQualifiedInnings(playerMap);
        playerService.saveAllPitchers(playerMap);
        System.out.println("✅ 모든 투수 저장 완료!");
    }

    private LocalDate fetchBirthDate(String playerUrl) {
        WebDriver subDriver = new ChromeDriver();
        try {
            subDriver.get(playerUrl);
            WebDriverWait wait = new WebDriverWait(subDriver, Duration.ofSeconds(5));
            WebElement birthDateElement = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.id("cphContents_cphContents_cphContents_playerProfile_lblBirthday")));

            String birthDateText = birthDateElement.getText(); // "1988년 03월 18일"
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
            return LocalDate.parse(birthDateText, formatter);
        } catch (Exception e) {
            System.out.println("❗ 생년월일 크롤링 실패: " + e.getMessage());
            return null;
        } finally {
            subDriver.quit();
        }
    }

    private int parseIntSafe(String text) {
        try {
            text = text.replaceAll("[^\\d-]", ""); // 숫자와 -만 남김
            return Integer.parseInt(text.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }


    private double parseDoubleSafe(String text) {
        try {
            text = text.replaceAll("[^\\d.]", ""); // 숫자와 .만 남김
            return Double.parseDouble(text.trim());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private double parseInningsBaseballStyle(String text) {
        try {
            text = text.trim();
            if (text.contains(" ")) {
                String[] parts = text.split(" ");
                int whole = Integer.parseInt(parts[0]);
                String fraction = parts[1];
                double decimal = 0.0;
                if (fraction.equals("1/3")) decimal = 0.1;
                else if (fraction.equals("2/3")) decimal = 0.2;
                return whole + decimal;
            } else {
                return Double.parseDouble(text);
            }
        } catch(Exception e) {
            return 0.0;
        }
    }
}
