package com.example.test.threadingtest.subject;


import org.springframework.context.ApplicationEvent;

public class MessageConsumedEvent extends ApplicationEvent {
    private final String message;

    public MessageConsumedEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

