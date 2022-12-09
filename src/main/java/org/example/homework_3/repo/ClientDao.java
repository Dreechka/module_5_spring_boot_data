package org.example.homework_3.repo;

import org.example.homework_3.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ClientDao extends JpaRepository<Client, Long> {
    Optional<Client> findClientByEmail(String email);
}
