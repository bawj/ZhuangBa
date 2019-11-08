package com.xiaomai.zhuangba.data.bean;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/11/8 0008
 */
public class SearchCondition {

    /**
     * batchCodeList : [{"text":"dawd","list":["04611028473764590"],"num":null}]
     * teamList : [{"text":"yingg","list":["19805851571","15067172560"],"num":null}]
     * equipmentList : [{"text":"300cm*400cm","list":["962"],"num":null}]
     * num : 10
     */

    private int num;
    /** 广告名称 */
    private List<BatchCodeListBean> batchCodeList;
    /** 选择公司 */
    private List<TeamListBean> teamList;
    /** 选择尺寸 */
    private List<EquipmentListBean> equipmentList;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<BatchCodeListBean> getBatchCodeList() {
        return batchCodeList;
    }

    public void setBatchCodeList(List<BatchCodeListBean> batchCodeList) {
        this.batchCodeList = batchCodeList;
    }

    public List<TeamListBean> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<TeamListBean> teamList) {
        this.teamList = teamList;
    }

    public List<EquipmentListBean> getEquipmentList() {
        return equipmentList;
    }

    public void setEquipmentList(List<EquipmentListBean> equipmentList) {
        this.equipmentList = equipmentList;
    }

    public static class BatchCodeListBean {
        /**
         * text : dawd
         * list : ["04611028473764590"]
         * num : null
         */

        private String text;
        private Object num;
        private List<String> list;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Object getNum() {
            return num;
        }

        public void setNum(Object num) {
            this.num = num;
        }

        public List<String> getList() {
            return list;
        }

        public void setList(List<String> list) {
            this.list = list;
        }
    }

    public static class TeamListBean {
        /**
         * text : yingg
         * list : ["19805851571","15067172560"]
         * num : null
         */

        private String text;
        private Object num;
        private List<String> list;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Object getNum() {
            return num;
        }

        public void setNum(Object num) {
            this.num = num;
        }

        public List<String> getList() {
            return list;
        }

        public void setList(List<String> list) {
            this.list = list;
        }
    }

    public static class EquipmentListBean {
        /**
         * text : 300cm*400cm
         * list : ["962"]
         * num : null
         */

        private String text;
        private Object num;
        private List<String> list;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Object getNum() {
            return num;
        }

        public void setNum(Object num) {
            this.num = num;
        }

        public List<String> getList() {
            return list;
        }

        public void setList(List<String> list) {
            this.list = list;
        }
    }
}
