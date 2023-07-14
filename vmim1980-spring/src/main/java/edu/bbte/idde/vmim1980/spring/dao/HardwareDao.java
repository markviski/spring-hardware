package edu.bbte.idde.vmim1980.spring.dao;

import edu.bbte.idde.vmim1980.spring.model.Hardware;

import java.util.Collection;

public interface HardwareDao extends Dao<Hardware> {
    Collection<Hardware> findByBrand(String brand);
}