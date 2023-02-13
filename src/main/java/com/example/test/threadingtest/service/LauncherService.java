package com.example.test.threadingtest.service;

import com.example.test.threadingtest.queue.Message;
import com.example.test.threadingtest.queue.MessageProcessingCallback;
import com.example.test.threadingtest.queue.MessageQueueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class LauncherService {

    //send to queue and wait for the processing to complete
    private static final Logger LOGGER = LoggerFactory.getLogger(LauncherService.class);

    @Autowired
    QueueManager qManager;

    @Autowired
    MessageQueueService messageQueueService;

    public void emulate(String type) {
        String message = "message"+Math.random();
       // qManager.push(type, message);

        Message m = new Message("Hello, World!");
        messageQueueService.pushMessage(m, new MessageProcessingCallback() {
            @Override
            public void onComplete(String result) {
                LOGGER.info("on call back of main thread");
                LOGGER.info(result);
            }
        });

    }

    public void queuePushAndWait(String type, String messageId) {
        long startTime = System.nanoTime();
        long timeout = TimeUnit.SECONDS.toNanos(25); // 25 seconds
        boolean isConsumed = false;
        qManager.push(type, messageId);
        while (System.nanoTime() - startTime < timeout) {
            // do something
            // ...

            // check if the loop should be terminated
            if (qManager.mapOfProcessedMessages.containsKey(messageId)) {
                LOGGER.info("{} has been consumed", messageId);
                isConsumed = true;
                break;
            }
        }
        if(!isConsumed) {
            LOGGER.info("message not consumed in 25 secs {} ", messageId);
            //
        }

        //kafka offset
    }
}
