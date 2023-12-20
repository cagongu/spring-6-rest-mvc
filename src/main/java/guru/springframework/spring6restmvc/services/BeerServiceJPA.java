package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.mappers.BeerMapper;
import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.repositories.BeerRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Primary
@AllArgsConstructor
public class BeerServiceJPA implements BeerService {
    private final BeerMapper beerMapper;
    private final BeerRepository repository;

    @Override
    public Optional<BeerDTO> getBearById(UUID id) {
        return Optional.ofNullable(beerMapper.beerToBeerDto(repository.findById(id)
                .orElse(null)));
    }

    @Override
    public List<BeerDTO> listBeer() {
        return repository.findAll()
                .stream()
                .map(beerMapper::beerToBeerDto)
                .collect(Collectors.toList());
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beer) {
        return beerMapper.beerToBeerDto(repository.save(beerMapper.beerDtoToBeer(beer)));
    }

    @Override
    public void updateBeerById(UUID id, BeerDTO beer) {

    }

    @Override
    public void deleteBeerById(UUID id) {

    }

    @Override
    public void patchBeerById(UUID id, BeerDTO beer) {

    }
}
