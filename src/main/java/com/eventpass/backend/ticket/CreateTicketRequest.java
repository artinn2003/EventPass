package com.eventpass.backend.ticket;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTicketRequest {

    private Long userId;
    private Long eventId;
}