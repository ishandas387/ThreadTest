package com.example.test.threadingtest.subject;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class CustomEventObserver implements ApplicationListener<MessageConsumedEvent> {
    @Override
    public void onApplicationEvent(MessageConsumedEvent event) {
        System.out.println("Received event: " + event.getMessage());
    }
}
