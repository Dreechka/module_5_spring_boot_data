package org.example.homework_3.dto;

import lombok.Builder;
import lombok.Data;
import org.example.homework_3.entities.RoomType;

@Builder
@Data
public class RoomDtoRs {
    private String name;
    private RoomType type;
}
