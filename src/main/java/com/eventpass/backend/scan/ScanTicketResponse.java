package com.eventpass.backend.scan;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScanTicketResponse {
    private final String result;
    private final String message;
    private final Integer scanCount;
}