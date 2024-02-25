package tech.xavi.spacecraft.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @Column(length = 40, nullable = false)
    @Size(min = 2, max = 40, message = "The spacecraft name must be between 2 and 40 characters")
    private String name;
    @Column(nullable = false)
    @Min(value = 1, message = "The spacecraft speed must be greater than zero")
    private long maxSpeed;
    @Column(nullable = false)
    @Min(value = 1, message = "The spacecraft width must be greater than zero")
    private int width;
    @Column(nullable = false)
    @Min(value = 1, message = "The spacecraft height must be greater than zero")
    private int height;
    @Column(nullable = false)
    @Min(value = 1, message = "The spacecraft crew size must be greater than zero")
    private int crewSize;
    @Column(nullable = false)
    @NotNull(message = "The spacecraft status must not be null")
    @Enumerated(EnumType.STRING)
    private Status status;

}
