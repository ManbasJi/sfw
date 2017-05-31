package com.kysfw.www.sfw.config;

/**
 * Created by admin on 2017/2/16.
 */

public class Constants {
//    public static String HOST = "http://192.168.31.223/test/";
    public static String HOST="http://192.168.31.59/test/";
    public static String HOST2 = "http://192.168.1.121:8080/Test/";
    public static int ROW_COUNT = 10;

    public static final class SP{
        public static final String USERINFO="userinfo";
    }


    public static final class API{
        public static final String GET_HOUSES_LIST = "getHouseBoxList.php";
        public static final String GET_NEW_HOUSES_LIST = "getNewHouseBoxList.php";
        public static final String GET_USED_HOUSES_LIST = "getUsedHouseBoxList.php";
        public static final String GET_RENTAL_HOUSES_LIST = "getRentalHouseBoxList.php";
        public static final String GET_HOUSE_DETAIL = "getHouseDetail.php";
        public static final String GET_HOUSE_PIC_LIST = "getHousePicList.php";
        public static final String GET_MANAGER_LIST = "getManagerList.php";
        public static final String GET_MANAGER_DETAIL = "getManagerDetail.php";
        public static final String GET_DECORATION_COMPANY_LIST = "getDecorationCompanyList.php";
        public static final String GET_DECORATION_COMPANY_DETAIL = "getDecorationCompanyDetail.php";
        public static final String GET_DECORATION_CASE_LIST = "getDecorationCaseList.php";
        public static final String GET_DECORATION_CASE_DETAIL = "getDecorationCaseDetail.php";
        public static final String GET_MATERIAL_COMPANY_LIST = "getMaterialCompanyList.php";
        public static final String GET_MATERIAL_COMPANY_DETAIL = "getMaterialCompanyDetail.php";
        public static final String GET_MATERIAL_CASE_LIST = "getMaterialList.php";
        public static final String GET_MATERIAL_CASE_DETAIL = "getMaterialDetail.php";
        public static final String GET_CASE_PIC_LIST = "getCasePicList.php";
        public static final String LOGIN_TEST = "login.php";
        public static final String MANAGER_LOGIN = "managerLogin.php";
        public static final String HOUSE_INSERT = "house_insert.php";
        public static final String HOUSE_UPDATE = "house_update.php";
        public static final String HOUSE_DELETE = "house_delete.php";
        public static final String PHOTO_ADD = "photo_add.php";
        public static final String PANG_HENG_PIAU = "pangheng.php";
        public static final String ADVANCED_SEARCH = "advancedSearch.php";
        public static final String PANGNGUANG_TUIXPY = "houseContrast.php";
        public static final String LOGIN_CHECK = "loginCheck.php";
    }
    public static final class API2{
        public static final String FYXXB_LIST = "fileAction.do?method=queryReturnJson";
        public static final String FYXXB_ADD = "fileAction.do?method=add";
        public static final String FYXXB_GET = "fileAction.do?method=queryInIdJson";
        public static final String FYXXB_UPDATE = "fileAction.do?method=update";
        public static final String FYXXB_DELETE = "fileAction.do?method=delete";
    }
    public static final class OPERATION{
        public static final int INIT = 0;
        public static final int REFRESH  = 1;
        public static final int LOAD_MORE = 2;
        public static final int BACK_TO_HOME = 40;
        public static final int REQUEST_CODE_PICK_IMAGE = 50;
        public static final int LOGIN_SUCCESS = 61;
        public static final int LOGIN_TEST = 60;
    }
    public static final class URL{
        public static final String HOUSE_PIC = "housePics/";
        public static final String MANAGER_AVATAR = "avatar/";
        public static final String TEST_PICS = "pics/";
        public static final String DECORATION_COMPANY = "decoration_company/";
        public static final String DECORATION_CASE = "decoration/";
    }
}
