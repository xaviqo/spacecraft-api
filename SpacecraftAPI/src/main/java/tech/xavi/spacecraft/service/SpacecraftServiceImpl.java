package tech.xavi.spacecraft.service;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tech.xavi.spacecraft.entity.Spacecraft;
import tech.xavi.spacecraft.exception.ApiError;
import tech.xavi.spacecraft.exception.ApiException;
import tech.xavi.spacecraft.repository.SpacecraftRepository;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class SpacecraftServiceImpl implements SpacecraftService {

    private final SpacecraftRepository spacecraftRepository;
    private final Cache<Long,Spacecraft> spacecraftCache;

    public SpacecraftServiceImpl(SpacecraftRepository spacecraftRepository) {
        this.spacecraftRepository = spacecraftRepository;
        this.spacecraftCache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .build();
    }

    @Override
    public List<Spacecraft> getAllSpacecrafts() {
        return spacecraftRepository.findAll();
    }

    @Override
    public List<Spacecraft> getAllSpacecrafts(int page, int size) {
        return spacecraftRepository
                .findAll(PageRequest.of(page, size))
                .getContent();
    }

    @Override
    public Spacecraft getSpacecraftById(long id) {
        if (id < 0)
            throw new ApiException(ApiError.NEGATIVE_ID,HttpStatus.BAD_REQUEST);
        return spacecraftCache.get(id, key ->
                spacecraftRepository
                        .findById(id)
                        .orElseThrow( () -> new ApiException(ApiError.INVALID_ID, HttpStatus.BAD_REQUEST) )
        );
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
        if (id < 0)
            throw new ApiException(ApiError.NEGATIVE_ID,HttpStatus.BAD_REQUEST);
        if (spacecraftRepository.existsById(id)) {
            spacecraft.setId(id);
            return spacecraftRepository.save(spacecraft);
        }
        throw new ApiException(ApiError.INVALID_ID,HttpStatus.BAD_REQUEST);
    }

    @Override
    public void deleteSpacecraft(long id) {
        spacecraftRepository.deleteById(id);
    }
}
