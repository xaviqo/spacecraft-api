package tech.xavi.spacecraft.service.spacecraft;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tech.xavi.spacecraft.dto.SpacecraftDto;
import tech.xavi.spacecraft.entity.spacecraft.Spacecraft;
import tech.xavi.spacecraft.exception.ApiError;
import tech.xavi.spacecraft.exception.ApiException;
import tech.xavi.spacecraft.mapper.SpacecraftMapper;
import tech.xavi.spacecraft.repository.SpacecraftRepository;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class SpacecraftService {

    private final SpacecraftRepository spacecraftRepository;
    private final SpacecraftMapper mapper;
    private final Cache<Long,SpacecraftDto> spacecraftCache;


    public SpacecraftService(
            SpacecraftRepository spacecraftRepository,
            SpacecraftMapper mapper
    ) {
        this.spacecraftRepository = spacecraftRepository;
        this.mapper = mapper;
        this.spacecraftCache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .build();
    }

    public List<SpacecraftDto> getAllSpacecrafts() {
        return spacecraftRepository
                .findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public List<SpacecraftDto> getAllSpacecrafts(Pageable pageable) {
        return spacecraftRepository
                .findAll(pageable)
                .getContent()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public SpacecraftDto getSpacecraftById(long id) {
        return spacecraftCache.get(id, key ->
                spacecraftRepository
                        .findById(id)
                        .map(mapper::toDto)
                        .orElseThrow(() -> new ApiException(ApiError.SC_ID_NOT_FOUND, HttpStatus.BAD_REQUEST))
        );
    }

    public List<SpacecraftDto> getSpacecraftsByNameContains(String name) {
        return spacecraftRepository
                .findByNameContaining(name)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public SpacecraftDto createSpacecraft(SpacecraftDto dto) {
        return mapper.toDto(spacecraftRepository.save(mapper.toEntity(dto)));
    }

    public SpacecraftDto updateSpacecraft(long id, SpacecraftDto dto) {
        return spacecraftRepository
                .findById(id)
                .map( originalSc -> {
                    updateSpacecraftFields(originalSc,dto);
                    spacecraftRepository.save(originalSc);
                    return mapper.toDto(originalSc);
                })
                .orElseThrow( () -> new ApiException(ApiError.SC_ID_NOT_FOUND,HttpStatus.BAD_REQUEST));
    }

    public void deleteSpacecraft(long id) {
        if (spacecraftRepository.existsById(id))
            spacecraftRepository.deleteById(id);
        else
            throw new ApiException(ApiError.SC_ID_NOT_FOUND,HttpStatus.BAD_REQUEST);
    }

    private void updateSpacecraftFields(Spacecraft existingSc, SpacecraftDto dto) {
        if (dto.name() != null)
            existingSc.setName(dto.name());
        if (dto.maxSpeed() != 0)
            existingSc.setMaxSpeed(dto.maxSpeed());
        if (dto.width() != 0)
            existingSc.setWidth(dto.width());
        if (dto.height() != 0)
            existingSc.setHeight(dto.height());
        if (dto.crewSize() != 0)
            existingSc.setCrewSize(dto.crewSize());
        if (dto.status() != null)
            existingSc.setStatus(dto.status());
    }
}
