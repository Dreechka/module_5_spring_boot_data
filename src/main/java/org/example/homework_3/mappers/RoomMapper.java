package org.example.homework_3.mappers;

import org.example.homework_3.dto.RoomDtoRs;
import org.example.homework_3.entities.Room;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper {
    public RoomDtoRs convertFromEntity(Room room) {
        return RoomDtoRs.builder()
                .name(room.getName())
                .type(room.getType())
                .build();
    }

}
