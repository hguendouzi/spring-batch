package fr.hguendouzi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class SpringBatchApplicationTests {

    @Test
    void contextLoads() {
        String context = "ok";
        assertThat(context).isEqualTo("ok");
    }

}
