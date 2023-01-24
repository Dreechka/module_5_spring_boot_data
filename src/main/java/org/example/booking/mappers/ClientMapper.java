package org.example.booking.mappers;

import org.example.booking.dto.ClientDtoRq;
import org.example.booking.entities.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    public Client convertFromDtoRq(ClientDtoRq clientDtoRq) {
        return new Client(clientDtoRq.getName(), clientDtoRq.getEmail());
    }
}
