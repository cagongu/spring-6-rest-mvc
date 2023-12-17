package guru.springframework.spring6restmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring6restmvc.model.UserDTO;
import guru.springframework.spring6restmvc.services.UserService;
import guru.springframework.spring6restmvc.services.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<UserDTO> userArgumentCaptor;

    UserServiceImpl userServiceImpl = new UserServiceImpl();

    @Test
    void testPatchUser() throws Exception {
        UserDTO user = userServiceImpl.getAllUsers().get(0);

        Map<String, Object> userMap = new HashMap<>();
        userMap.put("name", "ho phuc thai");

        mockMvc.perform(patch(UserController.USER_PATH_ID, user.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userMap)))
                .andExpect(status().isNoContent());

        verify(userService).patchUserById(uuidArgumentCaptor.capture(), userArgumentCaptor.capture());

        assertThat(user.getId()).isEqualTo(uuidArgumentCaptor.getValue());
        assertThat(userMap.get("name")).isEqualTo(userArgumentCaptor.getValue().getName());
    }

    @Test
    void testDeleteUser() throws Exception {
        UserDTO user = userServiceImpl.getAllUsers().get(0);

        mockMvc.perform(delete(UserController.USER_PATH_ID, user.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(userService).deleteUserById(uuidArgumentCaptor.capture());

        assertThat(user.getId()).isEqualTo(uuidArgumentCaptor.getValue());

    }

    @Test
    void testUpdateUser() throws Exception {
        UserDTO user = userServiceImpl.getAllUsers().get(0);

        mockMvc.perform(put(UserController.USER_PATH_ID, user.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(userService).updateUserById(any(UUID.class), any(UserDTO.class));

    }

    @Test
    void testCreateNewUser() throws Exception {
        UserDTO user = userServiceImpl.getAllUsers().get(0);

        user.setName(null);

        given(userService.saveNewCustomer(any(UserDTO.class)))
                .willReturn(userServiceImpl.getAllUsers().get(1));

        mockMvc.perform(post(UserController.USER_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testGetAllUsers() throws Exception {
        given(userService.getAllUsers()).willReturn(userServiceImpl.getAllUsers());

        mockMvc.perform(get(UserController.USER_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(2)));
    }

    @Test
    void getByIdNotFound() throws Exception{
        given(userService.getUserById(any(UUID.class))).willThrow(NotFoundException.class);

        mockMvc.perform(get(UserController.USER_PATH_ID, UUID.randomUUID()))
                .andExpect(status().isNotFound()) ;
    }

    @Test
    void getUserById() throws Exception {
        UserDTO user = userServiceImpl.getAllUsers().get(0);

        given(userService.getUserById(user.getId())).willReturn(user);

        mockMvc.perform(get(UserController.USER_PATH_ID, user.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(user.getName())));
    }
}