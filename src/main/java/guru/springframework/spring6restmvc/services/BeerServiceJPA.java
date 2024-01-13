package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.mappers.BeerMapper;
import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.model.BeerStyle;
import guru.springframework.spring6restmvc.repositories.BeerRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Primary
@AllArgsConstructor
public class BeerServiceJPA implements BeerService {
    private final BeerMapper beerMapper;
    private final BeerRepository repository;

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_PAGE_SIZE = 25;

    @Override
    public Optional<BeerDTO> getBearById(UUID id) {
        return Optional.ofNullable(beerMapper.beerToBeerDto(repository.findById(id)
                .orElse(null)));
    }

    @Override
    public Page<BeerDTO> listBeer(String beerName, BeerStyle beerStyle, Boolean showInventory, Integer pageNumber, Integer pageSize) {

        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);

        Page<Beer> beerPage;

        if (StringUtils.hasText(beerName) && beerStyle == null) {
            beerPage = listBeerByName(beerName, pageRequest);
        } else if (!StringUtils.hasText(beerName) && beerStyle != null) {
            beerPage = listBeerByStyle(beerStyle, pageRequest);
        } else if (StringUtils.hasText(beerName) && beerStyle != null) {
            beerPage = listBeerByNameAndStyle(beerName, beerStyle, pageRequest);
        } else {
            beerPage = repository.findAll(pageRequest);
        }

        if (showInventory != null && !showInventory) {
            beerPage.forEach(beer -> beer.setQuantityOnHand(null));
        }

        return beerPage.map(beerMapper::beerToBeerDto);
    }

    public PageRequest buildPageRequest(Integer pageNumber, Integer pageSize) {
        int queryPageNumber;
        int queryPageSize;

        if (pageNumber != null && pageNumber > 0) {
            queryPageNumber = pageNumber - 1;
        } else {
            queryPageNumber = DEFAULT_PAGE;
        }

        if (pageSize == null) {
            queryPageSize = DEFAULT_PAGE_SIZE;
        } else {
            if (pageSize > 1000) {
                queryPageSize = 1000 ;

            } else {
                queryPageSize = pageSize;
            }
        }

        return PageRequest.of(queryPageNumber, queryPageSize);
    }

    public Page<Beer> listBeerByNameAndStyle(String beerName, BeerStyle beerStyle, Pageable pageable) {
        return repository.findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle("%" + beerName + "%", beerStyle, pageable);
    }

    public Page<Beer> listBeerByStyle(BeerStyle beerList, Pageable pageable) {
        return repository.findAllByBeerStyle(beerList, pageable);
    }

    public Page<Beer> listBeerByName(String beerName, Pageable pageable) {
        return repository.findAllByBeerNameIsLikeIgnoreCase("%" + beerName + "%", pageable);
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beer) {
        return beerMapper.beerToBeerDto(repository.save(beerMapper.beerDtoToBeer(beer)));
    }

    @Override
    public Optional<BeerDTO> updateBeerById(UUID id, BeerDTO beer) {
        AtomicReference<Optional<BeerDTO>> atomicReference = new AtomicReference<>();

        repository.findById(id).ifPresentOrElse(foundBeer -> {
            foundBeer.setBeerName(beer.getBeerName());
            foundBeer.setBeerStyle(beer.getBeerStyle());
            foundBeer.setUpc(beer.getUpc());
            foundBeer.setPrice(beer.getPrice());
            atomicReference.set(Optional.of(beerMapper
                    .beerToBeerDto(repository.save(foundBeer))));
        }, () -> atomicReference.set(Optional.empty()));
        return atomicReference.get();
    }


    @Override
    public boolean deleteBeerById(UUID id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<BeerDTO> patchBeerById(UUID id, BeerDTO beer) {
        AtomicReference<Optional<BeerDTO>> atomicReference = new AtomicReference<>();

        repository.findById(id).ifPresentOrElse(foundBeer -> {
            if (StringUtils.hasText(beer.getBeerName())) {
                foundBeer.setBeerName(beer.getBeerName());
            }

            if (beer.getBeerStyle() != null) {
                foundBeer.setBeerStyle(beer.getBeerStyle());
            }

            if (beer.getPrice() != null) {
                foundBeer.setPrice(beer.getPrice());
            }

            if (beer.getQuantityOnHand() != null) {
                foundBeer.setQuantityOnHand(beer.getQuantityOnHand());
            }

            if (StringUtils.hasText(beer.getUpc())) {
                foundBeer.setUpc(beer.getUpc());
            }
            atomicReference.set(Optional.of(beerMapper.beerToBeerDto(repository.save(foundBeer))));
        }, () -> atomicReference.set(Optional.empty()));
        return atomicReference.get();
    }
}
