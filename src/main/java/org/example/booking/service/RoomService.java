package org.example.booking.service;

import lombok.AllArgsConstructor;
import org.example.booking.entities.Room;
import org.example.booking.entities.RoomType;
import org.example.booking.repo.RoomDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RoomService {
    private final RoomDao roomDao;

    public void fillDb() {
        Room room1 = new Room("1", RoomType.SUITE);
        Room room2 = new Room("2", RoomType.ECONOMY);
        Room room3 = new Room("3", RoomType.SUITE);
        Room room4 = new Room("4", RoomType.ECONOMY);
        roomDao.save(room1);
        roomDao.save(room2);
        roomDao.save(room3);
        roomDao.save(room4);
    }

/*    public Room findERoomByName(String name) {
        try {
            Optional<Room> roomByName = roomDao.findFirstByName(name);
            return roomByName.get();
        } catch (Exception e) {
            throw new RuntimeException("Заданный номер не существует");
        }
    }*/
}
