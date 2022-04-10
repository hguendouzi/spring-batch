package fr.hguendouzi.service.impl;

import fr.hguendouzi.dto.UserDTO;
import fr.hguendouzi.entity.User;
import fr.hguendouzi.mapper.UserMapper;
import fr.hguendouzi.repository.UserRepository;
import fr.hguendouzi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hguendouzi
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserDTO> findAllUsers() {
        return userMapper.toListDTO((List<User>) userRepository.findAll());
    }

    @Override
    public UserDTO findUserByEmail(String email) {
        return userMapper.toDto(userRepository.findByEmail(email).orElse(new User()));
    }
}
