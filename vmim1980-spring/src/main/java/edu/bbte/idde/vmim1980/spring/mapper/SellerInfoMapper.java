package edu.bbte.idde.vmim1980.spring.mapper;

import edu.bbte.idde.vmim1980.spring.dto.incoming.SellerInfoCreationDTO;
import edu.bbte.idde.vmim1980.spring.dto.outgoing.SellerInfoDetailedDTO;
import edu.bbte.idde.vmim1980.spring.model.SellerInfo;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper(componentModel = "spring")
public abstract class SellerInfoMapper {
    public abstract SellerInfo dtoToModel(SellerInfoCreationDTO dto);

    public abstract SellerInfoDetailedDTO modelToDto(SellerInfo sellerInfo);

    @IterableMapping(elementTargetType = SellerInfoDetailedDTO.class)
    public abstract Collection<SellerInfoDetailedDTO> modelsToDtos(Collection<SellerInfo> sellerInfos);
}
