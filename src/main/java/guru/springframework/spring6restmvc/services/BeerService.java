package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    Optional<BeerDTO> getBearById(UUID id);

    List<BeerDTO> listBeer();

    BeerDTO saveNewBeer(BeerDTO beer);

    void updateBeerById(UUID id, BeerDTO beer);

    void deleteBeerById(UUID id);

    void patchBeerById(UUID id, BeerDTO beer);
}
