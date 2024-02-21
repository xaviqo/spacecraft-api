package tech.xavi.spacecraft.configuration;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import tech.xavi.spacecraft.entity.Spacecraft;
import tech.xavi.spacecraft.entity.Status;
import tech.xavi.spacecraft.service.SpacecraftService;

import java.util.Random;

@Component @RequiredArgsConstructor @Slf4j
public class FakeDataCfg implements CommandLineRunner {

    private final SpacecraftService spacecraftService;
    private final long TOTAL_ENTRIES = 100;

    @Override
    public void run(String... args) throws Exception {
        generateTestData();
    }

    private void generateTestData(){
        StopWatch stopwatch = new StopWatch();
        stopwatch.start();
        log.info("Inserting test dataset, total {} records...",TOTAL_ENTRIES);

        Faker faker = new Faker();
        Random random = new Random();
        for (int i = 0; i < TOTAL_ENTRIES; i++)
            spacecraftService.createSpacecraft(
                    Spacecraft.builder()
                            .name(faker.space().nasaSpaceCraft())
                            .maxSpeed(faker.number().numberBetween(1000, 100000))
                            .width(faker.number().numberBetween(50, 500))
                            .height(faker.number().numberBetween(30, 300))
                            .crewSize(faker.number().numberBetween(1, 1000))
                            .status(Status.values()[random.nextInt(Status.values().length)])
                            .build()
            );

        stopwatch.stop();
        log.info("Test dataset successfully inserted! Time elapsed {}sec",stopwatch.getTotalTimeSeconds());
    }
}
