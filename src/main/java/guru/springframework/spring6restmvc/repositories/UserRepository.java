package guru.springframework.spring6restmvc.repositories;

import guru.springframework.spring6restmvc.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<Account, UUID> {
}
