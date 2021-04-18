package com.intive.patronative.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intive.patronative.controller.advice.UserControllerAdvice;
import com.intive.patronative.dto.registration.UserRegistrationResponseDTO;
import com.intive.patronative.dto.registration.UserRegistrationRequestDTO;
import com.intive.patronative.exception.InvalidArgumentException;
import com.intive.patronative.service.UserProviderTestData;
import com.intive.patronative.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private MockMvc mvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new UserControllerAdvice())
                .build();
    }

    @Test
    void userAlreadyExists_shouldThrowException() throws Exception {
        lenient().when(userService.saveUser(UserProviderTestData.getUserDTO()))
                .thenThrow(new UnsupportedOperationException());

        mvc.perform(post("/api/users")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void userNotExists_shouldCreated() throws Exception {
        lenient().when(userService.saveUser(any(UserRegistrationRequestDTO.class)))
                .thenReturn(any(UserRegistrationResponseDTO.class));

        mvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(UserProviderTestData.getUserDTO()))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201));
    }

    @Test
    void userNotExists_shouldReturnConsentValidationResult() throws Exception {
        lenient().when(userService.saveUser(any(UserRegistrationRequestDTO.class)))
                .thenThrow(InvalidArgumentException.class);

        mvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(UserProviderTestData.getUserDTO()))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(422));
    }
}
