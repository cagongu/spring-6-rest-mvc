package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.model.User;
import guru.springframework.spring6restmvc.services.UserService;
import guru.springframework.spring6restmvc.services.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    UserServiceImpl userServiceImpl = new UserServiceImpl();

    @Test
    void getUserById()  throws  Exception{
        User user = userServiceImpl.getAllUsers().get(0);

        given(userService.getUserById(any(UUID.class))).willReturn(user);

        mockMvc.perform(get("/api/v1/users/" + UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}