package org.example.booking.service;

import lombok.AllArgsConstructor;
import org.example.booking.dto.ReservationDtoRq;
import org.example.booking.dto.ReservationDtoRs;
import org.example.booking.entities.Reservation;
import org.example.booking.exceptions.ReservationException;
import org.example.booking.mappers.ReservationMapper;
import org.example.booking.repo.ReservationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ReservationService {
    private final ReservationDao reservationDao;
    private final ReservationMapper reservationMapper;

    @Transactional
    public Boolean removeByResNumber(String resNumber) {
        return reservationDao.removeByResNumber(resNumber) > 0;
    }

    public Optional<ReservationDtoRs> getReservationByResNumber(String resNumber) {
        return reservationDao.findFirstByResNumber(resNumber).map(reservationMapper::convertFromEntity);
    }

    public String createReservation(ReservationDtoRq reservationDtoRq) {
        Boolean isReservationExist = reservationDao.existsByCheckInBetweenOrCheckOutBetweenAndRoom_Name(
                reservationDtoRq.getCheckIn(),
                reservationDtoRq.getCheckOut(),
                reservationDtoRq.getCheckIn(),
                reservationDtoRq.getCheckOut(),
                reservationDtoRq.getRoomName()
        );
        if (isReservationExist) {
            throw new ReservationException("Выбранный номер на указанные даты уже забронирован");
        }
        Reservation reservation = reservationDao.save(reservationMapper.convertFromDtoRq(reservationDtoRq));
        return reservation.getResNumber();
    }

}
