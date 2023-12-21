package guru.springframework.spring6restmvc.controller;


import guru.springframework.spring6restmvc.model.AccountDTO;
import guru.springframework.spring6restmvc.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AccountController {
    public static final String USER_PATH = "/api/v1/users";
    public static final String USER_PATH_ID = USER_PATH + "/{userId}";

    private final AccountService accountService;

    @PatchMapping(USER_PATH_ID)
    public ResponseEntity<AccountDTO> updatePatchById(@PathVariable("userId") UUID id, @RequestBody AccountDTO accountDTO) {

        accountService.patchUserById(id, accountDTO);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(USER_PATH_ID)
    public ResponseEntity<AccountDTO> deleteById(@PathVariable("userId") UUID id) {
        if(!accountService.deleteUserById(id)){
            throw new NotFoundException();
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(USER_PATH_ID)
    public ResponseEntity<AccountDTO> updateById(@PathVariable("userId") UUID id, @RequestBody AccountDTO accountDTO) {
        if(accountService.updateUserById(id, accountDTO).isEmpty()){
            throw new NotFoundException();
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(USER_PATH)
    public ResponseEntity<AccountDTO> handlerPost(@RequestBody AccountDTO accountDTO) {
        AccountDTO SaveNewUser = accountService.saveNewCustomer(accountDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", USER_PATH + "/" + SaveNewUser.getId().toString());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping(USER_PATH)
    public List<AccountDTO> getAllUser() {
        return accountService.getAllUsers();
    }

    @GetMapping(USER_PATH_ID)
    public AccountDTO getUserById(@PathVariable("userId") UUID userId) {
        return accountService.getUserById(userId).orElseThrow(NotFoundException::new);
    }
}
