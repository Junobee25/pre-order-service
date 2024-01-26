package com.sideproject.preorderservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sideproject.preorderservice.dto.request.UserJoinRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void 회원가입() throws Exception {
        String email = "wnsdhqo@naver.com";
        String username = "juno";
        String password = "1234";
        String memo = "hello world";
        String profileImage = "/myPath";

        mockMvc.perform(post("/api/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserJoinRequest(email, username, password, memo, profileImage)))
                ).andDo(print())
                .andExpect(status().isOk());

    }

}