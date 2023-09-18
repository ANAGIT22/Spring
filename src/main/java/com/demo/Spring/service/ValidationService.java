package com.demo.Spring.service;

import com.demo.Spring.entities.model.Event;
import com.demo.Spring.exceptions.ValidationException;

import java.time.LocalDateTime;

public class ValidationService {

    public void validateCreateEvent(Event event) {
        validateId(event.getId());
        validateName(event.getName());
        validateDates(event.getStartDateTime(), event.getEndDateTime());
    }
    public void validateUpdateEvent(Event event) {
        validateName(event.getName());
        validateDates(event.getStartDateTime(), event.getEndDateTime());
    }

    public void validateName (String name) {
        if (name.isEmpty()) {
            throw new ValidationException("name is required");
        }
        if (name.length() < 5) {
            throw new ValidationException("name is too short");
        }
    }

    public void validateId(int id) {
        if(id == 0) {
            throw new ValidationException("id must not be zero");
        }  else if (id < 0) {
            throw new ValidationException("id must be positive");
        }
    }

    public void validateDates(LocalDateTime startDate, LocalDateTime endDate) {
        //Te animás a completar este método?
    }

    //Este método es un ejemplo, podes adaptarlo para que funcione
    // igual que los otros y también tendrás que adaptar los tests
    public boolean validateAttendees(int cantidad) {
        if (cantidad > 0 && cantidad <= 50) {
            return true;
        }
        return false;
    }
}