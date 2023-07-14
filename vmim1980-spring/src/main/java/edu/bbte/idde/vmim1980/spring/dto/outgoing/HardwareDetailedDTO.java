package edu.bbte.idde.vmim1980.spring.dto.outgoing;

import lombok.Data;

@Data
public class HardwareDetailedDTO {
    private Long id;
    private Float price;
    private String brand;
    private String model;
    private Integer year;
    private Integer memory;
}
