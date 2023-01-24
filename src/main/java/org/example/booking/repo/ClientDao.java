package org.example.booking.repo;

import org.example.booking.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ClientDao extends JpaRepository<Client, Long> {
    Optional<Client> findFirstByEmail(String email);
}
