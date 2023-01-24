package org.example.booking.mappers;

import lombok.AllArgsConstructor;
import org.example.booking.dto.ReservationDtoRq;
import org.example.booking.dto.ReservationDtoRs;
import org.example.booking.entities.Client;
import org.example.booking.entities.Reservation;
import org.example.booking.entities.Room;
import org.example.booking.repo.ClientDao;
import org.example.booking.repo.RoomDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ReservationMapper {
    private final ClientMapper clientMapper;
    private final RoomDao roomDao;
    private final ClientDao clientDao;


    public Reservation convertFromDtoRq(ReservationDtoRq reservationDtoRq) {
        Optional<Client> existingClient = clientDao.findFirstByEmail(reservationDtoRq.getClientDtoRq().getEmail());
        Client client = existingClient.isEmpty() ? clientDao.save(clientMapper.convertFromDtoRq(reservationDtoRq.getClientDtoRq())) :
                existingClient.get();
        Room room = roomDao.findFirstByName(reservationDtoRq.getRoomName()).orElseThrow();
        return new Reservation(
                reservationDtoRq.getCheckIn(),
                reservationDtoRq.getCheckOut(),
                client,
                room
        );
    }

    public ReservationDtoRs convertFromEntity(Reservation reservation) {
        return ReservationDtoRs.builder()
                .resNumber(reservation.getResNumber())
                .checkIn(reservation.getCheckIn())
                .checkOut(reservation.getCheckOut())
                .roomName(reservation.getRoom().getName())
                .clientName(reservation.getClient().getName())
                .build();
    }

}
