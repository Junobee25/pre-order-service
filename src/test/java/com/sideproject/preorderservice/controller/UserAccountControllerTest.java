package com.sideproject.preorderservice.controller;

import com.sideproject.preorderservice.dto.UserAccountDto;
import com.sideproject.preorderservice.dto.request.UserJoinRequest;
import com.sideproject.preorderservice.service.UserAccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserAccountController.class)
@MockBean(JpaMetamodelMappingContext.class)
@WithUserDetails
class UserAccountControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    UserAccountService userAccountService;

    @DisplayName("[POST] 회원 가입 - 정상 호출")
    @Test
    void join() throws Exception {
        UserAccountDto userAccountDto = UserAccountDto.of(1L, "test", "test", "test", true, "test");
        given(userAccountService.join(any(), any(), any(), any()))
                .willReturn(userAccountDto);
        // when && then
        mvc.perform(post("/api/join").with(csrf()))
                .andExpect(status().isOk());

    }

}