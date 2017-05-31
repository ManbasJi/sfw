package com.kysfw.www.sfw.model;

import java.util.List;

/**
 * Created by admin on 2017/5/22.
 */

public class AreaModel {
    public List<CityList> citylist;

    public class CityList{
        public String p; //省名称
        public boolean isSelect;
        public List<City> c;
    }

    public class City{
        public String n;//城市名称
        public boolean isSelect;
    }
}
