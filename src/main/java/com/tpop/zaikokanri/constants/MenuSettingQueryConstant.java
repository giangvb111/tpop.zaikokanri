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


    public static final String SEARCH_SCREEN_DETAIL_SETTING = "SELECT\n" +
            "\tdds.DISP_SETTING_ID as displaySettingId,\n" +
            "    dds.ITEM_NAME as itemName ,\n" +
            "    dds.DISP_ORDER as displayOrder , \n" +
            "    dds.REQUIRE_FLG as requireFlag ,\n" +
            "    dds.DISP_FLAG as displayFlag \n" +
            "     \n" +
            "FROM [S_メニュー] m  \n" +
            "JOIN [S_機能] f ON m.FUNCTION_ID = f.FUNCTION_ID \n" +
            "JOIN [S_画面表示設定] sds ON m.FUNCTION_ID = sds.FUNCTION_ID \n" +
            "JOIN [S_画面表示詳細設定] dds ON sds.DISP_SETTING_ID = dds.DISP_SETTING_ID \n" +
            "WHERE f.FUNCTION_CD =:functionCode  AND sds.DISP_SECTION IN (:displaySection)\n" +
            "ORDER BY dds.DISP_SETTING_ID , dds.DISP_ORDER" ;

    public static final String GET_DISPLAY_SECTION_FUNCTION_CODE = "SELECT\n" +
            "\tsds.DISP_SECTION \n" +
            "FROM [S_画面表示設定] sds \n" +
            "JOIN [S_機能] f ON sds.FUNCTION_ID = f.FUNCTION_ID " ;


}
