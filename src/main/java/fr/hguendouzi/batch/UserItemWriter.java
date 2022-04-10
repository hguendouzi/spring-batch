package fr.hguendouzi.batch;

import fr.hguendouzi.entity.User;
import fr.hguendouzi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author hguendouzi
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserItemWriter implements ItemWriter<User> {

    private final UserRepository repository;


    @Override
    public void write(List<? extends User> items) throws Exception {
        if (items != null && !items.isEmpty()) repository.saveAll(items);
    }
}
