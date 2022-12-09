package org.example.homework_3.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ClientDtoRs {
    private String name;
    private String email;

}
