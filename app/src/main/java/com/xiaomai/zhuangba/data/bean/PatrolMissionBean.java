package com.xiaomai.zhuangba.data.bean;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * @author Administrator
 * @date 2019/10/10 0010
 */
public class PatrolMissionBean extends SectionEntity<PatrolBean.TasklistBean> {

    private boolean isMore;
    public PatrolMissionBean(boolean isHeader, String header, boolean isMroe) {
        super(isHeader, header);
        this.isMore = isMroe;
    }

    public PatrolMissionBean(PatrolBean.TasklistBean t) {
        super(t);
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean mroe) {
        isMore = mroe;
    }
}
