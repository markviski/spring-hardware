package edu.bbte.idde.vmim1980.spring.dao.mem;

import edu.bbte.idde.vmim1980.spring.dao.HardwareDao;
import edu.bbte.idde.vmim1980.spring.model.Hardware;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Repository
@Profile("mem")
public class HardwareMemDao implements HardwareDao {
    private final ConcurrentHashMap<Long, Hardware> hardwareDatabase;
    private final AtomicLong key;

    public HardwareMemDao() {
        log.debug("creating hardwareMemDao...");
        hardwareDatabase = new ConcurrentHashMap<>();
        key = new AtomicLong(0L);
        this.saveAndFlush(new Hardware(52.99f, "Crucial", "DRAM", 2021, 4, new ArrayList<>()));
        this.saveAndFlush(new Hardware(85.55f, "Corsair", "Valueselect", 2019, 8, new ArrayList<>()));
        this.saveAndFlush(new Hardware(140f, "Kingston", "ValueRam", 2022, 16, new ArrayList<>()));
        log.info("hardwareMemDao created successfully.");
    }

    @Override
    public Hardware saveAndFlush(Hardware entity) {
        log.info("create operation called.");
        entity.setId(key.getAndIncrement());
        hardwareDatabase.put(entity.getId(), entity);
        log.debug("create operation successful.");
        return hardwareDatabase.get(entity.getId());
    }

    @Override
    public Hardware getById(Long id) {
        log.info("read operation called.");
        return hardwareDatabase.get(id);
    }

    @Override
    public Hardware save(Hardware entity) {
        log.info("update operation called.");
        hardwareDatabase.computeIfPresent(entity.getId(), (key, value) -> entity);
        log.debug("update operation successful.");
        return entity;
    }

    @Override
    public void deleteById(Long id) {
        log.info("delete operation called.");
        log.warn("an item is being deleted!");
        hardwareDatabase.remove(id);
        log.debug("delete operation successful.");
    }

    @Override
    public Collection<Hardware> findAll() {
        log.info("readAll operation called.");
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
