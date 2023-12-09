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
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @RequestMapping()
    public ResponseEntity<User> handlerUser(@RequestBody User user){
        User SaveNewUser = userService.saveNewCustomer(user);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/user/" + SaveNewUser.getId().toString());

        return  new  ResponseEntity<> (headers, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<User> getAllUser() {
        return userService.getAllUsers();
    }

    @RequestMapping(value = "{UserId}", method = RequestMethod.GET)
    public User getUserById(@PathVariable("UserId") UUID UserId){
        return userService.getUserById(UserId);
    }
}
