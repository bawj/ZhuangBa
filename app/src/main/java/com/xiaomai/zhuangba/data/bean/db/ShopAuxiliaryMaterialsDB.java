package com.xiaomai.zhuangba.data.bean.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author Administrator
 * @date 2019/8/20 0020
 */
@Entity
public class ShopAuxiliaryMaterialsDB {

    @Id
    private long id;

    /**
     * 开槽
     */
    private Long slottingId;
    private int slottingSlottingId;
    private String slottingStartLength;
    private String slottingEndLength;
    private double slottingSlottingPrice;
    /**
     * 辅材
     */
    private Long materialsId;
    private int materialsSlottingId;
    private String materialsStartLength;
    private String materialsEndLength;
    private double materialsSlottingPrice;

    /**
     * 是否需要调试 价格
     */
    private int debuggingId;
    private double debuggingPrice;

    @Generated(hash = 1396279356)
    public ShopAuxiliaryMaterialsDB(long id, Long slottingId, int slottingSlottingId, String slottingStartLength,
            String slottingEndLength, double slottingSlottingPrice, Long materialsId, int materialsSlottingId,
            String materialsStartLength, String materialsEndLength, double materialsSlottingPrice, int debuggingId,
            double debuggingPrice) {
        this.id = id;
        this.slottingId = slottingId;
        this.slottingSlottingId = slottingSlottingId;
        this.slottingStartLength = slottingStartLength;
        this.slottingEndLength = slottingEndLength;
        this.slottingSlottingPrice = slottingSlottingPrice;
        this.materialsId = materialsId;
        this.materialsSlottingId = materialsSlottingId;
        this.materialsStartLength = materialsStartLength;
        this.materialsEndLength = materialsEndLength;
        this.materialsSlottingPrice = materialsSlottingPrice;
        this.debuggingId = debuggingId;
        this.debuggingPrice = debuggingPrice;
    }

    @Generated(hash = 987243232)
    public ShopAuxiliaryMaterialsDB() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getSlottingId() {
        return this.slottingId;
    }

    public void setSlottingId(Long slottingId) {
        this.slottingId = slottingId;
    }

    public int getSlottingSlottingId() {
        return this.slottingSlottingId;
    }

    public void setSlottingSlottingId(int slottingSlottingId) {
        this.slottingSlottingId = slottingSlottingId;
    }

    public String getSlottingStartLength() {
        return this.slottingStartLength;
    }

    public void setSlottingStartLength(String slottingStartLength) {
        this.slottingStartLength = slottingStartLength;
    }

    public String getSlottingEndLength() {
        return this.slottingEndLength;
    }

    public void setSlottingEndLength(String slottingEndLength) {
        this.slottingEndLength = slottingEndLength;
    }

    public double getSlottingSlottingPrice() {
        return this.slottingSlottingPrice;
    }

    public void setSlottingSlottingPrice(double slottingSlottingPrice) {
        this.slottingSlottingPrice = slottingSlottingPrice;
    }

    public Long getMaterialsId() {
        return this.materialsId;
    }

    public void setMaterialsId(Long materialsId) {
        this.materialsId = materialsId;
    }

    public int getMaterialsSlottingId() {
        return this.materialsSlottingId;
    }

    public void setMaterialsSlottingId(int materialsSlottingId) {
        this.materialsSlottingId = materialsSlottingId;
    }

    public String getMaterialsStartLength() {
        return this.materialsStartLength;
    }

    public void setMaterialsStartLength(String materialsStartLength) {
        this.materialsStartLength = materialsStartLength;
    }

    public String getMaterialsEndLength() {
        return this.materialsEndLength;
    }

    public void setMaterialsEndLength(String materialsEndLength) {
        this.materialsEndLength = materialsEndLength;
    }

    public double getMaterialsSlottingPrice() {
        return this.materialsSlottingPrice;
    }

    public void setMaterialsSlottingPrice(double materialsSlottingPrice) {
        this.materialsSlottingPrice = materialsSlottingPrice;
    }

    public double getDebuggingPrice() {
        return this.debuggingPrice;
    }

    public void setDebuggingPrice(double debuggingPrice) {
        this.debuggingPrice = debuggingPrice;
    }

    public int getDebuggingId() {
        return debuggingId;
    }

    public void setDebuggingId(int debuggingId) {
        this.debuggingId = debuggingId;
    }
}
