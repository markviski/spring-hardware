package edu.bbte.idde.vmim1980.backend.dao;

import edu.bbte.idde.vmim1980.backend.model.SellerInfo;

import java.util.Collection;

public interface SellerInfoDao extends Dao<SellerInfo> {
    Collection<SellerInfo> findBySeller(String seller);
}