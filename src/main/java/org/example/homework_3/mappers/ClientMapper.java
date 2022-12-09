package org.example.homework_3.mappers;

import org.example.homework_3.dto.ClientDtoRq;
import org.example.homework_3.dto.ClientDtoRs;
import org.example.homework_3.entities.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    public ClientDtoRs convertFromEntity(Client client) {
        return ClientDtoRs.builder()
                .name(client.getName())
                .email(client.getEmail())
                .build();
    }

    public Client convertFromDtoRq(ClientDtoRq clientDtoRq) {
        return new Client(clientDtoRq.getName(), clientDtoRq.getEmail());
    }
}
