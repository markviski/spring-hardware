package edu.bbte.idde.vmim1980.spring.dto.incoming;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SellerInfoCreationDTO {
    @NotNull
    private String seller;
    @NotNull
    private Integer quantity;
    @NotNull
    private String phonenumber;
    @NotNull
    private Float shippingfee;
}
