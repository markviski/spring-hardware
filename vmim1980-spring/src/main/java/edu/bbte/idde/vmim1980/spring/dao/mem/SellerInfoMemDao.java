package edu.bbte.idde.vmim1980.spring.dao.mem;

import edu.bbte.idde.vmim1980.spring.dao.SellerInfoDao;
import edu.bbte.idde.vmim1980.spring.model.Hardware;
import edu.bbte.idde.vmim1980.spring.model.SellerInfo;
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
public class SellerInfoMemDao implements SellerInfoDao {
    private final ConcurrentHashMap<Long, SellerInfo> sellerInfoDatabase;
    private final AtomicLong key;

    public SellerInfoMemDao() {
        log.debug("creating hardwareMemDao...");
        sellerInfoDatabase = new ConcurrentHashMap<>();
        key = new AtomicLong(0L);
        this.saveAndFlush(new SellerInfo(1L, "eMag", 25, "0751230642", 0f,
                new Hardware(52.99f, "Crucial", "DRAM", 2021, 4, new ArrayList<>())));
        this.saveAndFlush(new SellerInfo(2L, "Flanco", 9, "0764265885", 6.99f,
                new Hardware(85.55f, "Corsair", "Valueselect", 2019, 8, new ArrayList<>())));
        log.info("sellerInfoMemDao created successfully.");
    }

    @Override
    public SellerInfo saveAndFlush(SellerInfo entity) {
        log.info("create operation called.");
        entity.setId(key.getAndIncrement());
        sellerInfoDatabase.put(entity.getId(), entity);
        log.debug("create operation successful.");
        return sellerInfoDatabase.get(entity.getId());
    }

    @Override
    public SellerInfo getById(Long id) {
        log.info("read operation called.");
        return sellerInfoDatabase.get(id);
    }

    @Override
    public SellerInfo save(SellerInfo entity) {
        log.info("update operation called.");
        sellerInfoDatabase.computeIfPresent(entity.getId(), (key, value) -> entity);
        log.debug("update operation successful.");
        return entity;
    }

    @Override
    public void deleteById(Long id) {
        log.info("delete operation called.");
        log.warn("an item is being deleted!");
        sellerInfoDatabase.remove(id);
        log.debug("delete operation successful.");
    }

    @Override
    public Collection<SellerInfo> findAll() {
        log.info("readAll operation called.");
        return sellerInfoDatabase.values();
    }

    @Override
    public Collection<SellerInfo> findBySeller(String seller) {
        Collection<SellerInfo> sellerInfoCollection = new ArrayList<>();
        for (SellerInfo sellerInfo : sellerInfoDatabase.values()) {
            if (sellerInfo.getSeller().equals(seller)) {
                sellerInfoCollection.add(sellerInfo);
            }
        }
        return sellerInfoCollection;
    }
}
