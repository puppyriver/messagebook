package com.infox.messagebook.model;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class KData extends XObject {
    private String kname;
    private String kvalue;
    private Date time;
    private String ktype;

    public String getKtype() {
        return ktype;
    }

    public void setKtype(String ktype) {
        this.ktype = ktype;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getKname() {
        return kname;
    }

    public void setKname(String kname) {
        this.kname = kname;
    }

    public String getKvalue() {
        return kvalue;
    }

    public void setKvalue(String kvalue) {
        this.kvalue = kvalue;
    }
}
