package com.demo.Spring.entities.model;

import lombok.*;

@AllArgsConstructor
@Getter @Setter
@ToString
@NoArgsConstructor
public class Price {
    private TicketType type;
    private Currency currency;
    private double value;
}
