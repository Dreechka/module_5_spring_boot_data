package org.example.booking.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"reservations"})
@EqualsAndHashCode(exclude = {"reservations"})
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NonNull
    private String name;

    @Column(nullable = false)
    @NonNull
    @Enumerated
    private RoomType type;

    @OneToMany(mappedBy = "room")
    private List<Reservation> reservations;

}
