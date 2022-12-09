package org.example.homework_3.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class ReservationDtoRq {
    private Date checkIn;
    private Date checkOut;
    private ClientDtoRq clientDtoRq;
    private RoomDtoRq roomDtoRq;

}
