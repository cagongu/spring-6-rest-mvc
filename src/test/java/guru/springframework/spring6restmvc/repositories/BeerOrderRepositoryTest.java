package guru.springframework.spring6restmvc.repositories;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.entities.BeerOrder;
import guru.springframework.spring6restmvc.entities.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BeerOrderRepositoryTest {
    @Autowired
    BeerOrderRepository beerOrderRepository;

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    CustomerRepository customerRepository;

    Customer customer;
    Beer beer;


    @BeforeEach
    void setUp() {
        customer = customerRepository.findAll().get(0);
        beer = beerRepository.findAll().get(0);
    }

    @Test
    void testBeerOrders(){
        BeerOrder beerOrder = BeerOrder.builder()
                .customerRef("Test order")
                .customer(customer)
                .build();

        BeerOrder saveBeerOrder = beerOrderRepository.saveAndFlush(beerOrder);

        System.out.println(saveBeerOrder.getCustomerRef());
    }
}