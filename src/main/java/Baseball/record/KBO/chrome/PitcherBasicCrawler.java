package Baseball.record.KBO.chrome;

import Baseball.record.KBO.domain.team.Team;
import Baseball.record.KBO.domain.team.TeamName;
import Baseball.record.KBO.dto.PitcherDto2;
import Baseball.record.KBO.repository.TeamRepository;
import Baseball.record.KBO.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Order(2)
public class PitcherBasicCrawler implements CommandLineRunner {

    private final TeamRepository teamRepository;
    private final PlayerService playerService;

    @Override
    public void run(String... args) throws Exception {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\eun04\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new"); // 최신 버전 대응
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://www.koreabaseball.com/Record/Player/PitcherBasic/Basic1.aspx");

        Map<TeamName, Team> teamMap = teamRepository.findAll().stream()
                .collect(Collectors.toMap(Team::getName, team -> team));

        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.id("cphContents_cphContents_cphContents_ddlTeam_ddlTeam")));

        String[] teamValues = { "WO", "OB", "NC", "LT", "SS", "SK", "HT", "LG", "KT", "HH" };
        Map<String, PitcherDto2> playerMap = new HashMap<>();

        for (String teamValue : teamValues) {
            Select teamSelect = new Select(driver.findElement(
                    By.id("cphContents_cphContents_cphContents_ddlTeam_ddlTeam")));

            WebElement oldTable = driver.findElement(By.className("tData01"));
            teamSelect.selectByValue(teamValue);
            wait.until(ExpectedConditions.stalenessOf(oldTable));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("tData01")));

            teamSelect = new Select(driver.findElement(
                    By.id("cphContents_cphContents_cphContents_ddlTeam_ddlTeam")));

            WebElement table = driver.findElement(By.className("tData01"));
            List<WebElement> rows = table.findElements(By.tagName("tr"));

            // 헤더 추출 및 인덱스 매핑
            Map<String, Integer> colIndex = new HashMap<>();
            List<WebElement> headers = driver.findElements(By.cssSelector(".tData01 thead tr th"));

            for (int i = 0; i < headers.size(); i++) {
                String headerText = headers.get(i).getText().trim();
                colIndex.put(headerText, i);  // 예: "ERA" -> 3, "SV" -> 7
            }

            String teamKor = teamSelect.getFirstSelectedOption().getText();
            TeamName teamNameEnum = TeamName.fromKoreanName(teamKor)
                    .orElseThrow(() -> new IllegalArgumentException("알 수 없는 팀: " + teamKor));
            Team team = teamMap.get(teamNameEnum);

            if (team == null) throw new IllegalArgumentException("DB에 존재하지 않는 팀: " + teamKor);

            for (int i = 1; i < rows.size(); i++) {
                try {
                    List<WebElement> cols = rows.get(i).findElements(By.tagName("td"));
                    WebElement nameCell = cols.get(colIndex.get("선수명"));
                    String name = nameCell.getText();
                    String playerUrl = nameCell.findElement(By.tagName("a")).getAttribute("href");

                    LocalDate birthDate = fetchBirthDate(playerUrl);
                    if (birthDate == null) continue;

                    int game = parseIntSafe(cols.get(colIndex.get("G")).getText());
                    int win = parseIntSafe(cols.get(colIndex.get("W")).getText());
                    int lose = parseIntSafe(cols.get(colIndex.get("L")).getText());
                    int save = parseIntSafe(cols.get(colIndex.get("SV")).getText());
                    int hold = parseIntSafe(cols.get(colIndex.get("HLD")).getText());
                    double ip = parseInningsBaseballStyle(cols.get(colIndex.get("IP")).getText());
                    double era = parseDoubleSafe(cols.get(colIndex.get("ERA")).getText());
                    int strikeouts = parseIntSafe(cols.get(colIndex.get("SO")).getText());


                    PitcherDto2 pitcherDto = new PitcherDto2(
                            name, birthDate, game, team.getName().getKoreanName(),
                            win, lose, ip, era, strikeouts, hold, save, null
                    );

                    String key = name + "_" + team.getName() + "_" + birthDate;
                    playerMap.put(key, pitcherDto);
                    System.out.println("✅ 크롤링 완료: " + name);
                } catch (Exception e) {
                    System.out.println("❗ 크롤링 실패: " + e.getMessage());
                }
            }
        }

        driver.quit();
        playerService.pitcherPositionFilter(playerMap);
        playerService.saveAllPitchers(playerMap);
        System.out.println("✅ 모든 투수 저장 완료!");
    }

    private static LocalDate fetchBirthDate(String playerUrl) {
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
