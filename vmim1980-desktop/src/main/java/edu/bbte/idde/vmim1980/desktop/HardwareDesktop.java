package edu.bbte.idde.vmim1980.desktop;

import edu.bbte.idde.vmim1980.backend.dao.DaoFactory;
import edu.bbte.idde.vmim1980.backend.dao.HardwareDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import edu.bbte.idde.vmim1980.backend.model.Hardware;

public class HardwareDesktop {
    public static final Logger LOGGER = LoggerFactory.getLogger(HardwareDesktop.class);

    public static void main(String[] args) {
        LOGGER.info("Create hardwareDao");
        HardwareDao hardwareDao = DaoFactory.getInstance().getHardwareDao();

        LOGGER.info("Create new entry");
        hardwareDao.create(new Hardware(259.99f, "Crucial", "DRAM", 2021, 4));
        LOGGER.debug(hardwareDao.read(0L).toString());

        LOGGER.info("Update the previously created entry");
        hardwareDao.update(new Hardware(420f, "Corsair", "Valueselect", 2019, 8), 0L);
        LOGGER.info(hardwareDao.read(0L).toString());

        LOGGER.info("Create another new entry");
        Hardware hardware = new Hardware(689f, "Kingston", "ValueRam", 2022, 16);
        LOGGER.debug("Get brand of the new entry.");
        LOGGER.debug(hardware.getBrand());
        hardwareDao.create(hardware);

        LOGGER.info("Read all entries");
        LOGGER.info(hardwareDao.readAll().toString());
        LOGGER.info("Read only the last entry");
        LOGGER.info(hardwareDao.read(1L).toString());

        LOGGER.info("Delete first entry");
        hardwareDao.delete(0L);
        LOGGER.info("Read all entries (after delete)");
        LOGGER.debug("We should only see one single entry");
        LOGGER.info(hardwareDao.readAll().toString());
    }
}