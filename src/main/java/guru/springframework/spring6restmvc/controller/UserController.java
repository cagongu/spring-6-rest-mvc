package guru.springframework.spring6restmvc.controller;


import guru.springframework.spring6restmvc.model.User;
import guru.springframework.spring6restmvc.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController {
    public static final String USER_PATH = "/api/v1/users";
    public static final String USER_PATH_ID = USER_PATH + "/{userId}";

    private final UserService userService;

    @PatchMapping(USER_PATH_ID)
    public ResponseEntity<User> updatePatchById(@PathVariable("userId") UUID id, @RequestBody User user) {

        userService.patchUserById(id, user);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(USER_PATH_ID)
    public ResponseEntity<User> deleteById(@PathVariable("userId") UUID id) {

        userService.deleteUserById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(USER_PATH_ID)
    public ResponseEntity<User> updateById(@PathVariable("userId") UUID id, @RequestBody User user) {

        userService.updateUserById(id, user);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(USER_PATH)
    public ResponseEntity<User> handlerUser(@RequestBody User user) {
        User SaveNewUser = userService.saveNewCustomer(user);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", USER_PATH + "/" + SaveNewUser.getId().toString());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping(USER_PATH)
    public List<User> getAllUser() {
        return userService.getAllUsers();
    }

    @GetMapping(USER_PATH_ID)
    public User getUserById(@PathVariable("userId") UUID userId) {
        return userService.getUserById(userId);
    }
}
