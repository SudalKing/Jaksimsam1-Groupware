package com.jaksimsam1.authservice.domain.auth.controller

import com.jaksimsam1.authservice.domain.auth.service.AuthService
import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequiredArgsConstructor
@RestController
@RequestMapping("/")
class AuthController (
    private val authService: AuthService,
){

}