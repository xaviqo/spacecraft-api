package tech.xavi.spacecraft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.xavi.spacecraft.entity.account.RefreshToken;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,String> {
}
