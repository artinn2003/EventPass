package com.eventpass.backend.scan;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventpass.backend.ticket.Ticket;
import com.eventpass.backend.ticket.TicketRepository;
import com.eventpass.backend.ticket.TicketStatus;
import com.eventpass.backend.user.User;
import com.eventpass.backend.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/scans")
@RequiredArgsConstructor
public class ScanController {

    private final TicketRepository ticketRepository;
    private final TicketScanRepository ticketScanRepository;
    private final UserRepository userRepository;

    @PostMapping
    public ScanTicketResponse scanTicket(@RequestBody ScanTicketRequest request) {

        Ticket ticket = ticketRepository.findByQrCodeToken(request.getQrCodeToken())
        .orElse(null);

        if (ticket == null) {
            return new ScanTicketResponse("INVALID", "QR code does not exist", 0);
}

        User checker = userRepository.findById(request.getCheckerId())
                .orElseThrow(() -> new RuntimeException("Checker not found"));

        String result;

        if (null == ticket.getStatus()) {
            result = "VALID";

            ticket.setStatus(TicketStatus.USED);
            ticket.setScanCount(ticket.getScanCount() + 1);
            ticketRepository.save(ticket);
        } else switch (ticket.getStatus()) {
            case USED -> result = "ALREADY_USED";
            case CANCELLED -> result = "CANCELLED";
            default -> {
                result = "VALID";
                ticket.setStatus(TicketStatus.USED);
                ticket.setScanCount(ticket.getScanCount() + 1);
                ticketRepository.save(ticket);
            }
        }

        TicketScan scan = TicketScan.builder()
                .ticket(ticket)
                .checker(checker)
                .result(result)
                .scannedAt(LocalDateTime.now())
                .build();

        ticketScanRepository.save(scan);

        return new ScanTicketResponse(result, "Ticket scan completed", ticket.getScanCount());
    }
}