package com.example.test.threadingtest.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Map;
import java.util.Queue;

@Service
public class SparkService {

  @Autowired QueueManager queueManager;
    private static final Logger LOGGER = LoggerFactory.getLogger(LauncherService.class);

  //    @EventListener(ApplicationReadyEvent.class)
  public void onReady() {
    int currentQueue = 0;
    while (true) {
      Map<String, Queue<String>> mapOfQueues = queueManager.mapOfQueue;
      if (!mapOfQueues.isEmpty()) {
        Iterator<String> iterator = mapOfQueues.keySet().iterator();
        while (iterator.hasNext()) {

          String key = (String) mapOfQueues.keySet().toArray()[currentQueue];
          Queue<String> current = mapOfQueues.get(key);
          if (current.isEmpty()) {
            // Check if all queues are empty, if yes break the loop
            boolean allQueuesEmpty = true;
            for (Queue<String> queue : mapOfQueues.values()) {
              if (!queue.isEmpty()) {
                allQueuesEmpty = false;
                break;
              }
            }
            if (allQueuesEmpty) {
              break;
            } else {
              // if the current queue is empty, move to the next queue
              currentQueue = (currentQueue + 1) % mapOfQueues.size();
            }
          } else {
            // if the current queue is not empty, pop the head and process it
            String value = current.poll();
            System.out.println("Processing value " + value + " from queue " + key);
            // move to the next queue
            currentQueue = (currentQueue + 1) % mapOfQueues.size();
          }
        }
      }
    }
  }

  @EventListener(ApplicationReadyEvent.class)
  public void onReadyForQConsumer() throws InterruptedException {
      int index = 0;
      Map<String, Queue<String>> mapOfQueues = queueManager.mapOfQueue;
      LOGGER.info("in map now {}", mapOfQueues);
      while (true) {
          if (!mapOfQueues.isEmpty()) {

          String[] queueNames = mapOfQueues.keySet().toArray(new String[0]);
          Queue<String> queue = mapOfQueues.get(queueNames[index]);
          String headOfQueue = queue.poll();
          if (headOfQueue != null) {
              // retury
              System.out.println("Reading from " + queueNames[index] + ": " + headOfQueue);
              queueManager.getMapOfProcessedMessages().putIfAbsent(headOfQueue, "SUCCESS");
          }
          index = (index + 1) % queueNames.length;
          }

      }

  }
}
