package com.example.test.threadingtest.queue;

import com.example.test.threadingtest.service.LauncherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

public class MessageConsumer implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageConsumer.class);

    private final ActualQueue queue;

    public MessageConsumer(ActualQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            Message message = queue.takeMessage();
            if(message != null){
                String result = "Processed message from consumer: " + message.getContent();
                LOGGER.info(result);

            }
        }
    }
}
