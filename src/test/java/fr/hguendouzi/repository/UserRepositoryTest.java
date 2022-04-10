package fr.hguendouzi.repository;

import fr.hguendouzi.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author hguendouzi
 */
@DataJpaTest(showSql = true)
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;
    User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .firstName("test")
                .lastName("test")
                .email("test@test.fr")
                .address("64 aBoulevard Brune paris 75014")
                .build();
    }


    @Test
    @DisplayName("Repository: save user")
    void should_be_save_user() {
        User userSaved = userRepository.save(user);
        assertThat(userSaved.getId()).isNotNull();
    }


    @Test
    @DisplayName("Repository: find user bey email")
    void should_be_find_user_by_email() {
        userRepository.save(user);
        User userSaved = userRepository.findByEmail("test@test.fr").get();
        assertThat(userSaved.getId()).isNotNull();
    }

}