package org.example.homework_3.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.UUID;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @GeneratedValue
    private String resNumber;

    @Column(nullable = false)
    @NonNull
    private Date checkIn;

    @Column(nullable = false)
    @NonNull
    private Date checkOut;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @OneToOne
    @NonNull
    @JoinColumn(nullable = false)
    private Room room;

    @PrePersist
    private void generateResNumber() {
        this.setResNumber(UUID.randomUUID().toString().substring(0, 8));
    }

}
