package com.infox.messagebook.repository;

import com.infox.messagebook.model.XMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public interface XMessageRepository extends JpaRepository<XMessage, Long> {
    XMessage findById(Long id);
    List<XMessage> findAllByCategory(String category, Sort sort);
    Page<XMessage> findByType(int type, Pageable pageable);
//    List<XMessage> findAll() ;
}
