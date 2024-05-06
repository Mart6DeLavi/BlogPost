package org.martix.blogpost.users;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blogpost")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @PostMapping("/new-user")
    public String addUser(@RequestBody UserEntity user) {
        userService.addUser(user);
        return "User added";
    }
}
