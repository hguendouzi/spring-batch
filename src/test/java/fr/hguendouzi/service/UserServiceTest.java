package fr.hguendouzi.service;

import fr.hguendouzi.dto.UserDTO;
import fr.hguendouzi.entity.User;
import fr.hguendouzi.mapper.UserMapper;
import fr.hguendouzi.repository.UserRepository;
import fr.hguendouzi.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author hguendouzi
 */
@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @Mock
    UserMapper mapper;
    @InjectMocks
    private UserServiceImpl service;
    @Mock
    private UserRepository repository;
    private User user;

    @BeforeEach
    void setUp() throws Exception {
        user = User.builder()
                .firstName("test")
                .lastName("test2")
                .email("test@test.fr").build();
    }

    @Test
    @DisplayName("Service: Test find all users")
    void should_be_find_all_users() {
        when(repository.findAll()).thenReturn(List.of(user));
        when(mapper.toListDTO(List.of(user)))
                .thenReturn(List.of(UserDTO.builder().firstName("test").build()));
        List<UserDTO> list = service.findAllUsers();
        assertThat(list).isNotEmpty();
    }

    @Test
    @DisplayName("Service: Test find by email")
    void should_be_find_user_by_email() {
        when(repository.findByEmail("test@test.fr")).thenReturn(Optional.of(user));
        when(mapper.toDto(user))
                .thenReturn(UserDTO.builder().firstName("test").build());
        UserDTO dto = service.findUserByEmail("test@test.fr");
        assertThat(dto.getFirstName()).isEqualTo("test");
    }


}