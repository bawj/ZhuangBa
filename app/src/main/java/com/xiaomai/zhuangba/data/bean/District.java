package com.xiaomai.zhuangba.data.bean;

import android.text.TextUtils;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/9/26 0026
 */
public class District implements IPickerViewData {

    private String name;
    private List<CityBean> city;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CityBean> getCity() {
        return city;
    }

    public void setCity(List<CityBean> city) {
        this.city = city;
    }

    @Override
    public String getPickerViewText() {
        return name;
    }

    public static class CityBean {

        private String name;
        private List<AreaBean> area;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<AreaBean> getArea() {
            return area;
        }

        public void setArea(List<AreaBean> area) {
            this.area = area;
        }

        public static class AreaBean {
            /**
             * citycode : 0710
             * adcode : 420682
             * name : 老河口市
             * center : 111.675732,32.385438
             * level : district
             * districts : []
             */

            private String citycode;
            private String adcode;
            private String name;
            private String center;
            private String level;

            public String getCitycode() {
                return citycode;
            }

            public void setCitycode(String citycode) {
                this.citycode = citycode;
            }

            public String getAdcode() {
                return adcode;
            }

            public void setAdcode(String adcode) {
                this.adcode = adcode;
            }

            public String getName() {
                return TextUtils.isEmpty(name) ? "" : name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCenter() {
                return center;
            }

            public void setCenter(String center) {
                this.center = center;
            }

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }
        }
    }
}
