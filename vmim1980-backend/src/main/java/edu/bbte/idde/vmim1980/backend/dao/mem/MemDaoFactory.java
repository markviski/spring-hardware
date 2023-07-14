package edu.bbte.idde.vmim1980.backend.dao.mem;

import edu.bbte.idde.vmim1980.backend.dao.DaoFactory;
import edu.bbte.idde.vmim1980.backend.dao.HardwareDao;
import edu.bbte.idde.vmim1980.backend.dao.SellerInfoDao;

public class MemDaoFactory extends DaoFactory {
    private HardwareMemDao hardwareMemDao;
    private SellerInfoMemDao sellerInfoMemDao;

    @Override
    public HardwareDao getHardwareDao() {
        if (hardwareMemDao == null) {
            hardwareMemDao = new HardwareMemDao();
        }
        return hardwareMemDao;
    }

    @Override
    public SellerInfoDao getSellerInfoDao() {
        if (sellerInfoMemDao == null) {
            sellerInfoMemDao = new SellerInfoMemDao();
        }
        return sellerInfoMemDao;
    }
}
