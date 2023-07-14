package edu.bbte.idde.vmim1980.spring.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "jpa_sellerinfo")
public class SellerInfo extends BaseEntity {
    private Long hardwareid;
    private String seller;
    private Integer quantity;
    private String phonenumber;
    private Float shippingfee;
    @Transient
    private Hardware hardware;
}
