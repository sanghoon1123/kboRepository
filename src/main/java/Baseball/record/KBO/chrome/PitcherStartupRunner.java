package Baseball.record.KBO.chrome;

import Baseball.record.KBO.chrome.crawlerService.PitcherCrawlerService;
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
import org.springframework.context.annotation.Profile;
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
@Profile("!test")
public class PitcherStartupRunner implements CommandLineRunner {

    private final PitcherCrawlerService pitcherCrawlerService;

    @Override
    public void run(String... args) {
        pitcherCrawlerService.crawl();
    }
}
