package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.Customer;
import guru.springframework.spring6restmvc.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    User getUserById(UUID uuid);

    List<User> getAllUsers();

    User saveNewCustomer(User user);
}