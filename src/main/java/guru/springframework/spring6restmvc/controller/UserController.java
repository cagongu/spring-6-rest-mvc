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
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PatchMapping("{userId}")
    public ResponseEntity<User> updatePatchById(@PathVariable("userId") UUID id, @RequestBody User user) {

        userService.patchUserById(id, user);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<User> deleteById(@PathVariable("userId") UUID id) {

        userService.deleteUserById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{userId}")
    public ResponseEntity<User> updateById(@PathVariable("userId") UUID id, @RequestBody User user) {

        userService.updateUserById(id, user);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping()
    public ResponseEntity<User> handlerUser(@RequestBody User user) {
        User SaveNewUser = userService.saveNewCustomer(user);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/users/" + SaveNewUser.getId().toString());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<User> getAllUser() {
        return userService.getAllUsers();
    }

    @RequestMapping(value = "{UserId}", method = RequestMethod.GET)
    public User getUserById(@PathVariable("UserId") UUID UserId) {
        return userService.getUserById(UserId);
    }
}
