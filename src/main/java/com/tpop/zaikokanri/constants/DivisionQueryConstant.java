package com.tpop.zaikokanri.constants;

public class DivisionQueryConstant {

    public static final String SEARCH_DIVISION_BY_DIVISION_ID =
            "select \n" +
                    "    d.DIVISION_ID divisionId, \n" +
                    "    d.DIVISION_CD divisionCd, \n" +
                    "    d.DIVISION_NAME divisionName, \n" +
                    "    w.WAREHOUSE_CD warehouseCd, \n" +
                    "    w.WAREHOUSE_NAME warehouseName \n" +
                    "from M_部門 as d\n" +
                    "left join M_部門_倉庫 as dw on d.DIVISION_ID = dw.DIVISION_ID\n" +
                    "left join M_倉庫 as w on dw.WAREHOUSE_ID = w.WAREHOUSE_ID\n" +
                    "where d.DIVISION_ID =:divisionId";

    public static final String SEARCH_DIVISION_BY_DIVISION_ID_LIST =
            "select \n" +
                    "    d.DIVISION_ID divisionId, \n" +
                    "    d.DIVISION_CD divisionCd, \n" +
                    "    d.DIVISION_NAME divisionName, \n" +
                    "    w.WAREHOUSE_CD warehouseCd, \n" +
                    "    w.WAREHOUSE_NAME warehouseName \n" +
                    "from M_部門 as d\n" +
                    "left join M_部門_倉庫 as dw on d.DIVISION_ID = dw.DIVISION_ID\n" +
                    "left join M_倉庫 as w on dw.WAREHOUSE_ID = w.WAREHOUSE_ID\n" +
                    "where d.DIVISION_ID IN :divisionIdList";


    public static final String SEARCH_DIVISION =
            "select d.DIVISION_ID , d.DIVISION_CD , d.DIVISION_NAME ,w.WAREHOUSE_CD , w.WAREHOUSE_NAME  \n" +
                    "from M_部門 as d\n" +
                    "left join M_部門_倉庫 as dw on d.DIVISION_ID = dw.DIVISION_ID\n" +
                    "left join M_倉庫 w on dw.WAREHOUSE_ID = dw.WAREHOUSE_ID\n" +
                    "where d.DIVISION_CD like CONCAT('%', :divisionCd, '%') \n" +
                    "and d.DIVISION_NAME like CONCAT('%', :divisionName, '%')";
}
