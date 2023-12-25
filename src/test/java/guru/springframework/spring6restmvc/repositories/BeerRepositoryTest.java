package guru.springframework.spring6restmvc.repositories;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.model.BeerStyle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testSaveBeer() {
        Beer beer = beerRepository.save(Beer.builder()
                .beerName("New Name")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12312321321")
                .price(new BigDecimal("11.99"))
                .build());

        beerRepository.flush();

        assertThat(beer).isNotNull();
        assertThat(beer.getId()).isNotNull();
    }
}