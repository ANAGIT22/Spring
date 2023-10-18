package com.demo.Spring.service;

import com.demo.Spring.entities.model.Event;
import com.demo.Spring.exceptions.ApiException;
import com.demo.Spring.repository.EventRepository;
import com.demo.Spring.testutils.EventListProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static com.demo.Spring.testutils.EventTestUtils.invalidDatesEvent;
import static com.demo.Spring.testutils.EventTestUtils.validFreeEvent;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {
    @Mock
    private EventRepository repository;

    @InjectMocks
    private EventService service;

    @DisplayName("testing getAllEvents")
    @ParameterizedTest(name = "{index}")
    @ArgumentsSource(EventListProvider.class)
    void testGetAllEvents(ArrayList<Event> events) {
        when(repository.getEvents()).thenReturn(events);
        assertEquals(events, service.getAllEvents());
    }

    @Test
    @DisplayName("testing getEventById Ok")
    void testGetEventByIdOk() {
        when(repository.find(1)).thenReturn(Optional.of(validFreeEvent));

        assertEquals(validFreeEvent, service.getEventById(1));
    }

    @Test
    @DisplayName("testing getEventById not found")
    void testGetEventByIdNotFound() {
        when(repository.find(1)).thenReturn(Optional.empty());

        ApiException exception = assertThrows(ApiException.class, () -> service.getEventById(1));
        assertEquals(404, exception.getStatus());
        assertEquals("Event 1 doesn't exists", exception.getMessage());
    }

    @Test
    @DisplayName("testing createEvent Ok")
    void testCreateEventOk() {
        assertEquals(validFreeEvent, service.createEvent(validFreeEvent));
    }

    @Test
    @DisplayName("testing updateEvent bad request")
    void testCreateEventBadReq() {
        ApiException exception = assertThrows(ApiException.class,
                () -> service.createEvent(invalidDatesEvent));
        assertEquals(400, exception.getStatus());
        assertEquals("Start date must be before end date", exception.getMessage());
    }

    @Test
    @DisplayName("testing updateEvent Ok")
    void testUpdateEventOk() {
        when(repository.find(1)).thenReturn(Optional.of(validFreeEvent));
        assertDoesNotThrow(() -> service.updateEvent(1, validFreeEvent));
    }

    @Test
    @DisplayName("testing updateEvent bad request")
    void testUpdateEventBadReq() {
        when(repository.find(1)).thenReturn(Optional.of(validFreeEvent));

        ApiException exception = assertThrows(ApiException.class,
                () -> service.updateEvent(1, invalidDatesEvent));
        assertEquals(400, exception.getStatus());
        assertEquals("Start date must be before end date", exception.getMessage());
    }

    @Test
    @DisplayName("testing updateEvent not found")
    void testUpdateNotFound() {
        when(repository.find(1)).thenReturn(Optional.empty());

        ApiException exception = assertThrows(ApiException.class, () -> service.updateEvent(1, validFreeEvent));
        assertEquals(404, exception.getStatus());
        assertEquals("Event 1 doesn't exists", exception.getMessage());
    }

    @Test
    @DisplayName("testing deleteEvent Ok")
    void testDeleteEventOk() {
        when(repository.find(1)).thenReturn(Optional.of(validFreeEvent));

        assertDoesNotThrow(() -> service.deleteEvent(1));
    }

    @Test
    @DisplayName("testing deleteEvent not found")
    void testDeleteNotFound() {
        when(repository.find(1)).thenReturn(Optional.empty());

        ApiException exception = assertThrows(ApiException.class, () -> service.deleteEvent(1));
        assertEquals(404, exception.getStatus());
        assertEquals("Event 1 doesn't exists", exception.getMessage());
    }
}