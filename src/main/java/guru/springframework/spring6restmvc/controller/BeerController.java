package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@Slf4j
public class BeerController {
    public static final String BEER_PATH = "/api/v1/beer";
    public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";
    private final BeerService beerService;

    @PatchMapping(BEER_PATH_ID)
    public ResponseEntity<BeerDTO> updateBeerPatchById(@PathVariable("beerId") UUID id, @RequestBody BeerDTO beer) {

        beerService.patchBeerById(id, beer);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(BEER_PATH_ID)
    public ResponseEntity<BeerDTO> deleteById(@PathVariable("beerId") UUID id) {

        beerService.deleteBeerById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(BEER_PATH_ID)
    public ResponseEntity<BeerDTO> updateById(@PathVariable("beerId") UUID id, @RequestBody BeerDTO beer) {

        beerService.updateBeerById(id, beer);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PostMapping(BEER_PATH)
    public ResponseEntity<BeerDTO> handlePost(@RequestBody BeerDTO beer) {

        BeerDTO savedBeer = beerService.saveNewBeer(beer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", BEER_PATH + "/" + savedBeer.getId().toString());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping(value = BEER_PATH)
    public List<BeerDTO> listBeer() {
        return beerService.listBeer();
    }

    @GetMapping(value = BEER_PATH_ID)
    public BeerDTO getBeerById(@PathVariable("beerId") UUID beerId) {
        log.debug("Get Beer by Id - in controller 1234 asdf");
        return beerService.getBearById(beerId).orElseThrow(NotFoundException::new);
    }
}
