package edu.bbte.idde.vmim1980.spring.dto.incoming;

import lombok.Data;

import jakarta.validation.constraints.*;

@Data
public class HardwareCreationDTO {
    @NotNull
    @Positive
    private Float price;

    @NotNull
    @Size(min = 2, max = 64)
    @Pattern(regexp = "[A-Za-z0-9-]+")
    private String brand;

    @NotNull
    @Size(max = 128)
    private String model;

    @NotNull
    @Positive
    private Integer year;

    @NotNull
    @Positive
    @Max(65537)
    private Integer memory;
}
