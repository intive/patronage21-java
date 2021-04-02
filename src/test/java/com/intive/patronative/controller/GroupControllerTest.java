package com.intive.patronative.controller;

import org.junit.jupiter.api.Test;
import com.intive.patronative.dto.group.Groups;
import com.intive.patronative.service.GroupService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(GroupController.class)
class GroupControllerTest {
    private final String GROUPS_ENDPOINT = "/api/groups";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GroupService service;

    @Test
    public void groups() throws Exception {
        when(service.getGroups()).thenReturn(new Groups(List.of(("Android"), ("Embedded"))));
        this.mockMvc.perform(MockMvcRequestBuilders.get(GROUPS_ENDPOINT))
                .andExpect(MockMvcResultMatchers.status().isOk());

        when(service.getGroups()).thenReturn(new Groups(new ArrayList<>()));
        this.mockMvc.perform(MockMvcRequestBuilders.get(GROUPS_ENDPOINT))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        when(service.getGroups()).thenReturn(new Groups(null));
        this.mockMvc.perform(MockMvcRequestBuilders.get(GROUPS_ENDPOINT))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}