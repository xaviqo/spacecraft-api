package tech.xavi.spacecraft.mapper;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import tech.xavi.spacecraft.dto.SpacecraftDto;
import tech.xavi.spacecraft.entity.spacecraft.Spacecraft;
import tech.xavi.spacecraft.entity.spacecraft.Status;

@Log4j2
class SpacecraftMapperTest {

    private SpacecraftMapper underTest;
    private SpacecraftDto dto;
    private Spacecraft entity;

    @BeforeEach
    void setUp() {
        log.info("Setting up test environment for {}",this.getClass());
        underTest = Mappers.getMapper(SpacecraftMapper.class);
        dto = SpacecraftDto.builder()
                .id(1337)
                .name("LEETCR3W")
                .maxSpeed(1337)
                .width(1337)
                .height(1337)
                .crewSize(1337)
                .status(Status.ACTIVE)
                .build();
        entity = Spacecraft.builder()
                .id(7331)
                .name("Nebuchadnezzar")
                .maxSpeed(7331)
                .width(7331)
                .height(7331)
                .crewSize(7331)
                .status(Status.UNDER_REPAIR)
                .build();
    }

    @Test
    void shouldConvertToDto() {
        // When
        SpacecraftDto convertedDto = underTest.toDto(entity);

        // Then
        Assertions.assertNotNull(convertedDto);
        Assertions.assertEquals(convertedDto.id(),entity.getId());
        Assertions.assertEquals(convertedDto.name(), entity.getName());
        Assertions.assertEquals(convertedDto.maxSpeed(), entity.getMaxSpeed());
        Assertions.assertEquals(convertedDto.width(), entity.getWidth());
        Assertions.assertEquals(convertedDto.height(), entity.getHeight());
        Assertions.assertEquals(convertedDto.crewSize(), entity.getCrewSize());
        Assertions.assertEquals(convertedDto.status(), entity.getStatus());
    }

    @Test
    void shouldConvertToEntity() {
        // When
        Spacecraft convertedEntity = underTest.toEntity(dto);

        // Then
        Assertions.assertNotNull(convertedEntity);
        Assertions.assertEquals(dto.id(), convertedEntity.getId());
        Assertions.assertEquals(dto.name(), convertedEntity.getName());
        Assertions.assertEquals(dto.maxSpeed(), convertedEntity.getMaxSpeed());
        Assertions.assertEquals(dto.width(), convertedEntity.getWidth());
        Assertions.assertEquals(dto.height(), convertedEntity.getHeight());
        Assertions.assertEquals(dto.crewSize(), convertedEntity.getCrewSize());
        Assertions.assertEquals(dto.status(), convertedEntity.getStatus());
    }
}