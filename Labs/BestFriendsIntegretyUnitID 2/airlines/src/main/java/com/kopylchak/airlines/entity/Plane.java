    package com.kopylchak.airlines.entity;

    import com.kopylchak.airlines.entity.enums.FlightClass;
    import com.kopylchak.airlines.entity.enums.PlaneStatus;
    import java.util.Map;
    import javax.persistence.*;
    import lombok.Builder;
    import lombok.Data;
    import lombok.NoArgsConstructor;
    import lombok.experimental.Tolerate;

    @Entity(name = "planes")
    @Builder
    @Data
    public class Plane {
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        private Long id;
        @Column(nullable = false)
        private String model;
        private Coordinates coordinates;
        @Enumerated(EnumType.STRING)
        private PlaneStatus status;
        private Integer capacity;
        @ElementCollection
        @MapKeyEnumerated(EnumType.STRING)
        private Map<FlightClass, Integer> seatsCount;

        @Tolerate
        public Plane() {
            // No args constructor.
        }
    }
