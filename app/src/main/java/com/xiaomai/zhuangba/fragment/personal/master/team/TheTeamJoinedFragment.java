package com.xiaomai.zhuangba.fragment.personal.master.team;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.DensityUtil;
import com.example.toollib.util.RegexUtils;
import com.example.toollib.util.ToastUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.TeamJoinedAdapter;
import com.xiaomai.zhuangba.data.bean.TeamJoinedBean;
import com.xiaomai.zhuangba.data.bean.UserInfo;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.base.BaseListFragment;
import com.xiaomai.zhuangba.fragment.masterworker.MasterWorkerFragment;
import com.xiaomai.zhuangba.fragment.personal.master.assignment.AssignmentTaskFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.weight.dialog.EditTextDialogBuilder;

import java.util.List;

import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/10/15 0015
 * <p>
 * 我创建的团队
 */
public class TheTeamJoinedFragment extends BaseListFragment {

    private List<TeamJoinedBean> teamJoinedBeanLists;
    private TeamJoinedAdapter teamJoinedAdapter;

    public static TheTeamJoinedFragment newInstance() {
        Bundle args = new Bundle();
        TheTeamJoinedFragment fragment = new TheTeamJoinedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public BaseQuickAdapter getBaseListAdapter() {
        teamJoinedAdapter = new TeamJoinedAdapter();
        teamJoinedAdapter.setOnDelListener(new TeamJoinedAdapter.IOnSwipeListener() {
            @Override
            public void onDel(String phone, int pos) {
                delTeamJoined(phone, pos);
            }

            @Override
            public void onItemClick(String phone) {
                //分配任务
                TeamJoinedBean teamJoinedBean = (TeamJoinedBean) view.findViewById(R.id.tvTeamPhone).getTag();
                startFragment(AssignmentTaskFragment.newInstance(teamJoinedBean.getUsernumber()));
            }
        });
        return teamJoinedAdapter;
    }

    private void delTeamJoined(final String phone, final int pos) {
        //删除团队成员
        RxUtils.getObservable(ServiceUrl.getUserApi().deleteMember(phone))
                .compose(this.<HttpResult<Object>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<Object>(getActivity()) {
                    @Override
                    protected void onSuccess(Object response) {
                        if (pos >= 0 && pos < teamJoinedBeanLists.size()) {
                            teamJoinedBeanLists.remove(pos);
                            teamJoinedAdapter.notifyItemRemoved(pos);
                        }
                    }
                });
    }

    @Override
    public void onBaseRefresh(final RefreshLayout refreshLayout) {
        RxUtils.getObservable(ServiceUrl.getUserApi()
                .getTeamPersonnel(String.valueOf(StaticExplain.REGIMENTAL_COMMANDER.getCode())))
                .compose(this.<HttpResult<List<TeamJoinedBean>>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<List<TeamJoinedBean>>() {
                    @Override
                    protected void onSuccess(List<TeamJoinedBean> teamJoinedBeanList) {
                        teamJoinedBeanLists = teamJoinedBeanList;
                        teamJoinedAdapter.setNewData(teamJoinedBeanLists);
                        finishRefresh();
                        loadMoreEnd();
                    }
                });
    }


    @OnClick(R.id.btnInvitedMembers)
    public void onViewClicked() {
        //添加成员
        EditTextDialogBuilder instance = EditTextDialogBuilder.getInstance();
        instance.initView(getActivity());
        EditText editText = instance.geteditDialogContent();
        editText.setGravity(Gravity.CENTER_VERTICAL);
        editText.setInputType(InputType.TYPE_CLASS_PHONE);
        InputFilter[] filters = {new InputFilter.LengthFilter(11)};
        editText.setFilters(filters);
        editText.setTextColor(getResources().getColor(R.color.tool_lib_red_EF2B2B));
        ViewGroup.LayoutParams lp = editText.getLayoutParams();
        lp.height = DensityUtil.dp2px(50);
        editText.setLayoutParams(lp);
        instance.getTip(getString(R.string.please_input_invitation_phone));
        instance.setTitle(getString(R.string.member_account))
                .setContent(getString(R.string.please_input_invitation_phone))
                .setICallBase(new EditTextDialogBuilder.BaseCallback() {
                    @Override
                    public void ok(String phone) {
                        //邀请团员  不能邀请自己
                        UserInfo unique = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();
                        String phoneNumber = unique.getPhoneNumber();
                        if (!phoneNumber.equals(phone) && RegexUtils.isMobileExact(phone)) {
                            saveMember(phone);
                        } else {
                            ToastUtil.showShort(getString(R.string.not_invitation_own));
                        }
                    }
                }).showDialog();
    }

    private void saveMember(String phone) {
        RxUtils.getObservable(ServiceUrl.getUserApi().saveMember(phone))
                .compose(this.<HttpResult<Object>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<Object>(getActivity()) {
                    @Override
                    protected void onSuccess(Object response) {
                        ToastUtil.showShort(getString(R.string.invitation_success));
                    }
                });
    }

    @Override
    public void rightTitleClick(View v) {
        //解散团队
        RxUtils.getObservable(ServiceUrl.getUserApi().dissolutionTeam(String.valueOf(StaticExplain.REGIMENTAL_COMMANDER.getCode())))
                .compose(this.<HttpResult<Object>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<Object>(getActivity()) {
                    @Override
                    public void onNext(HttpResult<Object> httpResult) {
                        super.onNext(httpResult);
                        startFragment(MasterWorkerFragment.newInstance());
                    }

                    @Override
                    protected void onSuccess(Object response) {
                    }
                });

    }

    @Override
    public int getContentView() {
        return R.layout.fragment_the_team;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.my_team);
    }

    @Override
    public int getIvNotDataBackground() {
        return R.drawable.bg_search_empty;
    }

    @Override
    public String getTvNotData() {
        return getString(R.string.search_empty);
    }

    @Override
    public String getRightTitle() {
        return getString(R.string.disbanded_team);
    }

}
