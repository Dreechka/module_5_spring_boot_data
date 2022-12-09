package org.example.homework_3.service;

import lombok.AllArgsConstructor;
import org.example.homework_3.dto.ClientDtoRq;
import org.example.homework_3.entities.Client;
import org.example.homework_3.mappers.ClientMapper;
import org.example.homework_3.repo.ClientDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ClientService {
    private ClientDao clientDao;
    private ClientMapper clientMapper;

    public Client findExistsOrAddNewClient(ClientDtoRq clientDtoRq) {
        Optional<Client> clientByEmail = clientDao.findClientByEmail(clientDtoRq.getEmail());
        if (clientByEmail.isEmpty()) {
            return clientDao.save(clientMapper.convertFromDtoRq(clientDtoRq));
        } else
            return clientByEmail.get();
    }

}
