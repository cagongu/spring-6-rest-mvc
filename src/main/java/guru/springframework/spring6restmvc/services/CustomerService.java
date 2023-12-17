package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    Optional<Customer> getCustomerById(UUID uuid);

    List<Customer> getAllCustomers();

    Customer saveNewCustomer(Customer customer);

    void updateCustomerById(UUID id, Customer customer);

    void deleteCustomerById(UUID id);

    void patchCustomerById(UUID id, Customer customer);
}