package org.example.homework_3.controllers;

import lombok.AllArgsConstructor;
import org.example.homework_3.dto.ReservationDtoRq;
import org.example.homework_3.dto.ReservationDtoRs;
import org.example.homework_3.service.ReservationService;
import org.example.homework_3.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class Controller {
    private RoomService roomService;
    private ReservationService reservationService;

    //0. Заполнить таблицу Room
    @PostMapping(path = "/add_rooms")
    public void fillRooms() {
        roomService.fillDb();
    }

    //1. Может выдать все бронирования покупателя
    @GetMapping(path = "/client_res")
    public ResponseEntity getReservationsByClientNameAndClientEmail(@RequestParam String name, String email) {
        List<ReservationDtoRs> reservationsDtoRs = reservationService.getReservationsByClientNameAndClientEmail(name, email);
        if (reservationsDtoRs.isEmpty()) {
            return new ResponseEntity("Бронирования по данному клиенту не найдены", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(reservationsDtoRs, HttpStatus.OK);
    }

    //2. Может выдать бронирование по номеру брони
    @GetMapping(path = "/reservation/{res_number}")
    public ResponseEntity getReservationsByResNumber(@PathVariable String res_number) {
        List<ReservationDtoRs> reservationsDtoRs = reservationService.getReservationsByResNumber(res_number);
        if (reservationsDtoRs.isEmpty()) {
            return new ResponseEntity("Бронирование не найдено", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(reservationsDtoRs, HttpStatus.OK);
    }

    //3. Забронировать номер на свободную дату - ответ, если получилось: номер брони
    //(в случае, если номер на эту дату забронирован - ответ: "Номер забронирован")
    @PostMapping(path = "/reserve")
    public ResponseEntity reserveRoom(@RequestBody ReservationDtoRq reservationDtoRq) {
        return new ResponseEntity(reservationService.reserveRoom(reservationDtoRq), HttpStatus.OK);
    }

    //4. Удалить бронь по номеру.
    @DeleteMapping(path = "/reservation/{resNumber}")
    public ResponseEntity removeByResNumber(@PathVariable String resNumber) {
        return new ResponseEntity(reservationService.removeByResNumber(resNumber), HttpStatus.OK);

    }

}
