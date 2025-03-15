package com.jaksimsam1.userservice.user.controller;

import com.jaksimsam1.commondto.model.response.ApiResponse;
import com.jaksimsam1.userservice.user.dto.UserDto;
import com.jaksimsam1.userservice.user.exception.UserNotFoundException;
import com.jaksimsam1.userservice.user.model.request.UserCreateRequest;
import com.jaksimsam1.userservice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/user/api/v1")
@RestController
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<Mono<UserDto>> findDetailUser(@PathVariable("userId") UUID userId) throws UserNotFoundException {
        return ResponseEntity.ok(userService.findUserById(userId));
    }

    @GetMapping("/all")
    public ResponseEntity<Flux<UserDto>> findUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping("/register")
    public Mono<ApiResponse<Void>> register(@RequestBody UserCreateRequest request) {
        return userService.createUser(request);
    }
}
