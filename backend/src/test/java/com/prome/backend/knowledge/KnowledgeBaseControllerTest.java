package com.prome.backend.knowledge;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.prome.backend.identity.User;
import com.prome.backend.identity.UserPrincipal;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class KnowledgeBaseControllerTest {
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        KnowledgeBaseController controller = new KnowledgeBaseController(
                mock(KnowledgeBaseService.class), mock(KnowledgeBaseMapper.class));
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new AuthenticationPrincipalArgumentResolver())
                .setControllerAdvice(new KnowledgeBaseExceptionHandler())
                .build();
        SecurityContextHolder.getContext().setAuthentication(
                new UserPrincipal(new User("user@example.com", "User", null)));
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void rejectsInvalidCreateRequest() throws Exception {
        mockMvc.perform(post("/api/knowledge-bases")
                        .contentType("application/json")
                        .content("{\"name\":\"\"}"))
                .andExpect(status().isBadRequest());
    }
}
