package org.example.homework_3.repo;

import org.example.homework_3.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomDao extends JpaRepository<Room, Long> {

    Optional<Room> findByName(String name);
}
