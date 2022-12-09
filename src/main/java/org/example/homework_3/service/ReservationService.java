package org.example.homework_3.service;

import lombok.AllArgsConstructor;
import org.example.homework_3.dto.ReservationDtoRq;
import org.example.homework_3.dto.ReservationDtoRs;
import org.example.homework_3.entities.Reservation;
import org.example.homework_3.mappers.ReservationMapper;
import org.example.homework_3.repo.ReservationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ReservationService {
    ReservationDao reservationDao;
    ReservationMapper reservationMapper;

    public String removeByResNumber(String resNumber) {
        Optional<Reservation> reservation = reservationDao.findReservationByResNumber(resNumber);
        if (reservation.isEmpty()) {
            return "Данная бронь не найдена";
        } else {
            reservationDao.delete(reservation.get());
            return "Бронь удалена";
        }
    }

    public List<ReservationDtoRs> getReservationsByResNumber(String resNumber) {
        List<ReservationDtoRs> reservationByResNumber = reservationDao.findReservationByResNumber(resNumber)
                .stream()
                .map(e -> reservationMapper.convertFromEntity(e))
                .collect(Collectors.toList());
        return reservationByResNumber;
    }

    public String reserveRoom(ReservationDtoRq reservationDtoRq) {
        List<Reservation> res = reservationDao.findByCheckInBetweenOrCheckOutBetweenAndRoomName(
                reservationDtoRq.getCheckIn(),
                reservationDtoRq.getCheckOut(),
                reservationDtoRq.getCheckIn(),
                reservationDtoRq.getCheckOut(),
                reservationDtoRq.getRoomDtoRq().getName()
        );
        if (res.size() == 0) {
            Reservation reservation = reservationMapper.convertFromDtoRq(reservationDtoRq);
            return reservationDao.save(reservation).getResNumber();
        } else
            return "Номер забронирован";
    }

    public List<ReservationDtoRs> getReservationsByClientNameAndClientEmail(String name, String email) {
        List<ReservationDtoRs> reservationByClientId = reservationDao.findAllReservationByClientNameAndClientEmail(name, email)
                .stream()
                .map(e -> reservationMapper.convertFromEntity(e))
                .collect(Collectors.toList());
        return reservationByClientId;
    }


}
