package edu.bbte.idde.vmim1980.spring.controller;

import edu.bbte.idde.vmim1980.spring.controller.exception.NotFoundException;
import edu.bbte.idde.vmim1980.spring.dao.HardwareDao;
import edu.bbte.idde.vmim1980.spring.dto.incoming.HardwareCreationDTO;
import edu.bbte.idde.vmim1980.spring.dto.outgoing.HardwareDetailedDTO;
import edu.bbte.idde.vmim1980.spring.mapper.HardwareMapper;
import edu.bbte.idde.vmim1980.spring.model.Hardware;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/hardwares")
public class HardwareController {
    @Autowired
    HardwareDao hardwareDao;

    @Autowired
    HardwareMapper hardwareMapper;

    @PostMapping
    public HardwareDetailedDTO create(@RequestBody @Valid HardwareCreationDTO hardwareCreationDTO) {
        Hardware hardware = hardwareMapper.dtoToModel(hardwareCreationDTO);
        log.info("spring: Hardware create successful.");
        return hardwareMapper.modelToDto(hardwareDao.saveAndFlush(hardware));
    }

    @GetMapping
    public Collection<HardwareDetailedDTO> readAll(@RequestParam(value = "brand", required = false) String brand) {
        if (brand == null) {
            log.info("spring: Hardware getAll successful.");
            return hardwareMapper.modelsToDtos(hardwareDao.findAll());
        }
        log.info("spring: Hardware find by brand name successful.");
        return hardwareMapper.modelsToDtos(hardwareDao.findByBrand(brand));
    }

    @GetMapping("/{id}")
    public HardwareDetailedDTO readById(@PathVariable("id") Long id) {
        try {
            Hardware hardware = hardwareDao.getById(id);
            if (hardware == null) {
                log.warn("spring: Hardware find by id unsuccessful, there is no entry with the given id.");
                throw new NotFoundException();
            } else {
                log.info("spring: Hardware find by id successful.");
                return hardwareMapper.modelToDto(hardware);
            }
        } catch (NotFoundException e) {
            log.warn("spring: Hardware find by id unsuccessful, there is no entry with the given id.");
            log.warn(e.toString());
            throw new NotFoundException();
        }
    }

    @PutMapping("/{id}")
    public void update(@RequestBody @Valid HardwareCreationDTO hardwareCreationDTO, @PathVariable("id") Long id) {
        try {
            Hardware hardware = hardwareDao.getById(id);
            if (hardware == null) {
                log.warn("spring: Hardware update unsuccessful, there is no entry with the given id.");
                throw new NotFoundException();
            }
            Hardware hardwareNew = hardwareMapper.dtoToModel(hardwareCreationDTO);
            hardwareNew.setId(id);
            hardwareDao.save(hardwareNew);
            log.info("spring: Hardware update successful.");
        } catch (NotFoundException e) {
            log.warn("spring: Hardware update unsuccessful, there is no entry with the given id.");
            log.warn(e.toString());
            throw new NotFoundException();
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        try {
            hardwareDao.getById(id);
            hardwareDao.deleteById(id);
            log.info("spring: Hardware update successful.");
        } catch (NotFoundException e) {
            log.warn("spring: Hardware delete unsuccessful, there is no entry with the given id.");
            log.warn(e.toString());
            throw new NotFoundException();
        }
    }
}
