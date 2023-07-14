package edu.bbte.idde.vmim1980.spring.controller;

import edu.bbte.idde.vmim1980.spring.controller.exception.NotFoundException;
import edu.bbte.idde.vmim1980.spring.dao.HardwareDao;
import edu.bbte.idde.vmim1980.spring.dto.incoming.SellerInfoCreationDTO;
import edu.bbte.idde.vmim1980.spring.dto.outgoing.SellerInfoDetailedDTO;
import edu.bbte.idde.vmim1980.spring.mapper.SellerInfoMapper;
import edu.bbte.idde.vmim1980.spring.model.Hardware;
import edu.bbte.idde.vmim1980.spring.model.SellerInfo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/hardwares/{id}/sellerinfo")
public class SellerInfoController {
    @Autowired
    HardwareDao hardwareDao;

    @Autowired
    private SellerInfoMapper sellerInfoMapper;

    @GetMapping
    public Collection<SellerInfo> findSellerInfoForHardware(@PathVariable("id") Long hardwareid) {
        try {
            Hardware hardware = hardwareDao.getById(hardwareid);
            if (hardware == null) {
                throw new NotFoundException();
            } else {
                return hardware.getSellerInfoCollection();
            }
        } catch (EntityNotFoundException e) {
            log.warn(e.toString());
            throw new NotFoundException();
        }
    }

    @PostMapping
    public SellerInfoDetailedDTO addSellerInfoToHardware(
            @PathVariable("id") Long hardwareid, @RequestBody @Valid SellerInfoCreationDTO sellerInfoCreationDTO) {
        try {
            Hardware hardware = hardwareDao.getById(hardwareid);
            Collection<SellerInfo> sellerInfoCollection = hardware.getSellerInfoCollection();
            SellerInfo sellerInfo = sellerInfoMapper.dtoToModel(sellerInfoCreationDTO);
            sellerInfo.setHardware(hardware);
            sellerInfoCollection.add(sellerInfo);
            hardware.setSellerInfoCollection(sellerInfoCollection);
            hardwareDao.save(hardware);
            sellerInfoCollection = hardware.getSellerInfoCollection();
            SellerInfo lastSellerInfo = new SellerInfo();
            for (SellerInfo i : sellerInfoCollection) {
                lastSellerInfo = i;
            }
            return sellerInfoMapper.modelToDto(lastSellerInfo);
        } catch (EntityNotFoundException e) {
            log.warn(e.toString());
            throw new NotFoundException();
        }
    }

    @DeleteMapping("/{sellerInfoId}")
    public void delete(@PathVariable("id") Long hardwareid, @PathVariable("sellerInfoId") Long sellerInfoId) {
        try {
            Hardware hardware = hardwareDao.getById(hardwareid);
            Collection<SellerInfo> sellerInfoCollection = hardware.getSellerInfoCollection();
            sellerInfoCollection.removeIf(sellerInfo -> sellerInfo.getId().equals(sellerInfoId));
            hardware.setSellerInfoCollection(sellerInfoCollection);
            hardwareDao.save(hardware);
        } catch (EntityNotFoundException e) {
            log.warn(e.toString());
            throw new NotFoundException();
        }
    }
}
