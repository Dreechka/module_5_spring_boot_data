package org.example.homework_3.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Builder
@Data
public class ReservationDtoRs {
    private String resNumber;
    private Date checkIn;
    private Date checkOut;
    private ClientDtoRs clientDtoRs;
    private RoomDtoRs roomDtoRs;

}
