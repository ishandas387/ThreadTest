package com.example.test.threadingtest.service;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

class QueueManagerTest {

  @Test
  public void testQ() {
    Map<String, List<Integer>> map = new HashMap<>();

    // Adding elements to the map
    map.put("A", new ArrayList<>(List.of(1, 2, 3, 4, 5)));
    map.put("B", new ArrayList<>(List.of(6, 7, 8, 9, 10)));
    map.put("C", new ArrayList<>(List.of(11, 12, 13, 14, 15)));

    // Iterating over the map
    for (Map.Entry<String, List<Integer>> entry : map.entrySet()) {
      String key = entry.getKey();
      List<Integer> values = entry.getValue();

      System.out.println("Key: " + key);
      System.out.println("Values: " + values);

      // Reading one item from the list at a time
      for (int value : values) {
        System.out.println("Value: " + value);
      }
    }
  }

  @Test
  public void multiQ() {

    Queue<Integer> queue1 = new LinkedList<>();
    queue1.add(10);
    queue1.add(20);
    queue1.add(30);
    Queue<Integer> queue2 = new LinkedList<>();
    queue2.add(40);
    queue2.add(50);
    queue2.add(60);

    Queue<Integer> queue3 = new LinkedList<>();
    queue3.add(70);
    queue3.add(80);
    queue3.add(90);
    Queue<Integer>[] queues = new Queue[] {queue1, queue2, queue3};

    int currentQueue = 0;
    while (true) {
      Queue<Integer> current = queues[currentQueue];
      if (current.isEmpty()) {
        // Check if all queues are empty, if yes break the loop
        boolean allQueuesEmpty = true;
        for (Queue<Integer> queue : queues) {
          if (!queue.isEmpty()) {
            allQueuesEmpty = false;
            break;
          }
        }
        if (allQueuesEmpty) {
          break;
        } else {
          // if the current queue is empty, move to the next queue
          currentQueue = (currentQueue + 1) % queues.length;
        }
      } else {
        // if the current queue is not empty, pop the head and process it
        Integer value = current.poll();
        System.out.println("Processing value " + value + " from queue " + (currentQueue + 1));
        // move to the next queue
        currentQueue = (currentQueue + 1) % queues.length;
      }
    }
  }

  @Test
  public void multiQMultiThread() throws InterruptedException {

    Queue<Integer> queue1 = new LinkedList<>();
    queue1.add(10);
    queue1.add(20);
    queue1.add(30);
    Queue<Integer> queue2 = new LinkedList<>();
    queue2.add(40);
    queue2.add(50);
    queue2.add(60);

    Queue<Integer> queue3 = new LinkedList<>();
    queue3.add(70);
    queue3.add(80);
    queue3.add(90);
    Queue<Integer>[] queues = new Queue[] {queue1, queue2, queue3};

    ExecutorService e = Executors.newFixedThreadPool(1);
    e.execute(
        () -> {
          int currentQueue = 0;
          while (true) {
            Queue<Integer> current = queues[currentQueue];
            if (current.isEmpty()) {
              // Check if all queues are empty, if yes break the loop
              boolean allQueuesEmpty = true;
              for (Queue<Integer> queue : queues) {
                if (!queue.isEmpty()) {
                  allQueuesEmpty = false;
                  break;
                }
              }
              if (allQueuesEmpty) {
                continue;
              } else {
                // if the current queue is empty, move to the next queue
                currentQueue = (currentQueue + 1) % queues.length;
              }
            } else {
              // if the current queue is not empty, pop the head and process it
              Integer value = current.poll();
              System.out.println("Processing value " + value + " from queue " + (currentQueue + 1));
              // move to the next queue
              currentQueue = (currentQueue + 1) % queues.length;
            }
          }
        });
    queue3.add(100);
    Thread.sleep(4000);
    queue1.add(55);
    queue3.add(65);
    queue3.add(75);
    queue2.add(85);
    Thread.sleep(5000);
    queue2.add(100);
      Thread.sleep(5000);

  }

  @Test
  public void multiQMultiThreadMap() throws InterruptedException {
      Map<String, Queue<Integer>> mapOfQueues = new HashMap<>();
      Queue<Integer> queue1 = new LinkedList<>();
      queue1.add(10);
      queue1.add(20);
      queue1.add(30);
      Queue<Integer> queue2 = new LinkedList<>();
      queue2.add(40);
      queue2.add(50);
      queue2.add(60);
      mapOfQueues.put("Queue1", queue1);
      mapOfQueues.put("Queue2", queue2);

      ExecutorService e = Executors.newFixedThreadPool(1);
    e.execute(
        () -> {
            int currentQueue =0;
            while (true) {
                String key = (String) mapOfQueues.keySet().toArray()[currentQueue];
                Queue<Integer> current = mapOfQueues.get(key);
                if (current.isEmpty()) {
                    // Check if all queues are empty, if yes break the loop
                    boolean allQueuesEmpty = true;
                    for (Queue<Integer> queue : mapOfQueues.values()) {
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
                    Integer value = current.poll();
                    System.out.println("Processing value " + value + " from queue " + key);
                    // move to the next queue
                    currentQueue = (currentQueue + 1) % mapOfQueues.size();
                }
            }
        });

     Thread.sleep(4000);
      queue2.add(700000);
      Queue<Integer> queue3 = new LinkedList<>();
      queue2.add(400);
      queue2.add(500);
      queue2.add(600);

/*
      Queue<Integer> queue4 = new LinkedList<>();
      queue2.add(4000);
      queue2.add(5000);
      queue2.add(6000);
      mapOfQueues.put("Queue3", queue3);
      mapOfQueues.put("Queue4", queue4);
      Thread.sleep(4000);
      queue2.add(70000099);*/

      // Add a new queue after 5 seconds
      new java.util.Timer().schedule(
              new java.util.TimerTask() {
                  @Override
                  public void run() {
                      Queue<Integer> queue3 = new LinkedList<>();
                      queue3.add(70);
                      queue3.add(80);
                      queue3.add(90);
                      mapOfQueues.put("Queue3", queue3);
                  }
              },
              5000
      );
  }

}