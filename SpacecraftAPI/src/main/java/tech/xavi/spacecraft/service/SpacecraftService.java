package tech.xavi.spacecraft.service;

import tech.xavi.spacecraft.dto.SpacecraftDto;
import tech.xavi.spacecraft.entity.Spacecraft;

import java.util.List;

public interface SpacecraftService {
    List<Spacecraft> getAllSpacecrafts();
    List<Spacecraft> getAllSpacecrafts(int page, int size);
    Spacecraft getSpacecraftById(long id);
    List<Spacecraft> getSpacecraftsByNameContains(String name);
    Spacecraft createSpacecraft(SpacecraftDto dto);
    Spacecraft updateSpacecraft(long id, SpacecraftDto dto);
    void deleteSpacecraft(long id);
}
