package com.infosky.sys.entity.po;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.infosky.framework.annotation.Comment;
import com.infosky.framework.entity.po.PO;

@Entity
@Table(name = "sys_ipdb")
@Comment("本地 IP地址库")
public class IpAddress extends PO<String> {

    /**
     * 
     */
    private static final long serialVersionUID = -9136903538390621107L;

    @Comment("ip地址")
    public String ip;

    @Comment("国家")
    public String country;

    @Comment("省份")
    public String province;

    @Comment("城市")
    public String city;

    @Comment("地区")
    public String district;

    @Comment("服务提供商：如电信、联通、移动等")
    public String isp;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getIsp() {
        return isp;
    }

    public void setIsp(String isp) {
        this.isp = isp;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
