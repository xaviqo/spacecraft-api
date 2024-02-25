package tech.xavi.spacecraft.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tech.xavi.spacecraft.dto.SpacecraftDto;
import tech.xavi.spacecraft.exception.ApiError;
import tech.xavi.spacecraft.exception.ApiException;
import tech.xavi.spacecraft.mapper.SpacecraftMapper;
import tech.xavi.spacecraft.repository.SpacecraftRepository;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class SpacecraftServiceImpl implements SpacecraftService {

    private final SpacecraftRepository spacecraftRepository;
    private final SpacecraftMapper mapper;
    private final Cache<Long,SpacecraftDto> spacecraftCache;


    public SpacecraftServiceImpl(
            SpacecraftRepository spacecraftRepository,
            SpacecraftMapper mapper
    ) {
        this.spacecraftRepository = spacecraftRepository;
        this.mapper = mapper;
        this.spacecraftCache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .build();
    }

    @Override
    public List<SpacecraftDto> getAllSpacecrafts() {
        return spacecraftRepository
                .findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public List<SpacecraftDto> getAllSpacecrafts(Pageable pageable) {
        return spacecraftRepository
                .findAll(pageable)
                .getContent()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public SpacecraftDto getSpacecraftById(long id) {
        Optional<SpacecraftDto> dto =spacecraftRepository
                .findById(id)
                .map(mapper::toDto);
        return dto.get();
/*        return spacecraftCache.get(id, key ->
                spacecraftRepository
                        .findById(id)
                        .map(mapper::toDto)
                        .orElseThrow(() -> new ApiException(ApiError.SC_ID_NOT_FOUND, HttpStatus.BAD_REQUEST))
        );*/
    }

    @Override
    public List<SpacecraftDto> getSpacecraftsByNameContains(String name) {
        return spacecraftRepository
                .findByNameContaining(name)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public SpacecraftDto createSpacecraft(SpacecraftDto dto) {
        spacecraftRepository.save(mapper.toEntity(dto));
        return dto;
    }

    @Override
    public SpacecraftDto updateSpacecraft(long id, SpacecraftDto dto) {
        if (dto.id() == id && spacecraftRepository.existsById(id)) {
            spacecraftRepository.save(mapper.toEntity(dto));
            return dto;
        }
        throw new ApiException(ApiError.SC_ID_NOT_FOUND,HttpStatus.BAD_REQUEST);
    }

    @Override
    public void deleteSpacecraft(long id) {
        if (spacecraftRepository.existsById(id))
            spacecraftRepository.deleteById(id);
        throw new ApiException(ApiError.SC_ID_NOT_FOUND,HttpStatus.BAD_REQUEST);
    }
}
