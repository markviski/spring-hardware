package edu.bbte.idde.vmim1980.spring.dao;

import edu.bbte.idde.vmim1980.spring.model.SellerInfo;

import java.util.Collection;

public interface SellerInfoDao extends Dao<SellerInfo> {
    Collection<SellerInfo> findBySeller(String seller);
}