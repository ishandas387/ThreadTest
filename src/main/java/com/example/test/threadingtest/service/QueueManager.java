package com.example.test.threadingtest.service;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class QueueManager {
    Map<String, Queue<String>> mapOfQueue = new ConcurrentHashMap<>();
    Map<String, String> mapOfProcessedMessages = new ConcurrentHashMap<>();

    public Map<String, Queue<String>> getMapOfQueue() {
        return mapOfQueue;
    }

    public void push(String messageType, String message) {
        if(mapOfQueue.containsKey(messageType)) {
            Queue<String> q = mapOfQueue.get(messageType);
            q.offer(message);
        } else {
            Queue<String> queue = new LinkedList<>();
            queue.offer(message);
            mapOfQueue.put(messageType, queue);
        }
    }

    public Map<String, String> getMapOfProcessedMessages() {
        return mapOfProcessedMessages;
    }
}
