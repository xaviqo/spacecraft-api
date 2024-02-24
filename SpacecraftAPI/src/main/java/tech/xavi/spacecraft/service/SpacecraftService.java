package tech.xavi.spacecraft.service;

import tech.xavi.spacecraft.dto.SpacecraftDto;

import java.util.List;

public interface SpacecraftService {
    List<SpacecraftDto> getAllSpacecrafts();
    List<SpacecraftDto> getAllSpacecrafts(int page, int size);
    SpacecraftDto getSpacecraftById(long id);
    List<SpacecraftDto> getSpacecraftsByNameContains(String name);
    SpacecraftDto createSpacecraft(SpacecraftDto dto);
    SpacecraftDto updateSpacecraft(long id, SpacecraftDto dto);
    void deleteSpacecraft(long id);
}
