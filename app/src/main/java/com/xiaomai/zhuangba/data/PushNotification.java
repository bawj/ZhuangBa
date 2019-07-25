package com.xiaomai.zhuangba.data;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

/**
 * @author Administrator
 * @date 2019/7/5 0005
 */
public class PushNotification {
    @SerializedName("alias_type")
    private String aliasType;
    private PayloadBean payload;
    private String alias;
    /** 描述 */
    private String description;
    private String appkey;
    private String type;
    @SerializedName("production_mode")
    private String productionMode;
    /** 时间 时间戳 */
    private String timestamp;

    public String getAliasType() {
        return TextUtils.isEmpty(aliasType) ? "" : aliasType;
    }

    public void setAliasType(String aliasType) {
        this.aliasType = aliasType;
    }

    public PayloadBean getPayload() {
        return payload;
    }

    public void setPayload(PayloadBean payload) {
        this.payload = payload;
    }

    public String getAlias() {
        return TextUtils.isEmpty(alias) ? "" : alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getDescription() {
        return TextUtils.isEmpty(description) ? "" : description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAppkey() {
        return TextUtils.isEmpty(appkey) ? "" : appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getType() {
        return TextUtils.isEmpty(type) ? "" : type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProductionMode() {
        return TextUtils.isEmpty(productionMode) ? "" : productionMode;
    }

    public void setProductionMode(String productionMode) {
        this.productionMode = productionMode;
    }

    public String getTimestamp() {
        return TextUtils.isEmpty(timestamp) ? "" : timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public static class PayloadBean {

        @SerializedName("display_type")
        private String displayType;
        private ExtraBean extra;
        private BodyBean body;

        public String getDisplayType() {
            return TextUtils.isEmpty(displayType) ? "" : displayType;
        }

        public void setDisplayType(String displayType) {
            this.displayType = displayType;
        }

        public ExtraBean getExtra() {
            return extra;
        }

        public void setExtra(ExtraBean extra) {
            this.extra = extra;
        }

        public BodyBean getBody() {
            return body;
        }

        public void setBody(BodyBean body) {
            this.body = body;
        }

        public static class ExtraBean {

            /**  type = 2   code = 订单编号 */
            private String code;
            /** 页面跳转   1 首页 2订单详情 3提现详情 */
            private String type;
            /** 时间 */
            private String time;
            public String getCode() {
                return TextUtils.isEmpty(code) ? "" : code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getType() {
                return TextUtils.isEmpty(type) ? "" : type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getTime() {
                return TextUtils.isEmpty(time) ? "" : time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }

        public static class BodyBean {
            /**
             * after_open : go_app
             * ticker : [0xe8][0xae][0xa2][0xe5][0x8d][0x95][0xe9][0x80][0x9a][0xe7][0x9f][0xa5]
             * text : 11111111
             * title : 11111
             */

            @SerializedName("after_open")
            private String afterOpen;
            /** 通知标题 */
            private String ticker;
            /** 通知内容 */
            private String text;
            /** 通知标题 */
            private String title;

            public String getAfterOpen() {
                return TextUtils.isEmpty(afterOpen) ? "" : afterOpen;
            }

            public void setAfterOpen(String afterOpen) {
                this.afterOpen = afterOpen;
            }

            public String getTicker() {
                return TextUtils.isEmpty(ticker) ? "" : ticker;
            }

            public void setTicker(String ticker) {
                this.ticker = ticker;
            }

            public String getText() {
                return TextUtils.isEmpty(text) ? "" : text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getTitle() {
                return TextUtils.isEmpty(title) ? "" : title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
