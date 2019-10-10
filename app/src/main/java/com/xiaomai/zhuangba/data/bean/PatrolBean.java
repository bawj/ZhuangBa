package com.xiaomai.zhuangba.data.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @date 2019/10/9 0009
 */
public class PatrolBean {

    /**
     * time : 2019-09-27
     * size : 2
     * tasklist : [{"id":3,"count":32,"suCount":0,"province":"北京市","city":"北京市","region":"朝阳区","street":"1","address":"1","detailNo":"231669777817448448","equipmentNo":null,"cover":null,"status":null,"createTime":"2019-09-27 16:23:36"},{"id":4,"count":32,"suCount":0,"province":"北京市","city":"北京市","region":"朝阳区","street":"1","address":"1","detailNo":"231671268242382848","equipmentNo":null,"cover":null,"status":null,"createTime":"2019-09-27 16:29:31"}]
     */

    private String time;
    private int size;
    private List<TasklistBean> tasklist;

    public String getTime() {
        return time == null ? "" : time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<TasklistBean> getTaskList() {
        if (tasklist == null) {
            return new ArrayList<>();
        }
        return tasklist;
    }

    public void setTaskList(List<TasklistBean> taskList) {
        this.tasklist = taskList;
    }

    public static class TasklistBean {
        /**
         * id : 3
         * count : 32
         * suCount : 0
         * province : 北京市
         * city : 北京市
         * region : 朝阳区
         * street : 1
         * address : 1
         * detailNo : 231669777817448448
         * equipmentNo : null
         * cover : null
         * status : null
         * createTime : 2019-09-27 16:23:36
         */

        private int id;
        private int count;
        private int suCount;
        private String province;
        private String city;
        private String region;
        private String street;
        private String address;
        private String detailNo;
        private String equipmentNo;
        private String cover;
        private String status;
        private String createTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getSuCount() {
            return suCount;
        }

        public void setSuCount(int suCount) {
            this.suCount = suCount;
        }

        public String getProvince() {
            return province == null ? "" : province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city == null ? "" : city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getRegion() {
            return region == null ? "" : region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getStreet() {
            return street == null ? "" : street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getAddress() {
            return address == null ? "" : address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getDetailNo() {
            return detailNo == null ? "" : detailNo;
        }

        public void setDetailNo(String detailNo) {
            this.detailNo = detailNo;
        }

        public String getEquipmentNo() {
            return equipmentNo == null ? "" : equipmentNo;
        }

        public void setEquipmentNo(String equipmentNo) {
            this.equipmentNo = equipmentNo;
        }

        public String getCover() {
            return cover == null ? "" : cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getStatus() {
            return status == null ? "" : status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreateTime() {
            return createTime == null ? "" : createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
