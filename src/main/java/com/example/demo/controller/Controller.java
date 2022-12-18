package com.example.demo.controller;


import com.example.demo.domain.Event;
import com.example.demo.producer.Producer;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class Controller {

    @Autowired
    Producer producer;

    @GetMapping("/start")
    public void start(@RequestBody Event event) throws JsonProcessingException {

        List<Integer> iteration = new ArrayList<>();
        List<Long> sync = new ArrayList<>();
        List<Long> async = new ArrayList<>();

        for (int i = 0; i <= 1000000; i+=50000) {
            iteration.add(i);

            long beforeTime1 = System.currentTimeMillis();
            for (int j=1 ; j<=i ; j++){
                producer.sendEventSynchronous(event);
            }
            long afterTime1 = System.currentTimeMillis();
            long secDiffTime1 = (afterTime1 - beforeTime1);
            sync.add(secDiffTime1);

            long beforeTime2 = System.currentTimeMillis();
            for(int j=1 ; j<=i ; j++){
                producer.sendEventAsynchronous(event);
            }
            long afterTime2 = System.currentTimeMillis();
            long secDiffTime2 = (afterTime2 - beforeTime2);
            async.add(secDiffTime2);
        }
//        System.out.println(iteration.toArray());
        System.out.println(iteration.toString());
        System.out.println(sync.toString());
        System.out.println(async.toString());

    }
}
