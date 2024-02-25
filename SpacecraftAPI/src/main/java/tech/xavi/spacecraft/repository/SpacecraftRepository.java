package tech.xavi.spacecraft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.xavi.spacecraft.entity.spacecraft.Spacecraft;

import java.util.List;

@Repository
public interface SpacecraftRepository extends JpaRepository<Spacecraft,Long> {
    List<Spacecraft> findByNameContaining(String name);
}
