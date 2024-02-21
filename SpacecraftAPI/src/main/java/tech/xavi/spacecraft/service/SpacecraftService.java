package tech.xavi.spacecraft.service;

import tech.xavi.spacecraft.entity.Spacecraft;

import java.util.List;

public interface SpacecraftService {
    List<Spacecraft> getAllSpacecrafts();
    List<Spacecraft> getAllSpacecrafts(int page, int size);
    Spacecraft getSpacecraftById(long id);
    List<Spacecraft> getSpacecraftsByNameContains(String name);
    Spacecraft createSpacecraft(Spacecraft spacecraft);
    Spacecraft updateSpacecraft(long id, Spacecraft spacecraft);
    void deleteSpacecraft(long id);
}
