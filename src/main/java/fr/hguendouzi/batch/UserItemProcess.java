package fr.hguendouzi.batch;

import fr.hguendouzi.dto.UserDTO;
import fr.hguendouzi.entity.User;
import fr.hguendouzi.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * @author hguendouzi
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserItemProcess implements ItemProcessor<UserDTO, User> {

    private final UserMapper mapper;

    @Override
    public User process(UserDTO dto) throws Exception {
        return mapper.toEntity(dto);
    }
}
