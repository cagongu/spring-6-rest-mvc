package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final Map<UUID, User> mapUser;

    public UserServiceImpl() {
        User user1 = User.builder()
                .id(UUID.randomUUID())
                .name("thai")
                .years(2003)
                .build();

        mapUser = new HashMap<>();

        mapUser.put(user1.getId(), user1);
    }

    @Override
    public User getUserById(UUID uuid) {
        return mapUser.get(uuid);
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(mapUser.values());
    }

    @Override
    public User saveNewCustomer(User user) {
        User saveUser = User.builder()
                .id(UUID.randomUUID())
                .name(user.getName())
                .years(user.getYears())
                .build();
        mapUser.put(saveUser.getId(), user);
        return saveUser;
    }
}
