package org.example.homework_3.repo;

import org.example.homework_3.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationDao extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllReservationByClientNameAndClientEmail(String name, String email);

    Optional<Reservation> findReservationByResNumber(String resNumber);

    List<Reservation> findByCheckInBetweenOrCheckOutBetweenAndRoomName(
            Date checkIn, Date checkOut, Date checkIn1, Date checkOut1, String roomName);


}
