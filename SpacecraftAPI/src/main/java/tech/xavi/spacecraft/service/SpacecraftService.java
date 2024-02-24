package tech.xavi.spacecraft.service;

import org.springframework.data.domain.Pageable;
import tech.xavi.spacecraft.dto.SpacecraftDto;

import java.util.List;

public interface SpacecraftService {
    List<SpacecraftDto> getAllSpacecrafts();
    List<SpacecraftDto> getAllSpacecrafts(Pageable pageable);
    SpacecraftDto getSpacecraftById(long id);
    List<SpacecraftDto> getSpacecraftsByNameContains(String name);
    SpacecraftDto createSpacecraft(SpacecraftDto dto);
    SpacecraftDto updateSpacecraft(long id, SpacecraftDto dto);
    void deleteSpacecraft(long id);
}
