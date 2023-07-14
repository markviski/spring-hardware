package edu.bbte.idde.vmim1980.backend.model;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Hardware extends BaseEntity {
    private Float price;
    private String brand;
    private String model;
    private Integer year;
    private Integer memory;
}
