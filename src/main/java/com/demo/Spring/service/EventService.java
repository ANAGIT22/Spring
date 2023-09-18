package com.demo.Spring.service;

import com.demo.Spring.entities.model.Event;
import com.demo.Spring.exceptions.AlreadyExistsException;
import com.demo.Spring.exceptions.NotFoundException;
import com.demo.Spring.repository.EventRepository;
import com.demo.Spring.utils.MapperUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

import static com.demo.Spring.utils.AppConstants.eventServiceMessages.*;
@Service
//@RestController

public class EventService {

    private static final Logger logger = LogManager.getLogger(EventService.class);

    private final MapperUtils mapperUtils;
    private final EventRepository repository;
    private final ValidationService validationService;

    @Autowired
    public EventService(MapperUtils mapperUtils, EventRepository repository, ValidationService validationService) {
        this.mapperUtils = mapperUtils;
        this.repository = repository;
        this.validationService = validationService;
    }

  /*public Response createEvent(String json) {
        try {
            Event event = mapperUtils.mapToEvent(json);
            validationService.validateCreateEvent(event);
            Optional<Event> foundEvent = repository.find(event.getId());
            if (foundEvent.isPresent()) {
                return new Response(400, "Event already exists");
            } else {
                repository.add(event);
                return new EventResponse(201, "Event Created", event);
            }
        } catch (ConvertionException e) {
            return new Response(400, "Malformed Event");
        } catch (RepoException e) {
            return new Response(500, e.getMessage());
        } catch (ValidationException e) {
            return new Response(400, e.getMessage());
        }
    }*/

    public Event createEvent(String json) throws JsonProcessingException {
        Event event = mapperUtils.mapToEvent(json);
        validationService.validateCreateEvent(event);
        Optional<Event> foundEvent = repository.find(event.getId());

        if (foundEvent.isPresent()) {

            logger.error(ERROR_MESSAGE_ALREADY_EXISTS);
            throw new AlreadyExistsException(ERROR_MESSAGE_ALREADY_EXISTS);

        } else {
            repository.add(event);
            logger.info(SUCCESS_MESSAGE_CREATED);
            return event;
        }
    }

   public ArrayList<Event> getAllEvents() {
        /*log.info("info - Obteniendo todos los eventos");
        log.severe("severe - Obteniendo todos los eventos");
        log.warning("warning - Obteniendo todos los eventos");
        log.fine("fine - Obteniendo todos los eventos");
        ArrayList<Event> temporalList = repository.getEvents();
        if (temporalList.isEmpty()) {
            return new Response(204, "Event list empty");
        } else {
            return new EventListResponse(200, "OK", temporalList);
        }
    }*/

       ArrayList<Event> events = repository.getEvents();
       if (events.isEmpty()) {
           logger.info(SUCCESS_MESSAGE_FETCHED_LIST_EMPTY);
       } else {
           logger.info(SUCCESS_MESSAGE_FETCHED);
       }
       return events;
   }

    /*public Response getEventById(int id) {
        Optional<Event> foundEvent = repository.find(id);
        return foundEvent.isPresent()
                ? new EventResponse(200, "OK", foundEvent.get())
                : new Response(404, "Event Not Found");
    }*/

    public Event getEventById(int id) {
        Optional<Event> event = repository.find(id);
        if (event.isPresent()) {
            logger.info( SUCCESS_MESSAGE_FIND_BY_ID + id);
            return event.get();
        } else {
            logger.error(ERROR_MESSAGE_NOT_FOUND + id);
            throw new NotFoundException(ERROR_MESSAGE_NOT_FOUND + id);
        }
    }

    /*public Response updateEvent(int id, String json) {
        try {
            Optional<Event> foundEvent = repository.find(id);
            if (foundEvent.isPresent()) {
                Event newEventData = mapperUtils.mapToEvent(json);
                validationService.validateUpdateEvent(newEventData);
                repository.update(id, newEventData);
                return new EventResponse(200, "Event updated", newEventData);
            } else {
                logger.info("El id ingresado es {} ", id);
                return new Response(404, "Event Not Found");
            }
        } catch (ConvertionException e) {
            logger.error("El id ingresado es {} y los datos son {} ", id, json);
            return new Response(400, "Malformed Event");
        } catch (RepoException e) {
            return new Response(500, e.getMessage());
        }
    }*/

    public Event updateEvent(int id, String json) throws JsonProcessingException {
        Optional<Event> foundEvent = repository.find(id);

        if (foundEvent.isPresent()) {
            Event newEventData = mapperUtils.mapToEvent(json);
            validationService.validateUpdateEvent(newEventData);
            repository.update(id, newEventData);
            logger.info(SUCCESS_MESSAGE_UPDATED);
            return newEventData;
        } else {
            logger.error(ERROR_MESSAGE_NOT_FOUND + id );
            throw new NotFoundException(ERROR_MESSAGE_NOT_FOUND + id);
        }
    }

    /*public Response deleteEvent(int id) {
        try {
            Optional<Event> foundEvent = repository.find(id);
            if (foundEvent.isPresent()) {
                repository.delete(foundEvent.get().getId());
                return new Response(204, "Event Deleted");
            } else {
                return new Response(404, "Event Not Found");
            }
        } catch (RepoException e) {
            return new Response(500, e.getMessage());
        }
    }
}*/

    public boolean deleteEvent(int id) {
        Optional<Event> foundEvent = repository.find(id);

        if (foundEvent.isPresent()) {
            repository.delete(foundEvent.get().getId());
            logger.info(SUCCESS_MESSAGE_DELETED);
            return true;
        } else {
            logger.error(ERROR_MESSAGE_NOT_FOUND + id);
            throw new NotFoundException(ERROR_MESSAGE_NOT_FOUND + id);
        }
    }
}