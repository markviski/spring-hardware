package edu.bbte.idde.vmim1980.spring.dao.jpa;

import edu.bbte.idde.vmim1980.spring.dao.HardwareDao;
import edu.bbte.idde.vmim1980.spring.model.Hardware;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("jpa")
public interface HardwareJpaDao extends HardwareDao, JpaRepository<Hardware, Long> {
}
