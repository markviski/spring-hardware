package edu.bbte.idde.vmim1980.backend.dao;

import edu.bbte.idde.vmim1980.backend.model.Hardware;

import java.util.Collection;

public interface HardwareDao extends Dao<Hardware> {
    Collection<Hardware> findByBrand(String brand);
}