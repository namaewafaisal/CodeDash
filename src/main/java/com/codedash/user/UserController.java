package com.codedash.user;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(
    name = "Users",
    description = "User management endpoints"
)
public class UserController {

    private final UserService userService;

    @Operation(
        summary = "Get all users",
        description = "Returns all registered users."
    )
    @GetMapping
    public List<User> all() {
        return userService.getAll();
    }
}