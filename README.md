# User Registration with Kafka

Projeto simples para demonstrar o uso de **Apache Kafka** em um fluxo de cadastro de usuários com envio de e-mails, utilizando **Spring Boot** e arquitetura orientada a eventos.

## 📌 Visão geral

O sistema é composto por dois serviços:

- **Producer**: responsável pelo cadastro do usuário e publicação de eventos no Kafka  
- **Consumer**: responsável por consumir os eventos e enviar os e-mails

## 🔄 Fluxo da aplicação

1. Usuário realiza o cadastro
2. O Producer gera um código de confirmação
3. Um evento é publicado no tópico `email-code`
4. O Consumer consome o evento e envia o e-mail com o código
5. O usuário confirma o código
6. O Producer publica um evento no tópico `user-confirmed`
7. O Consumer consome o evento e envia o e-mail de confirmação

## 🛠 Tecnologias utilizadas

- Spring Boot
- Apache Kafka
- Spring Mail

## ⚙️ Configuração

As credenciais de e-mail são configuradas via variáveis de ambiente utilizando um arquivo `.env` (não versionado).

Exemplo:
```env
MAIL_USERNAME=your_email@gmail.com
MAIL_PASSWORD=your_app_password
