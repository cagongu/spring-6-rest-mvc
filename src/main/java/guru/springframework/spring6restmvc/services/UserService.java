package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.UserDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserDTO getUserById(UUID uuid);

    List<UserDTO> getAllUsers();

    UserDTO saveNewCustomer(UserDTO user);

    void updateUserById(UUID id, UserDTO user);

    void deleteUserById(UUID id);

    void patchUserById(UUID id, UserDTO user);
}
