package com.jaksimsam1.authservice.domain.auth.controller

import com.jaksimsam1.authservice.domain.auth.entity.AuthUsers
import com.jaksimsam1.authservice.domain.auth.service.AuthService
import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequiredArgsConstructor
@RestController
//@RequestMapping("/auth/api/v1")
class AuthController (
    private val authService: AuthService,
){

//    @GetMapping("/{userId}")
//    suspend fun authenticate(@PathVariable("userId") userId: Long): AuthUsers {
//        return authService.();
//    }
}