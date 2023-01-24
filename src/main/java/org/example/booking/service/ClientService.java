package org.example.booking.service;

import lombok.AllArgsConstructor;
import org.example.booking.dto.ReservationDtoRs;
import org.example.booking.exceptions.ReservationException;
import org.example.booking.mappers.ReservationMapper;
import org.example.booking.repo.ClientDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ClientService {
    private final ClientDao clientDao;
    private final ReservationMapper reservationMapper;

    public List<ReservationDtoRs> getReservationsByClientEmail(String email) {
        return clientDao.findFirstByEmail(email)
                .orElseThrow(() -> new ReservationException("Бронирования по данному клиенту не найдены"))
                .getReservations().stream()
                .map(reservationMapper::convertFromEntity)
                .collect(Collectors.toList());
    }
}
