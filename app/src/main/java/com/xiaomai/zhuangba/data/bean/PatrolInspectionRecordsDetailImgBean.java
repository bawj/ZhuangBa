package com.xiaomai.zhuangba.data.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @date 2019/10/11 0011
 */
public class PatrolInspectionRecordsDetailImgBean {

    /**
     * id : 65
     * orderDetailNo : 231671268242382848
     * status : processed
     * taskPictureList : [{"id":34,"taskid":null,"pic":"D:\\work_zs\\img\\2019-10-11\\1c8622da5a074b9da6d6bba00b2a4022.jpg","side":"C","remarks":"本面是新增","createtime":null,"isdelete":null}]
     */

    private int id;
    private String orderDetailNo;
    private String status;
    private String cover;
    private List<TaskPictureListBean> taskPictureList;
    private List<TaskPictureListBean> taskPictureVOList;

    public String getCover() {
        return cover == null ? "" : cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderDetailNo() {
        return orderDetailNo;
    }

    public void setOrderDetailNo(String orderDetailNo) {
        this.orderDetailNo = orderDetailNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<TaskPictureListBean> getTaskPictureList() {
        if (taskPictureList == null) {
            return new ArrayList<>();
        }
        return taskPictureList;
    }

    public void setTaskPictureList(List<TaskPictureListBean> taskPictureList) {
        this.taskPictureList = taskPictureList;
    }

    public List<TaskPictureListBean> getTaskPictureVOList() {
        if (taskPictureVOList == null) {
            return new ArrayList<>();
        }
        return taskPictureVOList;
    }

    public void setTaskPictureVOList(List<TaskPictureListBean> taskPictureVOList) {
        this.taskPictureVOList = taskPictureVOList;
    }

    public static class TaskPictureListBean implements Parcelable {
        /**
         * id : 34
         * taskid : null
         * pic : D:\work_zs\img\2019-10-11\1c8622da5a074b9da6d6bba00b2a4022.jpg
         * side : C
         * remarks : 本面是新增
         * createtime : null
         * isdelete : null
         */

        private int id;
        private String taskid;
        private String pic;
        private String side;
        private String remarks;
        private String createtime;
        private String isdelete;

        public TaskPictureListBean() {
        }

        private TaskPictureListBean(Parcel in) {
            id = in.readInt();
            pic = in.readString();
            side = in.readString();
            remarks = in.readString();
        }

        public static final Creator<TaskPictureListBean> CREATOR = new Creator<TaskPictureListBean>() {
            @Override
            public TaskPictureListBean createFromParcel(Parcel in) {
                return new TaskPictureListBean(in);
            }

            @Override
            public TaskPictureListBean[] newArray(int size) {
                return new TaskPictureListBean[size];
            }
        };

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTaskid() {
            return taskid == null ? "" : taskid;
        }

        public void setTaskid(String taskid) {
            this.taskid = taskid;
        }

        public String getPic() {
            return pic == null ? "" : pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getSide() {
            return side == null ? "" : side;
        }

        public void setSide(String side) {
            this.side = side;
        }

        public String getRemarks() {
            return remarks == null ? "" : remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getCreatetime() {
            return createtime == null ? "" : createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getIsdelete() {
            return isdelete == null ? "" : isdelete;
        }

        public void setIsdelete(String isdelete) {
            this.isdelete = isdelete;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeString(pic);
            dest.writeString(side);
            dest.writeString(remarks);
        }
    }
}
