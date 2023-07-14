package edu.bbte.idde.vmim1980.backend.dao.mem;

import edu.bbte.idde.vmim1980.backend.dao.HardwareDao;
import edu.bbte.idde.vmim1980.backend.model.Hardware;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class HardwareMemDao implements HardwareDao {
    public static final Logger LOGGER = LoggerFactory.getLogger(HardwareMemDao.class);
    private final ConcurrentHashMap<Long, Hardware> hardwareDatabase;
    private final AtomicLong key;

    public HardwareMemDao() {
        LOGGER.debug("creating hardwareMemDao...");
        hardwareDatabase = new ConcurrentHashMap<>();
        key = new AtomicLong(0L);
        this.create(new Hardware(52.99f, "Crucial", "DRAM", 2021, 4));
        this.create(new Hardware(85.55f, "Corsair", "Valueselect", 2019, 8));
        this.create(new Hardware(140f, "Kingston", "ValueRam", 2022, 16));
        LOGGER.info("hardwareMemDao created successfully.");
    }

    @Override
    public void create(Hardware entity) {
        LOGGER.info("create operation called.");
        entity.setId(key.getAndIncrement());
        hardwareDatabase.put(entity.getId(), entity);
        LOGGER.debug("create operation successful.");
    }

    @Override
    public Hardware read(Long id) {
        LOGGER.info("read operation called.");
        return hardwareDatabase.get(id);
    }

    @Override
    public void update(Hardware entity, Long id) {
        LOGGER.info("update operation called.");
        hardwareDatabase.computeIfPresent(id, (key, value) -> entity);
        LOGGER.debug("update operation successful.");
    }

    @Override
    public void delete(Long id) {
        LOGGER.info("delete operation called.");
        LOGGER.warn("an item is being deleted!");
        hardwareDatabase.remove(id);
        LOGGER.debug("delete operation successful.");
    }

    @Override
    public Collection<Hardware> readAll() {
        LOGGER.info("readAll operation called.");
        return hardwareDatabase.values();
    }

    @Override
    public Collection<Hardware> findByBrand(String brand) {
        Collection<Hardware> hardwaresCollection = new ArrayList<>();
        for (Hardware hardware : hardwareDatabase.values()) {
            if (hardware.getBrand().equals(brand)) {
                hardwaresCollection.add(hardware);
            }
        }
        return hardwaresCollection;
    }
}