package com.xaoilin.translate.auth.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class TestControllerTest {

    @Autowired
    private TestController testController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(testController).build();
    }

    @Test
    void given_authorisedUser_when_queryingForProtectedResource_then_returns200OK() throws Exception {
        mockMvc.perform(get("/api/v1/test/hello"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("message").value("hi"));

    }

}