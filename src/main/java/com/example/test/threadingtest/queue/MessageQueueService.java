package com.example.test.threadingtest.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

@Service
public class MessageQueueService {
    private ActualQueue queue;
    private MessageConsumer consumer;
    private ThreadPoolTaskExecutor taskExecutor;

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageQueueService.class);


    public MessageQueueService(ThreadPoolTaskExecutor taskExecutor) {
        this.queue = new ActualQueue();
        this.consumer = new MessageConsumer(queue);
        this.taskExecutor = taskExecutor;
        taskExecutor.execute(consumer);
    }


    public void pushMessage(Message message, MessageProcessingCallback callback) {
        queue.addMessage(message);
        callback.onComplete("Message processed: " + message.getContent());
    }
}
