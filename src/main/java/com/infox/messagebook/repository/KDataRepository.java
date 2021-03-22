package com.infox.messagebook.repository;

import com.infox.messagebook.model.KData;
import com.infox.messagebook.model.XMessage;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Author: Ronnie.Chen
 * Date: 2017/3/16
 * Time: 14:50
 * rongrong.chen@alcatel-sbell.com.cn
 */
@Repository
@Transactional
public interface KDataRepository extends JpaRepository<KData, Long> {
    KData findById(Long id);
    KData findByKname(String kname);
//    List<XMessage> findAll() ;
}
