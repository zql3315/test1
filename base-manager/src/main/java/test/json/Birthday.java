package test.json;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

public class Birthday {

    private Date dateBirth;

    private String birthdayTx;

    public String getBirthdayTx() {
        return birthdayTx;
    }

    public void setBirthdayTx(String birthdayTx) {
        this.birthdayTx = birthdayTx;
    }

    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(Date dateBirth) {
        this.dateBirth = dateBirth;
    }

    public Birthday() {
    }
}
