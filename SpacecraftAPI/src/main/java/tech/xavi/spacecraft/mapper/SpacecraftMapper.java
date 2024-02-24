package tech.xavi.spacecraft.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import tech.xavi.spacecraft.dto.SpacecraftDto;
import tech.xavi.spacecraft.entity.Spacecraft;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SpacecraftMapper {

    //SpacecraftMapper mapper = Mappers.getMapper(SpacecraftMapper.class);

    SpacecraftDto toDto(Spacecraft sc);
    Spacecraft toEntity(SpacecraftDto dto);

}
