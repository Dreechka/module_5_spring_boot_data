package org.example.booking.repo;

import org.example.booking.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomDao extends JpaRepository<Room, Long> {
    Optional<Room> findFirstByName(String name);
}
