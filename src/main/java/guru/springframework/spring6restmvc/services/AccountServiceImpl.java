package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.AccountDTO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class AccountServiceImpl implements AccountService {

    private final Map<UUID, AccountDTO> mapUser;

    public AccountServiceImpl() {
        this.mapUser = new HashMap<>();

        AccountDTO user1 = AccountDTO.builder()
                .id(UUID.randomUUID())
                .name("thai")
                .years(2003)
                .build();

        AccountDTO user2 = AccountDTO.builder()
                .id(UUID.randomUUID())
                .name("thai2")
                .years(2003)
                .build();

        mapUser.put(user1.getId(), user1);
        mapUser.put(user2.getId(), user2);
    }

    @Override
    public Optional<AccountDTO> getUserById(UUID uuid) {
        return Optional.of(mapUser.get(uuid));
    }

    @Override
    public List<AccountDTO> getAllUsers() {
        return new ArrayList<>(mapUser.values());
    }

    @Override
    public AccountDTO saveNewCustomer(AccountDTO accountDTO) {
        AccountDTO saveUser = AccountDTO.builder()
                .id(UUID.randomUUID())
                .name(accountDTO.getName())
                .years(accountDTO.getYears())
                .build();
        mapUser.put(saveUser.getId(), accountDTO);
        return saveUser;
    }

    @Override
    public Optional<AccountDTO> updateUserById(UUID id, AccountDTO accountDTO) {
        AccountDTO existing = mapUser.get(id);
        existing.setName(accountDTO.getName());
        existing.setYears(accountDTO.getYears());

        return Optional.of(existing);
    }

    @Override
    public Boolean deleteUserById(UUID id) {
        mapUser.remove(id);

        return  true;
    }

    @Override
    public Optional<AccountDTO> patchUserById(UUID id, AccountDTO accountDTO) {
        AccountDTO existing = mapUser.get(id);

        if(StringUtils.hasText(accountDTO.getName())){
            existing.setName(accountDTO.getName());
        }

        if (accountDTO.getYears() != existing.getYears()) {
            existing.setYears(accountDTO.getYears());
        }

        return Optional.of(existing);
    }
}
