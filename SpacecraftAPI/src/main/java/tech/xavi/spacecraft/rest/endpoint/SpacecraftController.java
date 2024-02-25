package tech.xavi.spacecraft.rest.endpoint;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.xavi.spacecraft.configuration.EndPoints;
import tech.xavi.spacecraft.dto.SpacecraftDto;
import tech.xavi.spacecraft.service.SpacecraftService;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping
public class SpacecraftController {

    private final SpacecraftService spacecraftService;

    @GetMapping(EndPoints.EP_SPACECRAFT)
    public ResponseEntity<List<SpacecraftDto>> getAllSpacecrafts(@RequestParam(required = false) Pageable pageable) {
        return ResponseEntity.ok(
                (pageable != null)
                        ? spacecraftService.getAllSpacecrafts(pageable)
                        : spacecraftService.getAllSpacecrafts()
        );
    }

    @GetMapping(EndPoints.EP_SC_BY_ID)
    public ResponseEntity<SpacecraftDto> getSpacecraftById(@PathVariable long id) {
        return ResponseEntity.ok(spacecraftService.getSpacecraftById(id));
    }

    @GetMapping(EndPoints.EP_SC_NAME_CONTAINS)
    public ResponseEntity<List<SpacecraftDto>> getSpacecraftsByNameContains(@RequestParam String name) {
        return ResponseEntity.ok(spacecraftService.getSpacecraftsByNameContains(name));
    }

    @PostMapping(EndPoints.EP_SPACECRAFT)
    public ResponseEntity<SpacecraftDto> createSpacecraft(@RequestBody SpacecraftDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(spacecraftService.createSpacecraft(dto));
    }

    @PutMapping(EndPoints.EP_SC_BY_ID)
    public ResponseEntity<SpacecraftDto> updateSpacecraft(@PathVariable long id, @RequestBody SpacecraftDto dto) {
        return ResponseEntity.ok(spacecraftService.updateSpacecraft(id, dto));
    }

    @DeleteMapping(EndPoints.EP_SC_BY_ID)
    public ResponseEntity<Void> deleteSpacecraft(@PathVariable long id) {
        spacecraftService.deleteSpacecraft(id);
        return ResponseEntity.noContent().build();
    }

}
