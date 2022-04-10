package fr.hguendouzi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.hguendouzi.dto.UserDTO;
import fr.hguendouzi.service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * @author hguendouzi
 */
@WebMvcTest(UserController.class)
class UserControllerTest {

    protected JacksonTester<UserDTO> jsonCar;
    @MockBean
    UserService service;
    @Autowired
    private MockMvc mvc;

    @BeforeEach
    protected void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    @DisplayName("Test call operation allUser not null")
    void should_return_list_of_user_when_allUsers_is_calling() throws Exception {
        UserDTO dto = new UserDTO();
        dto.setFirstName("hicham");
        Mockito.when(service.findAllUsers()).thenReturn(List.of(dto));
        MockHttpServletResponse response = mvc.perform(
                        MockMvcRequestBuilders.get("/api/user/all")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].firstName", Matchers.is("hicham")))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    @DisplayName("Test call operation allUser not null")
    void should_return_list_empty_if_not_users_found() throws Exception {
        Mockito.when(service.findAllUsers()).thenReturn(List.of(new UserDTO()));
        MockHttpServletResponse response = mvc.perform(
                        MockMvcRequestBuilders.get("/api/user/all")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].firstName", Matchers.nullValue()))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    @DisplayName("Test call operation find user by email")
    void should_return_200_when_call_findUserByName() throws Exception {
        UserDTO dto = new UserDTO();
        dto.setFirstName("hicham");
        Mockito.when(service.findUserByEmail("test@test.fr")).thenReturn(dto);
        MockHttpServletResponse response = mvc.perform(
                        MockMvcRequestBuilders.get("/api/user/test@test.fr")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName", Matchers.is("hicham")))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    }

}