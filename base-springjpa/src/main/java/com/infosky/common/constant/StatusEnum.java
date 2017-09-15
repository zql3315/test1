package com.infosky.common.constant;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.infosky.common.util.PropertiesConfig;

/**
 * @Description: 状态举类
 * @author 004881
 */
public enum StatusEnum
{
    SUCCESS(200, "成功", "Success"), ERROR(500, "失败", "Error"), ;

    /**枚举值 */
    private int value;

    /**中文名称 */
    private String chDescription;

    /**英文名称 */
    private String enDescription;


    StatusEnum(int value, String chDescription, String enDescription)
    {
        this.value = value;
        this.chDescription = chDescription;
        this.enDescription = enDescription;
    }


    public int getValue()
    {
        return value;
    }


    public String getChDescription()
    {
        return chDescription;
    }


    public String getEnDescription()
    {
        return enDescription;
    }

    /**
     * @Desc : 值与中文名称映射列表，用于界面的下拉选项框
     *      LinkedHashMap: 保证输出和输入的顺序相同
     */
    public static final Map<Integer, String> STATUSMAP = new LinkedHashMap<Integer, String>();
    static
    {
        for (StatusEnum enumObj : StatusEnum.values())
        {
            STATUSMAP.put(enumObj.getValue(), enumObj.getChDescription());
        }
    }

    /**
     * @Desc : 值与英文名称映射列表，用于界面的下拉选项框
     */
    public static final Map<Integer, String> STATUSMAP_EN = new LinkedHashMap<Integer, String>();
    static
    {
        for (StatusEnum enumObj : StatusEnum.values())
        {
            STATUSMAP_EN.put(enumObj.getValue(), enumObj.getEnDescription());
        }
    }

    public static final String DZWL_ZM = PropertiesConfig.readValue("dzwl_zm");
    public static final String WIFI_WA_ACCOUNT = PropertiesConfig.readValue("wifi_wa_account");
    public static final String WIFI_WA_SOURCE_ONOFFLINE = PropertiesConfig.readValue("wifi_wa_source_onoffline");
    public static final String WIFI_WA_SOURCE_NETURL = PropertiesConfig.readValue("wifi_wa_source_neturl");
    public static final String WIFI_WA_SOURCE_ZM = PropertiesConfig.readValue("wifi_wa_source_zm");
    public static final String GTXX_SMZ = PropertiesConfig.readValue("gtxx_smz");
    public static final String MHT_CGLK = PropertiesConfig.readValue("mht_cglk");
    public static final String MHT_JGLK = PropertiesConfig.readValue("mht_jglk");
    public static final String JDC_RHJDC = PropertiesConfig.readValue("jdc_rhjdc");
    public static final String ZKT_ZZRKXX = PropertiesConfig.readValue("zkt_zzrkxx");
    public static final String LYT_GNLK = PropertiesConfig.readValue("lyt_gnlk");
    public static final String CZRK_NEW = PropertiesConfig.readValue("czrk_new");
    public static final String JTT_JSR = PropertiesConfig.readValue("jtt_jsr");
    public static final String JZZ_JZZXX = PropertiesConfig.readValue("jzz_jzzxx");
    public static final String QGZT_CXRY = PropertiesConfig.readValue("qgzt_cxry");
    public static final String QGZT_ZTRY = PropertiesConfig.readValue("qgzt_ztry");
    public static final String RKZZSB_CZWJNRY = PropertiesConfig.readValue("rkzzsb_czwjnry");

    /**
     * 17个类型的key
     */
    public static final Map<String, String> getUrl = new HashMap<String, String>();
    static
    {
        getUrl.put(DZWL_ZM, "dzwl_zm");
        getUrl.put(WIFI_WA_ACCOUNT, "wifi_wa_account");
        getUrl.put(WIFI_WA_SOURCE_ONOFFLINE, "wifi_wa_source_onoffline");
        getUrl.put(WIFI_WA_SOURCE_NETURL, "wifi_wa_source_neturl");
        getUrl.put(WIFI_WA_SOURCE_ZM, "wifi_wa_source_zm");
        getUrl.put(GTXX_SMZ, "gtxx_smz");
        getUrl.put(MHT_CGLK, "mht_cglk");
        getUrl.put(MHT_JGLK, "mht_jglk");
        getUrl.put(JDC_RHJDC, "jdc_rhjdc");
        getUrl.put(ZKT_ZZRKXX, "zkt_zzrkxx");
        getUrl.put(LYT_GNLK, "lyt_gnlk");
        getUrl.put(CZRK_NEW, "czrk_new");
        getUrl.put(JTT_JSR, "jtt_jsr");
        getUrl.put(JZZ_JZZXX, "jzz_jzzxx");
        getUrl.put(QGZT_CXRY, "qgzt_cxry");
        getUrl.put(QGZT_ZTRY, "qgzt_ztry");
        getUrl.put(RKZZSB_CZWJNRY, "rkzzsb_czwjnry");
    }

    /**
     * 17个类型的名称
     */
    public static final Map<String, String> getName = new HashMap<String, String>();
    static
    {
        getName.put(DZWL_ZM, "电子围栏侦码信息");
        getName.put(WIFI_WA_ACCOUNT, "WiFi上网虚拟账号信息");
        getName.put(WIFI_WA_SOURCE_ONOFFLINE, "WiFi上网上下线信息");
        getName.put(WIFI_WA_SOURCE_NETURL, "WiFi上网行为信息");
        getName.put(WIFI_WA_SOURCE_ZM, "WiFi侦码信息");
        getName.put(GTXX_SMZ, "广铁信息");
        getName.put(MHT_CGLK, "实际-出港旅客信息");
        getName.put(MHT_JGLK, "实际-进港旅客信息");
        getName.put(JDC_RHJDC, "机动车基本信息");
        getName.put(ZKT_ZZRKXX, "暂住人口信息");
        getName.put(LYT_GNLK, "国内旅客");
        getName.put(CZRK_NEW, "常住人口");
        getName.put(JTT_JSR, "驾驶人");
        getName.put(JZZ_JZZXX, "流动人口居住证");
        getName.put(QGZT_CXRY, "全国在逃人员撤销人员表");
        getName.put(QGZT_ZTRY, "全国在逃人员信息表");
        getName.put(RKZZSB_CZWJNRY, "出租屋境内人员");
    }
    /**
     * 17个类型对应的jsp
     */
    public static final Map<String, String> getJsp = new HashMap<String, String>();
    static
    {
        getJsp.put(DZWL_ZM, "dzwl");
        getJsp.put(WIFI_WA_ACCOUNT, "account");
        getJsp.put(WIFI_WA_SOURCE_ONOFFLINE, "onoffline");
        getJsp.put(WIFI_WA_SOURCE_NETURL, "neturl");
        getJsp.put(WIFI_WA_SOURCE_ZM, "zm");
        getJsp.put(GTXX_SMZ, "smz");
        getJsp.put(MHT_CGLK, "cglk");
        getJsp.put(MHT_JGLK, "jglk");
        getJsp.put(JDC_RHJDC, "rhjdc");
        getJsp.put(ZKT_ZZRKXX, "zzrkxx");
        getJsp.put(LYT_GNLK, "gnlk");
        getJsp.put(CZRK_NEW, "zzrkxx");
        getJsp.put(JTT_JSR, "jsr");
        getJsp.put(JZZ_JZZXX, "jzzxx");
        getJsp.put(QGZT_CXRY, "cxry");
        getJsp.put(QGZT_ZTRY, "ztry");
        getJsp.put(RKZZSB_CZWJNRY, "czwjnry");
    }
}
