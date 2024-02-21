package tech.xavi.spacecraft.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.xavi.spacecraft.entity.Spacecraft;
import tech.xavi.spacecraft.repository.SpacecraftRepository;

import java.util.List;

@Service @RequiredArgsConstructor
public class SpacecraftServiceImpl implements SpacecraftService {

    private final SpacecraftRepository spacecraftRepository;

    @Override
    public List<Spacecraft> getAllSpacecrafts() {
        return spacecraftRepository.findAll();
    }

    @Override
    public List<Spacecraft> getAllSpacecrafts(int page, int size) {
        // Implementación de la paginación según tus necesidades
        // Podrías utilizar métodos de repositorio específicos de Spring Data JPA
        return null;
    }

    @Override
    public Spacecraft getSpacecraftById(long id) {
        return spacecraftRepository.findById(id).orElse(null);
    }

    @Override
    public List<Spacecraft> getSpacecraftsByNameContains(String name) {
        return spacecraftRepository.findByNameContaining(name);
    }

    @Override
    public Spacecraft createSpacecraft(Spacecraft spacecraft) {
        return spacecraftRepository.save(spacecraft);
    }

    @Override
    public Spacecraft updateSpacecraft(long id, Spacecraft spacecraft) {
        if (spacecraftRepository.existsById(id)) {
            spacecraft.setId(id);
            return spacecraftRepository.save(spacecraft);
        }
        return null;
    }

    @Override
    public void deleteSpacecraft(long id) {
        spacecraftRepository.deleteById(id);
    }
}
