package com.infosky.sys.entity.dto;

import com.infosky.framework.entity.dto.DTO;

public class IpAddressDTO extends DTO<String> {

    /**
     * 
     */
    private static final long serialVersionUID = -9136903538390621107L;

    public String ip;

    public String country;

    public String province;

    public String city;

    public String district;

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
