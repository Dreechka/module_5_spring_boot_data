package org.example.booking.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientDtoRq {
    private String name;
    private String email;
}
