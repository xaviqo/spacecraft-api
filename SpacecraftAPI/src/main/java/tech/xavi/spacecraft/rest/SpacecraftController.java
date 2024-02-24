package tech.xavi.spacecraft.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.xavi.spacecraft.configuration.EndPoints;
import tech.xavi.spacecraft.entity.Spacecraft;
import tech.xavi.spacecraft.service.SpacecraftService;

import java.util.List;

@Log4j2
@RestController
@RequestMapping(EndPoints.EP_SPACECRAFT) @RequiredArgsConstructor
public class SpacecraftController {

    private final SpacecraftService spacecraftService;

    @GetMapping
    public ResponseEntity<List<Spacecraft>> getAllSpacecrafts(@RequestParam(required = false) Integer page,
                                                              @RequestParam(required = false) Integer size) {
        return ResponseEntity.ok(
                (page != null && size != null)
                        ? spacecraftService.getAllSpacecrafts(page,size)
                        : spacecraftService.getAllSpacecrafts()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Spacecraft> getSpacecraftById(@PathVariable long id) {
        return ResponseEntity.ok(spacecraftService.getSpacecraftById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Spacecraft>> getSpacecraftsByNameContains(@RequestParam String name) {
        return ResponseEntity.ok(spacecraftService.getSpacecraftsByNameContains(name));
    }

    @PostMapping
    public ResponseEntity<Spacecraft> createSpacecraft(@RequestBody Spacecraft spacecraft) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(spacecraftService.createSpacecraft(spacecraft));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Spacecraft> updateSpacecraft(@PathVariable long id, @RequestBody Spacecraft spacecraft) {
        return ResponseEntity.ok(spacecraftService.updateSpacecraft(id, spacecraft));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpacecraft(@PathVariable long id) {
        spacecraftService.deleteSpacecraft(id);
        return ResponseEntity.noContent().build();
    }

}
