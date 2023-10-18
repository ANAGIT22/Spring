
package com.demo.Spring.testutils;

import com.demo.Spring.entities.model.Currency;
import com.demo.Spring.entities.model.Event;
import com.demo.Spring.entities.model.EventType;
import com.demo.Spring.entities.model.Price;
import com.demo.Spring.entities.model.TicketType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
public class EventTestUtils {
    public static final Event validFreeEvent = new Event(1, EventType.ENCUENTRO_METLAB, "Met Lab Web", LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2), 10, "Met", List.of());
    public static final Event validPricedEvent = new Event(2, EventType.ANIVERSARIO, "Gala Met", LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2), 10, "Met", List.of(new Price(TicketType.VIP_ONE_DAY, Currency.ARS, BigDecimal.TEN.doubleValue())));
    public static final Event invalidDatesEvent = new Event(1, EventType.ENCUENTRO_METLAB, "Met Lab Web", LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(1), 10, "Met", List.of());

    public static Event eventToCreate() {
        Event newEvent = new Event();
        newEvent.setType(validFreeEvent.getType());
        newEvent.setName(validFreeEvent.getName());
        newEvent.setAttendees(validFreeEvent.getAttendees());
        newEvent.setOrganizer(validFreeEvent.getOrganizer());
        newEvent.setStartDateTime(validFreeEvent.getStartDateTime());
        newEvent.setEndDateTime(validFreeEvent.getEndDateTime());

        return newEvent;
    }

    public static Event eventToUpdate() {
        Event newEvent = new Event();
        newEvent.setType(validPricedEvent.getType());
        newEvent.setName(validPricedEvent.getName());
        newEvent.setAttendees(validPricedEvent.getAttendees());
        newEvent.setOrganizer(validPricedEvent.getOrganizer());
        newEvent.setStartDateTime(validPricedEvent.getStartDateTime());
        newEvent.setEndDateTime(validPricedEvent.getEndDateTime());
        newEvent.setPrices(validPricedEvent.getPrices());
        return newEvent;
    }
}