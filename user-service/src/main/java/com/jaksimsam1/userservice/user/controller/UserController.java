package com.jaksimsam1.userservice.user.controller;

import com.jaksimsam1.userservice.user.dto.UserDto;
import com.jaksimsam1.userservice.user.exception.UserNotFoundException;
import com.jaksimsam1.userservice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RequestMapping("/user/api/v1")
@RestController
public class UserController {
    private final UserService userService;

    @GetMapping("/health-check")
    public ResponseEntity<Mono<UserDto>> healthCheck() {
        return ResponseEntity.ok(Mono.just(UserDto.builder()
                        .userId("Test1")
                        .build())
        );
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Mono<UserDto>> findDetailUser(@PathVariable("userId") String userId) throws UserNotFoundException {
        return ResponseEntity.ok(userService.findUserById(userId));
    }

    @GetMapping("/search")
    public ResponseEntity<Flux<UserDto>> findUsers() {
        return ResponseEntity.ok(userService.findAll());
    }
}
