package com.patrick.producer.dto;

public record UserResponseDto(Long id, String name, String email, boolean confirmed) {
}
