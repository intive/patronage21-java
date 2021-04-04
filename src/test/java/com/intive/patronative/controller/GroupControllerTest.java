package com.intive.patronative.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.intive.patronative.controller.api.GroupController;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import com.intive.patronative.dto.group.Groups;
import com.intive.patronative.service.GroupService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(GroupController.class)
class GroupControllerTest {
    private final String GROUPS_ENDPOINT = "/api/groups";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GroupService service;

    @ParameterizedTest
    @MethodSource("provideDataResultingInOkStatusResponse")
    public void groups_shouldReturnAllGroupsAndOkStatus(Groups groups) throws Exception {
        when(service.getGroups()).thenReturn(groups);
        this.mockMvc.perform(MockMvcRequestBuilders.get(GROUPS_ENDPOINT))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json(parseObjectToJson(groups)));
    }

    @ParameterizedTest
    @MethodSource("provideDataCausingNoContentStatusResponse")
    public void groups_shouldReturnNoContentStatus(Groups groups) throws Exception {
        when(service.getGroups()).thenReturn(groups);
        this.mockMvc.perform(MockMvcRequestBuilders.get(GROUPS_ENDPOINT))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    private static Stream provideDataCausingNoContentStatusResponse() {
        return Stream.of(
                new Groups(new ArrayList<>()),
                new Groups(null)
        );
    }
    private static Stream provideDataResultingInOkStatusResponse() {
        return Stream.of(
                new Groups(List.of(("Android"), ("Embedded"))),
                new Groups(List.of("Android", "Java", "QA", "JavaScript"))
        );
    }

    private String parseObjectToJson(Object object) throws JsonProcessingException {
        return new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(object);
    }
}