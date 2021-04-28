package com.intive.patronative.controller.frontend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intive.patronative.controller.advice.UserControllerAdvice;
import com.intive.patronative.dto.model.UsersDTO;
import com.intive.patronative.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserFrontControllerTest {

    private MockMvc mvc;

    @InjectMocks
    private UserFrontController userFrontController;

    @Mock
    private UserService userService;

    private UsersDTO users;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(userFrontController)
                .setControllerAdvice(new UserControllerAdvice())
                .build();

        users = UserProviderTestData.getOneElementUsersList();
    }

    @Test
    void usersFound_shouldReturnListAndStatus200() throws Exception {
        when(userService.searchUsers(any(), any(), any(), any()))
                .thenReturn(users);

        mvc.perform(get("/frontend-api/users"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(objectMapper.writeValueAsString(users)));

        verify(userService, times(1)).searchUsers(any(), any(), any(), any());
    }

    @Test
    void usersNotFound_shouldReturnEmptyListAndStatus200() throws Exception {
        final var emptyUsers = new UsersDTO(Collections.emptyList());

        when(userService.searchUsers(any(), any(), any(), any()))
                .thenReturn(emptyUsers);

        mvc.perform(get("/frontend-api/users"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(objectMapper.writeValueAsString(emptyUsers)));

        verify(userService, times(1)).searchUsers(any(), any(), any(), any());
    }
}