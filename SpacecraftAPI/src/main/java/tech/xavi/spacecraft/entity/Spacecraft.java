package tech.xavi.spacecraft.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Spacecraft {

    @Id @GeneratedValue
    private long id;
    private String name;
    private long maxSpeed;
    private int width;
    private int height;
    private int crewSize;
    @Enumerated(EnumType.STRING)
    private Status status;

}
