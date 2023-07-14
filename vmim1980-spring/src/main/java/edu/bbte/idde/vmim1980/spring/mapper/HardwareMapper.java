package edu.bbte.idde.vmim1980.spring.mapper;

import edu.bbte.idde.vmim1980.spring.dto.incoming.HardwareCreationDTO;
import edu.bbte.idde.vmim1980.spring.dto.outgoing.HardwareDetailedDTO;
import edu.bbte.idde.vmim1980.spring.model.Hardware;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper(componentModel = "spring")
public abstract class HardwareMapper {
    public abstract Hardware dtoToModel(HardwareCreationDTO dto);

    public abstract HardwareDetailedDTO modelToDto(Hardware hardware);

    @IterableMapping(elementTargetType = HardwareDetailedDTO.class)
    public abstract Collection<HardwareDetailedDTO> modelsToDtos(Collection<Hardware> hardwares);

}
