package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.CustomerDTO;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Service

public class CustomerServiceImpl implements CustomerService {

    private final Map<UUID, CustomerDTO> customerMap;

    public CustomerServiceImpl() {
        CustomerDTO customer1 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .name("Customer 1")
                .version(1)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        CustomerDTO customer2 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .name("Customer 2")
                .version(1)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        CustomerDTO customer3 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .name("Customer 3")
                .version(1)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        customerMap = new HashMap<>();
        customerMap.put(customer1.getId(), customer1);
        customerMap.put(customer2.getId(), customer2);
        customerMap.put(customer3.getId(), customer3);
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID uuid) {
        return Optional.of(customerMap.get(uuid));
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customer) {
        CustomerDTO saveCustomer = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .name(customer.getName())
                .version(customer.getVersion())
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        customerMap.put(saveCustomer.getId(),saveCustomer);

        return saveCustomer;
    }

    @Override
    public void updateCustomerById(UUID id, CustomerDTO customer) {

        CustomerDTO existing = customerMap.get(id);
        existing.setName(customer.getName());
        existing.setVersion(customer.getVersion());
        existing.setUpdateDate(customer.getUpdateDate());
        existing.setCreatedDate(customer.getCreatedDate());

        customerMap.put(id, existing);
    }

    @Override
    public void deleteCustomerById(UUID id) {
        customerMap.remove(id);
    }

    @Override
    public void patchCustomerById(UUID id, CustomerDTO customer) {
        CustomerDTO existing = customerMap.get(id);

        if(StringUtils.hasText(customer.getName())){
            existing.setName(customer.getName());
        }

        if(customer.getCreatedDate()!= null){
            existing.setCreatedDate(customer.getCreatedDate());
        }

        if(customer.getUpdateDate() != null){
            existing.setUpdateDate(customer.getUpdateDate());
        }

        if(customer.getVersion() != null){
            existing.setVersion(customer.getVersion());
        }
    }
}