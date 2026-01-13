package com.patrick.consumer.listener;

import com.patrick.consumer.dto.UserCodeEvent;
import com.patrick.consumer.dto.UserConfirmedEvent;
import com.patrick.consumer.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserListener {

    private final EmailService emailService;

    @KafkaListener(topics = "email-code", groupId = "email-code-group",
            containerFactory = "userCodeKafkaListenerContainerFactory")
    public void sendCode(UserCodeEvent userCodeEvent) {
        emailService.sendNotification(userCodeEvent.email(), "Código de confirmação de cadastro",
                String.format("""
                        Olá!
                        
                        Recebemos uma solicitação de cadastro com este e-mail.
                        
                        Seu código de confirmação é: %s
                        
                        Informe esse código para concluir seu cadastro.
                        
                        """, userCodeEvent.code()));
        log.info("Code: {} sent to email: {}", userCodeEvent.code(), userCodeEvent.email());
    }

    @KafkaListener(topics = "user-confirmed", groupId = "email-confirmed-group",
            containerFactory = "userConfirmedKafkaListenerContainerFactory")
    public void sendConfirmation(UserConfirmedEvent userConfirmedEvent) {
        emailService.sendNotification(userConfirmedEvent.email(), "Cadastro confirmado com sucesso",
                String.format("""
                        Olá, %s!
                        
                        Seu cadastro foi confirmado com sucesso.
                        
                        Agora você já pode acessar a aplicação normalmente.
                        
                        Obrigado por se cadastrar!
                        """, userConfirmedEvent.name()));
        log.info("Confirmation email sent to {}, successful delivery.", userConfirmedEvent.email());
    }
}
