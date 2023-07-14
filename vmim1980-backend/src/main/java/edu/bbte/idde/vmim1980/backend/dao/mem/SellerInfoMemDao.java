package edu.bbte.idde.vmim1980.backend.dao.mem;

import edu.bbte.idde.vmim1980.backend.dao.SellerInfoDao;
import edu.bbte.idde.vmim1980.backend.model.SellerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class SellerInfoMemDao implements SellerInfoDao {
    public static final Logger LOGGER = LoggerFactory.getLogger(HardwareMemDao.class);
    private final ConcurrentHashMap<Long, SellerInfo> sellerInfoDatabase;
    private final AtomicLong key;

    public SellerInfoMemDao() {
        LOGGER.debug("creating hardwareMemDao...");
        sellerInfoDatabase = new ConcurrentHashMap<>();
        key = new AtomicLong(0L);
        this.create(new SellerInfo(1L, "eMag", 25, "0751230642", 0f));
        this.create(new SellerInfo(2L, "Flanco", 9, "0764265885", 6.99f));
        LOGGER.info("sellerInfoMemDao created successfully.");
    }

    @Override
    public void create(SellerInfo entity) {
        LOGGER.info("create operation called.");
        entity.setId(key.getAndIncrement());
        sellerInfoDatabase.put(entity.getId(), entity);
        LOGGER.debug("create operation successful.");
    }

    @Override
    public SellerInfo read(Long id) {
        LOGGER.info("read operation called.");
        return sellerInfoDatabase.get(id);
    }

    @Override
    public void update(SellerInfo entity, Long id) {
        LOGGER.info("update operation called.");
        sellerInfoDatabase.computeIfPresent(id, (key, value) -> entity);
        LOGGER.debug("update operation successful.");
    }

    @Override
    public void delete(Long id) {
        LOGGER.info("delete operation called.");
        LOGGER.warn("an item is being deleted!");
        sellerInfoDatabase.remove(id);
        LOGGER.debug("delete operation successful.");
    }

    @Override
    public Collection<SellerInfo> readAll() {
        LOGGER.info("readAll operation called.");
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
