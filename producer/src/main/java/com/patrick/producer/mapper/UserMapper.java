package com.patrick.producer.mapper;

import com.patrick.producer.domain.User;
import com.patrick.producer.dto.UserRequestDto;
import com.patrick.producer.dto.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    User toUser(UserRequestDto userRequestDto);

    UserResponseDto toResponse(User user);
}
