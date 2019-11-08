package com.xiaomai.zhuangba.data.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @date 2019/11/8 0008
 */
public class SearchCondition {

    private String type;
    private List<String> teamList;
    private List<String> equipmentList;
    private List<String> batchCodeList;

    public String getType() {
        return type == null ? "" : type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getTeamList() {
        if (teamList == null) {
            return new ArrayList<>();
        }
        return teamList;
    }

    public void setTeamList(List<String> teamList) {
        this.teamList = teamList;
    }

    public List<String> getEquipmentList() {
        if (equipmentList == null) {
            return new ArrayList<>();
        }
        return equipmentList;
    }

    public void setEquipmentList(List<String> equipmentList) {
        this.equipmentList = equipmentList;
    }

    public List<String> getBatchCodeList() {
        if (batchCodeList == null) {
            return new ArrayList<>();
        }
        return batchCodeList;
    }

    public void setBatchCodeList(List<String> batchCodeList) {
        this.batchCodeList = batchCodeList;
    }
}
