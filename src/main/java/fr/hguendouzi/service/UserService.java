package fr.hguendouzi.service;

import fr.hguendouzi.dto.UserDTO;

import java.util.List;

/**
 * @author hguendouzi
 */
public interface UserService {

    List<UserDTO> findAllUsers();

    UserDTO findUserByEmail(String email);
}
