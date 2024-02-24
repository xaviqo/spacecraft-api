package tech.xavi.spacecraft.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tech.xavi.spacecraft.mapper.SpacecraftMapper;
import tech.xavi.spacecraft.repository.SpacecraftRepository;

@Log4j2
class SpacecraftServiceImplTest {

    @Mock
    private SpacecraftRepository spacecraftRepository;
    private SpacecraftMapper spacecraftMapper;
    private AutoCloseable autoCloseable;
    private SpacecraftService underTest;

    @BeforeEach
    void setUp() {
        log.info("Setting up test environment for {}",this.getClass());
        spacecraftMapper = Mappers.getMapper(SpacecraftMapper.class);
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new SpacecraftServiceImpl(
                spacecraftRepository,
                spacecraftMapper
        );
    }

    @AfterEach
    void tearDown() throws Exception {
        log.info("Tearing down test environment for {}",this.getClass());
        autoCloseable.close();
    }

    @Test
    void canGetAllSpacecrafts(){
        FakeDataGenerator.getFakeSpacecraftList(1).forEach( sc -> {
            System.out.println(sc.toString());
            underTest.createSpacecraft(spacecraftMapper.toDto(sc));
        });

        // when
        System.out.println(underTest.getAllSpacecrafts());;
        // then

    }
}