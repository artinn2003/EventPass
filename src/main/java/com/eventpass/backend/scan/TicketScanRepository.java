package com.eventpass.backend.scan;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketScanRepository extends JpaRepository<TicketScan, Long> {
}