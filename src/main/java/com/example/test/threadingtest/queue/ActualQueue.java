package com.example.test.threadingtest.queue;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ActualQueue {
    private Queue<Message> queue = new LinkedList<>();

    public void addMessage(Message message) {
        queue.offer(message);
    }

    public Message takeMessage() {
        return queue.poll();
    }
}
