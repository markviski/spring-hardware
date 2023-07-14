package edu.bbte.idde.vmim1980.spring.dto.outgoing;

import lombok.Data;

@Data
public class SellerInfoDetailedDTO {
    private Long id;
    private String seller;
    private Integer quantity;
    private String phonenumber;
    private Float shippingfee;
}
