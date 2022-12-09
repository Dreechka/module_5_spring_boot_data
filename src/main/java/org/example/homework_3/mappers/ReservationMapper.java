package org.example.homework_3.mappers;

import lombok.AllArgsConstructor;
import org.example.homework_3.dto.ReservationDtoRq;
import org.example.homework_3.dto.ReservationDtoRs;
import org.example.homework_3.entities.Reservation;
import org.example.homework_3.service.ClientService;
import org.example.homework_3.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ReservationMapper {
    ClientMapper clientMapper;
    ClientService clientService;
    RoomService roomService;
    RoomMapper roomMapper;

    public Reservation convertFromDtoRq(ReservationDtoRq reservationDtoRq) {
        return new Reservation(
                reservationDtoRq.getCheckIn(),
                reservationDtoRq.getCheckOut(),
                clientService.findExistsOrAddNewClient(reservationDtoRq.getClientDtoRq()),
                roomService.findERoomByName(reservationDtoRq.getRoomDtoRq().getName())
        );
    }

    public ReservationDtoRs convertFromEntity(Reservation reservation) {
        return ReservationDtoRs.builder()
                .resNumber(reservation.getResNumber())
                .checkIn(reservation.getCheckIn())
                .checkOut(reservation.getCheckOut())
                .clientDtoRs(clientMapper.convertFromEntity(reservation.getClient()))
                .roomDtoRs(roomMapper.convertFromEntity(reservation.getRoom()))
                .build();
    }

}
