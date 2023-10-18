package com.demo.Spring.controllers;

import com.demo.Spring.entities.model.Event;
import com.demo.Spring.service.EventService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@Getter
@Setter
@RestController

@RequestMapping("/met/metcamp/web/events")
public class EventController {
 //Controlador: enlazas la vista a una ruta ,accediendo por medio de los metodos.
 //get,post,put,patch,delete etc.
    @Autowired
    private EventService eventService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllEvents() {
        return ResponseEntity.ok(Map.of("events", eventService.getAllEvents()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Event>> getEventById(@PathVariable int id) {
        return ResponseEntity.ok(Map.of("event", eventService.getEventById(id)));


    }

    @PostMapping
    public ResponseEntity<Map<String, Event>> createEvent(@Valid @RequestBody Event event) {
        return ResponseEntity.status(201).body(Map.of("event", eventService.createEvent(event)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Event>> updateEvent(@PathVariable int id,
                                                          @RequestBody Event body) {
        eventService.updateEvent(id, body);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteEvent(@PathVariable int id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

}
