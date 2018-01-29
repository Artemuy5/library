package edu.shapovalov.library.entity;

import lombok.Data;

/**
 * @author Artem Shapovalov
 * @version 0.1
 * @since 06.01.2018
 */

@Data
public class ChatMessage {
    private MessageType messageType;
    private String content;
    private String sender;

    public enum MessageType {
        CHAT, JOIN, LEAVE
    }
}
