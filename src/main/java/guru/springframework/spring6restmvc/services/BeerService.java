package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.model.BeerStyle;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    Optional<BeerDTO> getBearById(UUID id);

    Page<BeerDTO> listBeer(String beerName, BeerStyle beerStyle, Boolean showInventory, Integer pageNumber, Integer pageSize);

    BeerDTO saveNewBeer(BeerDTO beer);

    Optional<BeerDTO> updateBeerById(UUID id, BeerDTO beer);

    boolean deleteBeerById(UUID id);

    Optional<BeerDTO> patchBeerById(UUID id, BeerDTO beer);
}
