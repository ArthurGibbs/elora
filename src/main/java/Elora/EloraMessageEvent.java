package Elora;

import Elora.dto.User_DTO;

public class EloraMessageEvent {
    String message;
    User_DTO sender;

    public EloraMessageEvent(String message, User_DTO sender) {
        this.message = message;
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public User_DTO getSender() {
        return sender;
    }
}
