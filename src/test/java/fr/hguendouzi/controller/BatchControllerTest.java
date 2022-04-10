package fr.hguendouzi.controller;

import fr.hguendouzi.launcher.BatchLauncher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.batch.core.BatchStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * @author hguendouzi
 */
@WebMvcTest(BatchController.class)
class BatchControllerTest {
    @MockBean
    BatchLauncher batchLauncher;
    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Test operation launcher batch with status completed")
    void should_return_status_completed() throws Exception {
        Mockito.when(batchLauncher.runBatch()).thenReturn(BatchStatus.COMPLETED);
        MockHttpServletResponse response = mvc.perform(
                        MockMvcRequestBuilders.get("/api/launchBatch")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", Matchers.is("COMPLETED")))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    @DisplayName("Test operation launcher batch with status FAILED")
    void should_return_status_failed() throws Exception {
        Mockito.when(batchLauncher.runBatch()).thenThrow(new Exception());
        MockHttpServletResponse response = mvc.perform(
                        MockMvcRequestBuilders.get("/api/launchBatch")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", Matchers.is("FAILED")))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    }


}