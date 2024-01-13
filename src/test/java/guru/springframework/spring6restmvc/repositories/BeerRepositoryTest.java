package guru.springframework.spring6restmvc.repositories;

import guru.springframework.spring6restmvc.bootstrap.BootstrapData;
import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.model.BeerStyle;
import guru.springframework.spring6restmvc.services.BeerCSVServiceImpl;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@Import({BootstrapData.class, BeerCSVServiceImpl.class})
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testGetBeerListByName() {
        Page<Beer> list = beerRepository.findAllByBeerNameIsLikeIgnoreCase("%IPA%", null);

        assertThat(list.getContent().size()).isEqualTo(336);
    }

    @Test
    void testSaveBeerToLong() {
        assertThrows(ConstraintViolationException.class, () -> {
            Beer beer = beerRepository.save(Beer.builder()
                    .beerName("New Name sadsadsadsadsadddddddddddddddddddddddddddddddddddddddddddddddd")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .upc("12312321321")
                    .price(new BigDecimal("11.99"))
                    .build());

            beerRepository.flush();
            assertThat(beer).isNotNull();
            assertThat(beer.getId()).isNotNull();
        });
    }

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