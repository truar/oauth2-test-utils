package com.zenika.stack.oauth2testutils.resource.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SimpleController.class)
@Import(AuthenticationManagerProvider.class)
class SimpleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
	@WithMockOAuth2Scope(token = "123456789",
            username = "resource-tester",
            scopes = "resource.read",
            authorities = "CAN_READ")
    public void test() throws Exception {
        mockMvc.perform(get("/whoami")
                .header("Authorization", "Bearer 123456789"))
                .andExpect(status().isOk())
                .andExpect(content().string("resource-tester"));
    }
}