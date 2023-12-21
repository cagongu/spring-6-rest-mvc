package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.AccountDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountService {

    Optional<AccountDTO> getUserById(UUID uuid);

    List<AccountDTO> getAllUsers();

    AccountDTO saveNewCustomer(AccountDTO accountDTO);

    Optional<AccountDTO> updateUserById(UUID id, AccountDTO accountDTO);

    Boolean deleteUserById(UUID id);

    Optional<AccountDTO> patchUserById(UUID id, AccountDTO accountDTO);
}
