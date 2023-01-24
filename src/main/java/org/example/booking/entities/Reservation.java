package org.example.booking.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"room", "client"})
@EqualsAndHashCode(exclude = {"room", "client"})
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @GeneratedValue
    private String resNumber;

    @Column(nullable = false)
    @NonNull
    private LocalDate checkIn;

    @Column(nullable = false)
    @NonNull
    private LocalDate checkOut;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @PrePersist
    private void generateResNumber() {
        this.setResNumber(UUID.randomUUID().toString().substring(0, 8));
    }

}
