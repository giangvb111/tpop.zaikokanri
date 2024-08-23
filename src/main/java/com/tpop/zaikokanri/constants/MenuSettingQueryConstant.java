package com.tpop.zaikokanri.constants;

public class MenuSettingQueryConstant {

    public static final String SEARCH_SCREEN_DISPLAY_SETTING = "SELECT \n" +
            "\t\tsm.MENU_ID as menuId, \n" +
            "\t\tsm.FUNCTION_ID as functionId, \n" +
            "\t\tsm.DISPLAY_NAME as displayName, \n" +
            "\t\tsm.PARENT_ID as parentId, \n" +
            "\t\tsm.DISPLAY_ORDER as displayOrder, \n" +
            "\t\tsf.FUNCTION_SECTION as functionSection, \n" +
            "\t\tsf.FUNCTION_NAME as functionName, \n" +
            "\t\tsf.FUNCTION_CD as functionCode, \n" +
            "\t\tsf.URL as url \n" +
            "FROM [S_メニュー] sm \n" +
            "LEFT JOIN [S_機能] sf ON sm.FUNCTION_ID = sf.FUNCTION_ID \n" +
            "WHERE sm.PARENT_ID IS NULL ORDER BY sm.DISPLAY_ORDER\n" ;

    public static final String SEARCH_CHILDREN_SCREEN_DISPLAY_SETTING = "SELECT \n" +
            "\t\tsm.MENU_ID as menuId, \n" +
            "\t\tsm.FUNCTION_ID as functionId, \n" +
            "\t\tsm.DISPLAY_NAME as displayName, \n" +
            "\t\tsm.PARENT_ID as parentId, \n" +
            "\t\tsm.DISPLAY_ORDER as displayOrder,\n" +
            "\t\tsf.FUNCTION_SECTION as functionSection, \n" +
            "\t\tsf.FUNCTION_NAME as functionName, \n" +
            "\t\tsf.FUNCTION_CD as functionCode, \n" +
            "\t\tsf.URL as url\n" +
            "FROM [S_メニュー] sm \n" +
            "LEFT JOIN [S_機能] sf ON sm.FUNCTION_ID = sf.FUNCTION_ID \n" +
            "WHERE sm.PARENT_ID IN (:parentIdList) ORDER BY sm.DISPLAY_ORDER" ;


}
