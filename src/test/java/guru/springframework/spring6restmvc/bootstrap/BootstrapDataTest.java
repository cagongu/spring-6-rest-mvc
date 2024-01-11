package guru.springframework.spring6restmvc.bootstrap;

import guru.springframework.spring6restmvc.repositories.BeerRepository;
import guru.springframework.spring6restmvc.repositories.CustomerRepository;
import guru.springframework.spring6restmvc.repositories.UserRepository;
import guru.springframework.spring6restmvc.services.BeerCSVService;
import guru.springframework.spring6restmvc.services.BeerCSVServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(BeerCSVServiceImpl.class)
class BootstrapDataTest {

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BeerCSVService beerCSVService;

    @Autowired
    UserRepository userRepository;

    BootstrapData bootstrapData;

    @BeforeEach
    void setUp() {
        bootstrapData = new BootstrapData(userRepository, customerRepository, beerRepository, beerCSVService);
    }

    @Test
    void run() throws Exception {
        bootstrapData.run(null);

        assertThat(beerRepository.count()).isEqualTo(2410);
        assertThat(customerRepository.count()).isEqualTo(3);
        assertThat(userRepository.count()).isEqualTo(2);
    }
}