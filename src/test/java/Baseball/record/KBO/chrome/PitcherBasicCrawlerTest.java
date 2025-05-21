package Baseball.record.KBO.chrome;

import Baseball.record.KBO.domain.team.Team;
import Baseball.record.KBO.domain.team.TeamName;
import Baseball.record.KBO.dto.PitcherDto2;
import Baseball.record.KBO.repository.TeamRepository;
import Baseball.record.KBO.service.PitcherCrawlerService;
import Baseball.record.KBO.service.PlayerService;
import Baseball.record.KBO.service.TeamCrawlerService;
import Baseball.record.KBO.service.TeamService;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
@Order(2)
@ActiveProfiles("test")
public class PitcherBasicCrawlerTest {

    @Autowired
    private PitcherCrawlerService pitcherCrawlerService;

    @Test
    public void crawlAndSavePitcher() throws Exception {
        pitcherCrawlerService.crawlAndSavePitcher();
    }

}
