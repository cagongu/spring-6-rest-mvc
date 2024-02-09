package guru.springframework.spring6restmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring6restmvc.model.AccountDTO;
import guru.springframework.spring6restmvc.services.AccountService;
import guru.springframework.spring6restmvc.services.AccountServiceImpl;
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
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AccountService accountService;

    @Autowired
    ObjectMapper objectMapper;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<AccountDTO> userArgumentCaptor;

    AccountServiceImpl userServiceImpl = new AccountServiceImpl();

    @Test
    void testPatchUser() throws Exception {
        AccountDTO user = userServiceImpl.getAllUsers().get(0);

        Map<String, Object> userMap = new HashMap<>();
        userMap.put("name", "ho phuc thai");

        mockMvc.perform(patch(AccountController.USER_PATH_ID, user.getId())
                        .with(httpBasic("user1", "password"))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userMap)))
                .andExpect(status().isNoContent());

        verify(accountService).patchUserById(uuidArgumentCaptor.capture(), userArgumentCaptor.capture());

        assertThat(user.getId()).isEqualTo(uuidArgumentCaptor.getValue());
        assertThat(userMap.get("name")).isEqualTo(userArgumentCaptor.getValue().getName());
    }

    @Test
    void testDeleteUser() throws Exception {
        AccountDTO user = userServiceImpl.getAllUsers().get(0);

        given(accountService.deleteUserById(any())).willReturn(true);

        mockMvc.perform(delete(AccountController.USER_PATH_ID, user.getId())
                        .with(httpBasic("user1", "password"))

                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(accountService).deleteUserById(uuidArgumentCaptor.capture());

        assertThat(user.getId()).isEqualTo(uuidArgumentCaptor.getValue());

    }

    @Test
    void testUpdateUser() throws Exception {
        AccountDTO user = userServiceImpl.getAllUsers().get(0);

        given(accountService.updateUserById(any(), any())).willReturn(Optional.of(user));

        mockMvc.perform(put(AccountController.USER_PATH_ID, user.getId())
                        .with(httpBasic("user1", "password"))
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(accountService).updateUserById(any(UUID.class), any(AccountDTO.class));

    }

    @Test
    void testCreateNewUser() throws Exception {
        AccountDTO user = userServiceImpl.getAllUsers().get(0);

        user.setName(null);

        given(accountService.saveNewCustomer(any(AccountDTO.class)))
                .willReturn(userServiceImpl.getAllUsers().get(1));

        mockMvc.perform(post(AccountController.USER_PATH)
                        .with(httpBasic("user1", "password"))

                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testGetAllUsers() throws Exception {
        given(accountService.getAllUsers()).willReturn(userServiceImpl.getAllUsers());

        mockMvc.perform(get(AccountController.USER_PATH)
                        .with(httpBasic("user1", "password"))

                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(2)));
    }

    @Test
    void getByIdNotFound() throws Exception{
        given(accountService.getUserById(any(UUID.class))).willThrow(NotFoundException.class);

        mockMvc.perform(get(AccountController.USER_PATH_ID, UUID.randomUUID())
                .with(httpBasic("user1", "password")))
                .andExpect(status().isNotFound()) ;
    }

    @Test
    void getUserById() throws Exception {
        AccountDTO user = userServiceImpl.getAllUsers().get(0);

        given(accountService.getUserById(user.getId())).willReturn(Optional.of(user));

        mockMvc.perform(get(AccountController.USER_PATH_ID, user.getId())
                        .with(httpBasic("user1", "password"))

                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(user.getName())));
    }
}