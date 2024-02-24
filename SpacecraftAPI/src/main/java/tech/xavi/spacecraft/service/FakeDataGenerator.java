package tech.xavi.spacecraft.service;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import tech.xavi.spacecraft.entity.Spacecraft;
import tech.xavi.spacecraft.entity.Status;
import tech.xavi.spacecraft.repository.SpacecraftRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component @RequiredArgsConstructor @Log4j2
public class FakeDataGenerator implements CommandLineRunner {

    private final SpacecraftRepository repository;

    @Override
    public void run(String... args) {
        generateTestData();
    }

    private void generateTestData(){
        long totalEntries = 100;

        StopWatch stopwatch = new StopWatch();
        stopwatch.start();

        log.info("Inserting test dataset, total {} records...", totalEntries);
        repository.saveAll(getFakeSpacecraftList(totalEntries));
        stopwatch.stop();
        log.info("Test dataset successfully inserted! Time elapsed {}sec",stopwatch.getTotalTimeSeconds());
    }

    public static List<Spacecraft> getFakeSpacecraftList(long total){
        List<Spacecraft> dataSet = new ArrayList<>();
        Faker faker = new Faker();
        Random random = new Random();

        for (int i = 0; i < total; i++)
            dataSet.add(
                    Spacecraft.builder()
                            .name(faker.space().nasaSpaceCraft())
                            .maxSpeed(faker.number().numberBetween(1000, 100000))
                            .width(faker.number().numberBetween(50, 500))
                            .height(faker.number().numberBetween(30, 300))
                            .crewSize(faker.number().numberBetween(1, 1000))
                            .status(Status.values()[random.nextInt(Status.values().length)])
                            .build()
            );

        return dataSet;
    }
}
