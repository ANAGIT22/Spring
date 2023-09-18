package com.demo.Spring.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.demo.Spring.entities.model.Event;
import com.demo.Spring.exceptions.ConvertionException;

import java.util.ArrayList;

public class MapperUtils {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public Event mapToEvent(String input) {
        try {
            return MAPPER.readValue(input, Event.class);
        } catch (JsonProcessingException e) {
            throw new ConvertionException(e);
        }
    }

    public ArrayList<Event> mapToEventList(String input) {
        try {
            TypeReference<ArrayList<Event>> typeRef = new TypeReference<>() {
            };
            return MAPPER.readValue(input, typeRef);
        } catch (JsonProcessingException e) {
            throw new ConvertionException(e);
        }
    }

    public String mapToJson(Event event) {
        try {
            return MAPPER.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            throw new ConvertionException(e);
        }
    }

    public String mapToJson(ArrayList<Event> eventList) {
        try {
            return MAPPER.writeValueAsString(eventList);
        } catch (JsonProcessingException e) {
            throw new ConvertionException(e);
        }
    }
}