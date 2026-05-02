package com.yas.webhook.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.yas.webhook.model.viewmodel.webhook.WebhookDetailVm;
import com.yas.webhook.model.viewmodel.webhook.WebhookListGetVm;
import com.yas.webhook.model.viewmodel.webhook.WebhookPostVm;
import com.yas.webhook.model.viewmodel.webhook.WebhookVm;
import com.yas.webhook.service.WebhookService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = WebhookController.class, excludeAutoConfiguration = org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class)
class WebhookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WebhookService webhookService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void getPageableWebhooks_shouldReturnPage() throws Exception {
        WebhookListGetVm response = new WebhookListGetVm(List.of(), 0, 10, 0, 0, true);
        when(webhookService.getPageableWebhooks(anyInt(), anyInt())).thenReturn(response);

        mockMvc.perform(get("/backoffice/webhooks/paging"))
                .andExpect(status().isOk());
    }

    @Test
    void listWebhooks_shouldReturnList() throws Exception {
        WebhookVm vm = new WebhookVm(1L, "http://localhost");
        when(webhookService.findAllWebhooks()).thenReturn(List.of(vm));

        mockMvc.perform(get("/backoffice/webhooks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].payloadUrl").value("http://localhost"));
    }

    @Test
    void getWebhook_shouldReturnWebhook() throws Exception {
        WebhookDetailVm vm = new WebhookDetailVm(1L, "http://localhost", "secret", "secret", true, List.of());
        when(webhookService.findById(1L)).thenReturn(vm);

        mockMvc.perform(get("/backoffice/webhooks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.payloadUrl").value("http://localhost"));
    }

    @Test
    void createWebhook_shouldReturnCreated() throws Exception {
        WebhookPostVm postVm = new WebhookPostVm("http://localhost", "secret", "secret", true, List.of());
        WebhookDetailVm vm = new WebhookDetailVm(1L, "http://localhost", "secret", "secret", true, List.of());
        when(webhookService.create(any(WebhookPostVm.class))).thenReturn(vm);

        mockMvc.perform(post("/backoffice/webhooks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postVm)))
                .andExpect(status().isCreated());
    }

    @Test
    void updateWebhook_shouldReturnNoContent() throws Exception {
        WebhookPostVm postVm = new WebhookPostVm("http://localhost", "secret", "secret", true, List.of());
        doNothing().when(webhookService).update(any(WebhookPostVm.class), anyLong());

        mockMvc.perform(put("/backoffice/webhooks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postVm)))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteWebhook_shouldReturnNoContent() throws Exception {
        doNothing().when(webhookService).delete(1L);

        mockMvc.perform(delete("/backoffice/webhooks/1"))
                .andExpect(status().isNoContent());
    }
}
