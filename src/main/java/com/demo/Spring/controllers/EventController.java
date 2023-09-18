package com.demo.Spring.controllers;
import com.demo.Spring.entities.model.Event;
import com.demo.Spring.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;



import java.util.ArrayList;

import java.util.Map;

@RestController


@RequestMapping("/met/metcamp/web/events")
public class EventController {

    private final EventService eventService;
    //private EventRepository repository;
    private static final Logger logger = LogManager.getLogger(EventService.class);
    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<Map<String,Object>> getAllEvents(){
        //return ResponseEntity.ok(Map.of("events:", "[GET Lista de eventos]"));
        //return eventService.getAllEvents();
        ArrayList<Event>temporalList = eventService.getAllEvents();
        return temporalList.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : ResponseEntity.ok(Map.of("events",temporalList));


    }

    @GetMapping("/{id}")
    public ResponseEntity getEventById(@PathVariable int id) {
        //hacer cosas...
        return ResponseEntity.ok(Map.of("events:", String.format("[GET evento con id %s]", id)));
    }

    @PostMapping
    public ResponseEntity createEvent(@org.jetbrains.annotations.NotNull @RequestBody String body) {
        //hacemos cosas
        if (body.contains("*")) {
            return ResponseEntity.badRequest().body("LNo se permiten caracteres especiales");
        } else {
            return ResponseEntity.ok(Map.of("datos recibidos:", body));
        }

    }

}