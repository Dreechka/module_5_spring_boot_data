package org.example.booking.repo;

import org.example.booking.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ReservationDao extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findFirstByResNumber(String resNumber);

    /*@Query(value = "SELECT * FROM reservation r INNER JOIN room ON r.room_id = room.id\n" +
            "WHERE room.name = :room_name\n" +
            "AND r.check_in BETWEEN :check_in AND :check_out\n" +
            "OR r.check_out BETWEEN :check_in AND :check_out")
    List<Reservation> findByCheckInCheckOutAndRoomName(
            @Param("room_name") String roomName, @Param("check_in") LocalDate checkIn, @Param("check_out") LocalDate checkOut);
*/
    Boolean existsByCheckInBetweenOrCheckOutBetweenAndRoom_Name(
            LocalDate checkIn, LocalDate checkOut, LocalDate checkIn1, LocalDate checkOut1, String roomName);


    Long removeByResNumber(String resNumber);
}
