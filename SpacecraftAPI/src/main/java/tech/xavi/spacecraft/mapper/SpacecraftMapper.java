package tech.xavi.spacecraft.mapper;

import org.mapstruct.Mapper;
import tech.xavi.spacecraft.dto.SpacecraftDto;
import tech.xavi.spacecraft.entity.Spacecraft;

@Mapper()
public interface SpacecraftMapper {

    SpacecraftDto toDto(Spacecraft sc);
    Spacecraft toEntity(SpacecraftDto dto);

}
