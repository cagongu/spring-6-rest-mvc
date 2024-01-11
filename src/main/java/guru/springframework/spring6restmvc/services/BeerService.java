package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    Optional<BeerDTO> getBearById(UUID id);

    List<BeerDTO> listBeer(String beerName);

    BeerDTO saveNewBeer(BeerDTO beer);

    Optional<BeerDTO> updateBeerById(UUID id, BeerDTO beer);

    boolean deleteBeerById(UUID id);

    Optional<BeerDTO> patchBeerById(UUID id, BeerDTO beer);
}
