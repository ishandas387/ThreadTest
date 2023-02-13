package com.example.test.threadingtest.controller;


import com.example.test.threadingtest.service.LauncherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class Controller {

    @Autowired
    LauncherService launcherService;

    @GetMapping("/alive")
    public String alive(){
        return "hi";
    }

    @GetMapping("/emulate")
    public String emulateEvent(@RequestParam (required = false) String type){
        launcherService.emulate(type);
        return "ok";
    }

    @GetMapping("/type")
    public String emulateEvents(@RequestParam (required = false, defaultValue = "instrument_type") String type){


        ExecutorService executorService = Executors.newFixedThreadPool(15);
        List<String> types = List.of("positions", "transactions", "instruments");

        executorService.submit( () -> launcherService.queuePushAndWait(types.get(0), "p"+ LocalDateTime.now().getNano()));
        executorService.submit( () -> launcherService.queuePushAndWait(types.get(0), "p"+ LocalDateTime.now().getNano()));
        executorService.submit( () -> launcherService.queuePushAndWait(types.get(0), "p"+ LocalDateTime.now().getNano()));
        executorService.submit( () -> launcherService.queuePushAndWait(types.get(0), "p"+ LocalDateTime.now().getNano()));
        executorService.submit( () -> launcherService.queuePushAndWait(types.get(0), "p"+ LocalDateTime.now().getNano()));

        executorService.submit( () -> launcherService.queuePushAndWait(types.get(1), "t"+ LocalDateTime.now().getNano()));
        executorService.submit( () -> launcherService.queuePushAndWait(types.get(1), "t"+ LocalDateTime.now().getNano()));
        executorService.submit( () -> launcherService.queuePushAndWait(types.get(1), "t"+ LocalDateTime.now().getNano()));
        executorService.submit( () -> launcherService.queuePushAndWait(types.get(1), "t"+ LocalDateTime.now().getNano()));
        executorService.submit( () -> launcherService.queuePushAndWait(types.get(1), "t"+ LocalDateTime.now().getNano()));

        executorService.submit( () -> launcherService.queuePushAndWait(types.get(2), "i"+ LocalDateTime.now().getNano()));
        executorService.submit( () -> launcherService.queuePushAndWait(types.get(2), "i"+ LocalDateTime.now().getNano()));
        executorService.submit( () -> launcherService.queuePushAndWait(types.get(2), "i"+ LocalDateTime.now().getNano()));
        executorService.submit( () -> launcherService.queuePushAndWait(types.get(2), "i"+ LocalDateTime.now().getNano()));
        executorService.submit( () -> launcherService.queuePushAndWait(types.get(2), "i"+ LocalDateTime.now().getNano()));

     /*   for(int i =0; i<5; i++) {
            executorService.submit( () -> launcherService.queuePushAndWait(types.get(0), UUID.randomUUID().toString()));
            executorService.submit( () -> launcherService.queuePushAndWait(types.get(2), UUID.randomUUID().toString()));

        }
        for(int i =0; i<5; i++) {
            executorService.submit( () -> launcherService.queuePushAndWait(types.get(1), UUID.randomUUID().toString()));

        }
        for(int i =0; i<5; i++) {
            executorService.submit( () -> launcherService.queuePushAndWait(types.get(2), UUID.randomUUID().toString()));

        }*/
        executorService.shutdown();
        return "hmm";
    }
}
