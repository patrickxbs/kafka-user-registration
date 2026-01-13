package com.patrick.producer.service;

import com.patrick.producer.domain.User;
import com.patrick.producer.dto.UserRequestDto;
import com.patrick.producer.dto.UserResponseDto;
import com.patrick.producer.event.UserCodeEvent;
import com.patrick.producer.event.UserConfirmedEvent;
import com.patrick.producer.mapper.UserMapper;
import com.patrick.producer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public UserResponseDto create(UserRequestDto userRequestDto) {
        User user = userMapper.toUser(userRequestDto);
        user.setConfirmed(false);
        user.setConfirmationCode(generateCode());
        UserCodeEvent userCodeEvent = new UserCodeEvent(user.getEmail(), user.getConfirmationCode());
        userRepository.save(user);

        kafkaTemplate.send("email-code", userCodeEvent);

        return userMapper.toResponse(user);
    }

    public UserResponseDto confirmationCode(Long id, String code) {
        User user = userRepository.findById(id).orElseThrow();

        if (!user.getConfirmationCode().equals(code)) throw new RuntimeException("Code invalid");
        user.setConfirmed(true);
        UserConfirmedEvent userConfirmedEvent = new UserConfirmedEvent(user.getName(), user.getEmail());
        kafkaTemplate.send("user-confirmed", userConfirmedEvent);

        return userMapper.toResponse(user);
    }

    private String generateCode() {
        int code = ThreadLocalRandom.current().nextInt(100000, 1000000);
        return String.valueOf(code);
    }
}
