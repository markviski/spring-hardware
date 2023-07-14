package edu.bbte.idde.vmim1980.spring.dao.jpa;

import edu.bbte.idde.vmim1980.spring.dao.SellerInfoDao;
import edu.bbte.idde.vmim1980.spring.model.SellerInfo;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("jpa")
public interface SellerInfoJpaDao extends SellerInfoDao, JpaRepository<SellerInfo, Long> {
}
