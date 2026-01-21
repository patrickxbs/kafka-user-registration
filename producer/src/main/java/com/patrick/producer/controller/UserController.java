package com.patrick.producer.controller;

import com.patrick.producer.dto.UserRequestDto;
import com.patrick.producer.dto.UserResponseDto;
import com.patrick.producer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> create(@RequestBody UserRequestDto userRequestDto) {
        UserResponseDto userResponseDto = userService.create(userRequestDto);
        return ResponseEntity.ok(userResponseDto);
    }

    @PostMapping("/{id}/confirm/{code}")
    public ResponseEntity<UserResponseDto> confirmUser(@PathVariable("id") Long id, @PathVariable("code") String code) {
        UserResponseDto userResponseDto = userService.confirmUser(id, code);
        return ResponseEntity.ok(userResponseDto);
    }
}
