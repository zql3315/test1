package com.infosky.wechat.service.message.req;

/**
 * 地理位置消息内容
 * @author 004881
 *
 */
public class LocationMessage extends ReqBaseMessage {

    // 地理位置维度
    private String Location_X;

    // 地理位置经度
    private String Location_Y;

    // 地图缩放大小
    private String Scale;

    // 地理位置信息
    private String Label;

    public String getLocation_X() {
        return Location_X;
    }

    public void setLocation_X(String location_X) {
        this.Location_X = location_X;
    }

    public String getLocation_Y() {
        return Location_Y;
    }

    public void setLocation_Y(String location_Y) {
        this.Location_Y = location_Y;
    }

    public String getScale() {
        return Scale;
    }

    public void setScale(String scale) {
        this.Scale = scale;
    }

    public String getLabel() {
        return Label;
    }

    public void setLabel(String label) {
        this.Label = label;
    }

}
