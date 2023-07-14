package edu.bbte.idde.vmim1980.backend.model;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class SellerInfo extends BaseEntity {
    private Long hardwareid;
    private String seller;
    private Integer quantity;
    private String phonenumber;
    private Float shippingfee;
}
