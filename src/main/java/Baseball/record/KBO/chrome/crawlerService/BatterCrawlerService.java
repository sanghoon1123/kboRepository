package Baseball.record.KBO.chrome.crawlerService;

import Baseball.record.KBO.domain.player.BatterPosition;
import Baseball.record.KBO.domain.team.TeamName;
import Baseball.record.KBO.dto.BatterDto2;
import Baseball.record.KBO.dto.PlayerDto2;
import Baseball.record.KBO.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BatterCrawlerService {

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
        Map<String, PlayerDto2> playerMap = new HashMap<>();

        try {
            crawlBasicStats(driver, playerMap);
            crawlOpsStats(driver, playerMap);
            crawlPos(driver, playerMap);
            playerService.saveAllBatters(playerMap);
            System.out.println("✅ 타자 저장 완료");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    private void crawlBasicStats(WebDriver driver, Map<String, PlayerDto2> playerMap) throws InterruptedException {
        driver.get("https://www.koreabaseball.com/Record/Player/HitterBasic/Basic1.aspx");
        Thread.sleep(2000);
        int currentPage = 1;

        while (true) {
            List<WebElement> rows = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));

            for (WebElement row : rows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));
                if (cells.size() < 13) continue;

                try {
                    WebElement link = cells.get(1).findElement(By.tagName("a"));
                    String name = link.getText();
                    String playerUrl = link.getAttribute("href");
                    String teamKorName = cells.get(2).getText().trim();

                    int game = Integer.parseInt(cells.get(4).getText().replaceAll(",", ""));
                    double average = Double.parseDouble(cells.get(3).getText());
                    int hit = Integer.parseInt(cells.get(8).getText().replaceAll(",", ""));
                    int homeRun = Integer.parseInt(cells.get(11).getText().replaceAll(",", ""));
                    int rbi = Integer.parseInt(cells.get(13).getText().replaceAll(",", ""));

                    LocalDate birthDate = fetchBirthDate(playerUrl);
                    String key = makeKey(name, birthDate);

                    BatterDto2 batterDto = new BatterDto2();
                    batterDto.setName(name);
                    batterDto.setBirthDate(birthDate);
                    batterDto.setGame(game);
                    batterDto.setAverage(average);
                    batterDto.setHit(hit);
                    batterDto.setHomeRun(homeRun);
                    batterDto.setRbi(rbi);

                    TeamName teamName = TeamName.fromKoreanName(teamKorName)
                            .orElseThrow(() -> new IllegalArgumentException("알 수 없는 팀명: " + teamKorName));
                    batterDto.setTeamName(teamName);

                    playerMap.put(key, batterDto);

                } catch (Exception e) {
                    System.out.println("⚠️ 기본 기록 크롤링 오류: " + e.getMessage());
                }
            }

            // 다음 숫자 페이지로 이동
            List<WebElement> pageLinks = driver.findElements(By.cssSelector("div.paging a"));
            boolean foundNext = false;

            for (WebElement link : pageLinks) {
                String text = link.getText().trim();
                if (text.matches("\\d+")) {
                    int pageNum = Integer.parseInt(text);
                    if (pageNum > currentPage) {
                        currentPage = pageNum;
                        link.click();
                        Thread.sleep(1500);
                        foundNext = true;
                        break;
                    }
                }
            }

            if (!foundNext) {
                System.out.println("📄 타자 기본 기록 마지막 페이지 도달");
                break;
            }
        }
    }



    private LocalDate fetchBirthDate(String playerUrl) {
        WebDriver subDriver = new ChromeDriver();
        try {
            subDriver.get(playerUrl);
            WebDriverWait wait = new WebDriverWait(subDriver, Duration.ofSeconds(5));
            WebElement birthDateElement = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.id("cphContents_cphContents_cphContents_playerProfile_lblBirthday")));
            String birthDateText = birthDateElement.getText();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
            return LocalDate.parse(birthDateText, formatter);
        } catch (Exception e) {
            System.out.println("⚠️ 생년월일 크롤링 실패: " + e.getMessage());
            return null;
        } finally {
            subDriver.quit();
        }
    }

    private void crawlOpsStats(WebDriver driver, Map<String, PlayerDto2> playerMap) throws InterruptedException {
        driver.get("https://www.koreabaseball.com/Record/Player/HitterBasic/Basic2.aspx");
        Thread.sleep(2000);
        int currentPage = 1;

        while (true) {
            List<WebElement> rows = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));

            for (WebElement row : rows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));
                if (cells.size() < 13) continue;

                try {
                    String name = cells.get(1).getText();
                    double ops = Double.parseDouble(cells.get(11).getText());

                    for (PlayerDto2 dto : playerMap.values()) {
                        if (dto instanceof BatterDto2 batterDto && batterDto.getName().equals(name)) {
                            batterDto.setOps(ops);
                            break;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("⚠️ OPS 파싱 오류: " + e.getMessage());
                }
            }

            // 다음 숫자 페이지 찾기
            List<WebElement> pageLinks = driver.findElements(By.cssSelector("div.paging a"));
            boolean foundNext = false;

            for (WebElement link : pageLinks) {
                String text = link.getText().trim();
                if (text.matches("\\d+")) {
                    int pageNum = Integer.parseInt(text);
                    if (pageNum > currentPage) {
                        currentPage = pageNum;
                        link.click();
                        Thread.sleep(1500);
                        foundNext = true;
                        break;
                    }
                }
            }

            if (!foundNext) {
                System.out.println("📄 OPS 기록 마지막 페이지 도달");
                break;
            }
        }
    }



    private void crawlPos(WebDriver driver, Map<String, PlayerDto2> playerMap) throws InterruptedException {
        driver.get("https://www.koreabaseball.com/Record/Player/Defense/Basic.aspx");
        Thread.sleep(2000);

        while (true) {
            List<String> pageNumbers = driver.findElements(By.cssSelector("div.paging a")).stream()
                    .map(WebElement::getText)
                    .filter(text -> text.trim().matches("\\d+"))
                    .toList();

            for (String pageNum : pageNumbers) {
                try {
                    clickPage(driver, pageNum);
                    Thread.sleep(1500);
                    parseDefensePage(driver, playerMap);
                } catch (Exception e) {
                    System.out.println("📛 페이지 클릭 실패: " + pageNum);
                }
            }

            if (!clickNextPage(driver)) {
                System.out.println("📄 마지막 페이지 도달, 수비 포지션 크롤링 완료");
                break;
            }
        }
    }

    private void clickPage(WebDriver driver, String pageNum) {
        List<WebElement> refreshedLinks = driver.findElements(By.cssSelector("div.paging a"));
        for (WebElement link : refreshedLinks) {
            if (link.getText().trim().equals(pageNum)) {
                link.click();
                return;
            }
        }
    }

    private boolean clickNextPage(WebDriver driver) throws InterruptedException {
        List<WebElement> nextButtons = driver.findElements(By.cssSelector("a > img[alt='다음']"));
        if (nextButtons.isEmpty()) return false;

        try {
            WebElement nextAnchor = nextButtons.get(0).findElement(By.xpath(".."));
            nextAnchor.click();
            Thread.sleep(1500);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void parseDefensePage(WebDriver driver, Map<String, PlayerDto2> playerMap) {
        List<WebElement> rows = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() < 5) continue;

            String name = cells.get(1).getText().trim();
            String teamKorName = cells.get(2).getText().trim();
            String posKor = cells.get(3).getText().trim();
            String posGameCountText = cells.get(4).getText().trim();

            TeamName teamName = TeamName.fromKoreanName(teamKorName).orElse(null);
            if (teamName == null) {
                System.out.println("❓ 알 수 없는 팀명: " + teamKorName);
                continue;
            }

            BatterPosition position = batterPositionMap.get(posKor);
            if (position == null) continue;

            int posGameCount;
            try {
                posGameCount = Integer.parseInt(posGameCountText.replaceAll(",", ""));
            } catch (NumberFormatException e) {
                System.out.println("⚠️ 게임 수 파싱 실패: " + posGameCountText);
                continue;
            }

            for (PlayerDto2 dto : playerMap.values()) {
                if (dto instanceof BatterDto2 batterDto && batterDto.getName().equals(name) && batterDto.getTeamName() == teamName) {
                    if (batterDto.getBatterPosition() == null) {
                        int totalGame = batterDto.getGame();
                        if (posGameCount < totalGame / 4) {
                            batterDto.setBatterPosition(BatterPosition.DH);
                            System.out.println("🔁 포지션 경기 수 부족 → DH 설정: " + name + " (" + posGameCount + "/" + totalGame + ")");
                        } else {
                            batterDto.setBatterPosition(position);
                            System.out.println("✅ 포지션 설정 완료: " + name + " → " + position);
                        }
                    } else {
                        System.out.println("⏩ 이미 포지션 설정됨: " + name + " → 유지: " + batterDto.getBatterPosition() + " (무시됨: " + position + ")");
                    }
                    break;
                }
            }
        }
    }


    private String makeKey(String name, LocalDate birthDate) {
        return name + "_" + birthDate;
    }

    private final Map<String, BatterPosition> batterPositionMap = Map.of(
            "우익수", BatterPosition.RIGHT,
            "중견수", BatterPosition.CENTER,
            "좌익수", BatterPosition.LEFT,
            "유격수", BatterPosition.SS,
            "3루수", BatterPosition.THIRD,
            "2루수", BatterPosition.SECOND,
            "1루수", BatterPosition.FIRST,
            "포수", BatterPosition.C
    );
}
