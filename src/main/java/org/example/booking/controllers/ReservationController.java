package org.example.booking.controllers;

import lombok.AllArgsConstructor;
import org.example.booking.dto.ReservationDtoRq;
import org.example.booking.dto.ReservationDtoRs;
import org.example.booking.exceptions.ReservationException;
import org.example.booking.service.ClientService;
import org.example.booking.service.ReservationService;
import org.example.booking.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ReservationController {
    private RoomService roomService;
    private ReservationService reservationService;
    private ClientService clientService;

    //0. Заполнить таблицу Room
    @PostMapping(path = "/add_rooms")
    public void fillRooms() {
        roomService.fillDb();
    }

    //1. Может выдать все бронирования покупателя
    //2. Может выдать бронирование по номеру брони
    @GetMapping(path = "/reservation")
    public ResponseEntity getReservationsByClientEmail(@RequestParam(required = false) String email,
                                                       @RequestParam(required = false) String resNumber) {
        if (email != null) {
            return getReservationsByClientEmail(email);
        } else
            return getReservationByResNumber(resNumber);
    }

    //3. Забронировать номер на свободную дату - ответ, если получилось: номер брони
    //(в случае, если номер на эту дату забронирован - ответ: "Номер забронирован")
    @PostMapping(path = "/reservation")
    public ResponseEntity createReservation(@RequestBody ReservationDtoRq reservationDtoRq) {
        return new ResponseEntity(reservationService.createReservation(reservationDtoRq), HttpStatus.OK);
    }

    //4. Удалить бронь по номеру.
    @DeleteMapping(path = "/reservation/{resNumber}")
    public ResponseEntity removeReservation(@PathVariable String resNumber) {
        Boolean result = reservationService.removeByResNumber(resNumber);
        if (!result) {
            throw new ReservationException("Данное бронирование не найдено");
        }
        return new ResponseEntity("Бронь успешно удалена", HttpStatus.OK);
    }

    private ResponseEntity getReservationsByClientEmail(String email) {
        return new ResponseEntity(clientService.getReservationsByClientEmail(email), HttpStatus.OK);
    }

    private ResponseEntity getReservationByResNumber(String resNumber) {
        Optional<ReservationDtoRs> reservationByResNumber = reservationService.getReservationByResNumber(resNumber);
        ReservationDtoRs reservationDtoRs = reservationByResNumber.orElseThrow(
                () -> new ReservationException("Бронирование не найдено"));
        return new ResponseEntity(reservationDtoRs, HttpStatus.OK);
    }

}
