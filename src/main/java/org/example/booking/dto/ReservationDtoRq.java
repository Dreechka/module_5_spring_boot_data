package org.example.booking.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class ReservationDtoRq {
    private LocalDate checkIn;
    private LocalDate checkOut;
    private ClientDtoRq clientDtoRq;
    private String roomName;
}
