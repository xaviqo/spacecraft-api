package tech.xavi.spacecraft.configuration;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import tech.xavi.spacecraft.dto.AccountDto;
import tech.xavi.spacecraft.entity.account.Role;
import tech.xavi.spacecraft.entity.spacecraft.Spacecraft;
import tech.xavi.spacecraft.entity.spacecraft.Status;
import tech.xavi.spacecraft.repository.SpacecraftRepository;
import tech.xavi.spacecraft.service.account.AccountService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component @RequiredArgsConstructor @Log4j2
public class FakeDataGenerator implements CommandLineRunner {

    private final SpacecraftRepository repository;
    private final AccountService accountService;
    @Value("${xavi.tech.spacecraft.cfg.test.fake-spacecraft-entries}")
    private int totalFakeEntries;
    @Value("${xavi.tech.spacecraft.cfg.test.credentials.create-fake-accounts}")
    private boolean isCreateFakeAccounts;
    @Value("${xavi.tech.spacecraft.cfg.test.credentials.admin}")
    private String fakeAdminAccountCreds;
    @Value("${xavi.tech.spacecraft.cfg.test.credentials.user}")
    private String fakeUserAccountCreds;

    @Override
    public void run(String... args) {
        if (totalFakeEntries > 0)
            generateTestSpacecrafts(totalFakeEntries);
        if (isCreateFakeAccounts)
            generateTestAccounts();
    }

    private void generateTestAccounts() {
        log.info("Inserting test accounts...");
        log.info("Admin credentials: {}", fakeAdminAccountCreds);
        accountService.createAccount(
                AccountDto.builder()
                        .username(fakeAdminAccountCreds.split(",")[0])
                        .password(fakeAdminAccountCreds.split(",")[1])
                        .build(),
                Role.ADMIN
        );
        log.info("User credentials: {}", fakeUserAccountCreds);
        accountService.createAccount(
                AccountDto.builder()
                        .username(fakeUserAccountCreds.split(",")[0])
                        .password(fakeUserAccountCreds.split(",")[1])
                        .build(),
                Role.USER
        );
        log.info("Test accounts successfully inserted!");
    }

    public List<Spacecraft> generateTestSpacecrafts(int totalEntries){
        StopWatch stopwatch = new StopWatch();
        stopwatch.start();

        log.info("Inserting test dataset, total {} records...", totalEntries);
        List<Spacecraft> fakeEnties = getFakeSpacecraftList(totalEntries);
        repository.saveAll(fakeEnties);
        stopwatch.stop();
        log.info("Test dataset successfully inserted! Time elapsed {}sec",stopwatch.getTotalTimeSeconds());
        return fakeEnties;
    }

    public static List<Spacecraft> getFakeSpacecraftList(int total){
        List<Spacecraft> dataSet = new ArrayList<>();
        Faker faker = new Faker();
        Random random = new Random();

        for (int i = 0; i < total; i++) {
            Spacecraft fakeSpacecraft = Spacecraft.builder()
                    .name(faker.space().nasaSpaceCraft())
                    .maxSpeed(faker.number().numberBetween(1000, 100000))
                    .width(faker.number().numberBetween(50, 500))
                    .height(faker.number().numberBetween(30, 300))
                    .crewSize(faker.number().numberBetween(1, 1000))
                    .status(Status.values()[random.nextInt(Status.values().length)])
                    .build();
            dataSet.add(fakeSpacecraft);
            log.debug("Created fake spacecraft ({} of {}): {}",
                    (i+1),
                    total,
                    fakeSpacecraft.toString()
            );
        }

        return dataSet;
    }
}
