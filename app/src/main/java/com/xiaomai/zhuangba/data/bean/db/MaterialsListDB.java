package com.xiaomai.zhuangba.data.bean.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author Administrator
 * @date 2019/8/20 0020
 * 辅材
 */
@Entity
public class MaterialsListDB {

    /**
     * slottingId : 1
     * startLength : 1
     * endLength : 2
     * slottingPrice : 3.0
     */

    @Id(autoincrement = true)
    private Long id;
    private int slottingId;
    private String startLength;
    private String endLength;
    private double slottingPrice;
    /**
     * 是否选中
     */
    private boolean isCheck;

    @Generated(hash = 2033400270)
    public MaterialsListDB(Long id, int slottingId, String startLength,
                           String endLength, double slottingPrice, boolean isCheck) {
        this.id = id;
        this.slottingId = slottingId;
        this.startLength = startLength;
        this.endLength = endLength;
        this.slottingPrice = slottingPrice;
        this.isCheck = isCheck;
    }

    @Generated(hash = 1088776688)
    public MaterialsListDB() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSlottingId() {
        return this.slottingId;
    }

    public void setSlottingId(int slottingId) {
        this.slottingId = slottingId;
    }

    public String getStartLength() {
        return this.startLength;
    }

    public void setStartLength(String startLength) {
        this.startLength = startLength;
    }

    public String getEndLength() {
        return this.endLength;
    }

    public void setEndLength(String endLength) {
        this.endLength = endLength;
    }

    public double getSlottingPrice() {
        return this.slottingPrice;
    }

    public void setSlottingPrice(double slottingPrice) {
        this.slottingPrice = slottingPrice;
    }

    public boolean getIsCheck() {
        return this.isCheck;
    }

    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }


}
