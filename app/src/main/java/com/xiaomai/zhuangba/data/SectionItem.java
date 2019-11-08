package com.xiaomai.zhuangba.data;

import com.qmuiteam.qmui.widget.section.QMUISection;

/**
 * @author Administrator
 * @date 2019/11/8 0008
 */
public class SectionItem implements QMUISection.Model<SectionItem> {
    private final String text;

    public SectionItem(String text){
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public SectionItem cloneForDiff() {
        return new SectionItem(getText());
    }

    @Override
    public boolean isSameItem(SectionItem other) {
        return text == other.text || (text != null && text.equals(other.text));
    }

    @Override
    public boolean isSameContent(SectionItem other) {
        return true;
    }
}
