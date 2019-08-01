package com.xiaomai.zhuangba.data;

import android.text.TextUtils;

import com.qmuiteam.qmui.widget.section.QMUISection;

/**
 * @author Administrator
 * @date 2019/7/27 0027
 */
public class SectionHeader implements QMUISection.Model<SectionHeader> {
    private final String text;

    public SectionHeader(String text){
        this.text = text;
    }

    public String getText() {
        return TextUtils.isEmpty(text) ? "" : text;
    }

    @Override
    public SectionHeader cloneForDiff() {
        return new SectionHeader(getText());
    }

    @Override
    public boolean isSameItem(SectionHeader other) {
        return text == other.text || (text != null && text.equals(other.text));
    }

    @Override
    public boolean isSameContent(SectionHeader other) {
        return true;
    }
}
