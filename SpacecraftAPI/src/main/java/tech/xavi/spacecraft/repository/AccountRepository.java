package tech.xavi.spacecraft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.xavi.spacecraft.entity.account.Account;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
    boolean existsAccountByUsernameIgnoreCase(String uname);
    Optional<Account> findAccountByUsernameIgnoreCase(String uname);
}
