package fr.hguendouzi.controller;

import fr.hguendouzi.dto.UserDTO;
import fr.hguendouzi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author hguendouzi
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    private final UserService userService;


    @GetMapping("/all")
    public @ResponseBody
    List<UserDTO> getAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/{email}")
    public @ResponseBody
    UserDTO findUserByEmail(@PathVariable String email) {
        return userService.findUserByEmail(email);
    }
}
