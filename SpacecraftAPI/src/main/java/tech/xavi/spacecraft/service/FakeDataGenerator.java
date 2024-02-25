package tech.xavi.spacecraft.service;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${xavi.tech.spacecraft.cfg.fake-entries}")
    private int totalFakeEntries;
    @Override
    public void run(String... args) {
        generateTestData(totalFakeEntries);
    }

    public List<Spacecraft> generateTestData(int totalEntries){
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
            log.debug("Created fake spacecraft: {}",fakeSpacecraft.toString());
        }

        return dataSet;
    }
}
