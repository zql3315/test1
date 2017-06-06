package com.infosky.common.util.lottery;

/**
 * 
 * @author n004881
 */
public class Gift {

    /**
     * 序号
     */
    private int index;

    /**
     * 物品ID
     */
    private String gitfId;

    /**
     * 物品名称
     */
    private String giftName;

    /**
     * 抽奖概率
     */
    private double probability;

    public Gift(int index, String gitfId, String giftName, double probability) {
        this.index = index;
        this.gitfId = gitfId;
        this.giftName = giftName;
        this.probability = probability;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getGitfId() {
        return gitfId;
    }

    public void setGitfId(String gitfId) {
        this.gitfId = gitfId;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    @Override
    public String toString() {
        return "Gift [index=" + index + ", gitfId=" + gitfId + ", giftName=" + giftName + ", probability=" + probability + "]";
    }

}
