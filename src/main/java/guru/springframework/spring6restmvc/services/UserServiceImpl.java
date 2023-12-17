package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.UserDTO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final Map<UUID, UserDTO> mapUser;

    public UserServiceImpl() {
        UserDTO user1 = UserDTO.builder()
                .id(UUID.randomUUID())
                .name("thai")
                .years(2003)
                .build();

        UserDTO user2 = UserDTO.builder()
                .id(UUID.randomUUID())
                .name("thai2")
                .years(2003)
                .build();

        mapUser = new HashMap<>();

        mapUser.put(user1.getId(), user1);
        mapUser.put(user2.getId(), user2);
    }

    @Override
    public UserDTO getUserById(UUID uuid) {
        return mapUser.get(uuid);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return new ArrayList<>(mapUser.values());
    }

    @Override
    public UserDTO saveNewCustomer(UserDTO user) {
        UserDTO saveUser = UserDTO.builder()
                .id(UUID.randomUUID())
                .name(user.getName())
                .years(user.getYears())
                .build();
        mapUser.put(saveUser.getId(), user);
        return saveUser;
    }

    @Override
    public void updateUserById(UUID id, UserDTO user) {

        UserDTO existing = mapUser.get(id);
        existing.setName(user.getName());
        existing.setYears(user.getYears());

        mapUser.put(id, user);

    }

    @Override
    public void deleteUserById(UUID id) {
        mapUser.remove(id);
    }

    @Override
    public void patchUserById(UUID id, UserDTO user) {
        UserDTO existing = mapUser.get(id);

        if(StringUtils.hasText(user.getName())){
            existing.setName(user.getName());
        }

        if (user.getYears() != existing.getYears()) {
            existing.setYears(user.getYears());
        }
    }
}
