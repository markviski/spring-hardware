package edu.bbte.idde.vmim1980.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "jpa_hardware")
public class Hardware extends BaseEntity {
    private Float price;
    private String brand;
    private String model;
    private Integer year;
    private Integer memory;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    private Collection<SellerInfo> sellerInfoCollection;
}
