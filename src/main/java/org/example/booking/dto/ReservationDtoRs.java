package org.example.booking.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class ReservationDtoRs {
    private String resNumber;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private String clientName;
    private String roomName;
}
