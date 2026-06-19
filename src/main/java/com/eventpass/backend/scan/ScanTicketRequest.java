package com.eventpass.backend.scan;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScanTicketRequest {
    private String qrCodeToken;
    private Long checkerId;
}