package com.infox.messagebook.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Author: Ronnie.Chen
 * Date: 2017/3/18
 * Time: 13:03
 * rongrong.chen@alcatel-sbell.com.cn
 */
@MappedSuperclass
public class XObject  implements Serializable, Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "ID_GEN")
    @TableGenerator(name = "ID_GEN", table = "ID_GEN2", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "NETELEMENT_GEN", initialValue = 10000, allocationSize = 10000)
    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
