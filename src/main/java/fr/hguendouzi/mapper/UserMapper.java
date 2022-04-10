package fr.hguendouzi.mapper;

import fr.hguendouzi.dto.UserDTO;
import fr.hguendouzi.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author hguendouzi
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserDTO dto);

    UserDTO toDto(User user);

    List<UserDTO> toListDTO(List<User> list);
}
