package com.tpop.zaikokanri.constants;

public class DivisionQueryConstant {

    public static final String SEARCH_DIVISION_BY_DIVISION_ID =
            "select \n" +
                    "    d.DIVISION_ID id, \n" +
                    "    d.DIVISION_CD divisionCd, \n" +
                    "    d.DIVISION_NAME divisionName, \n" +
                    "    w.WAREHOUSE_ID as warehouseId, \n" +
                    "    w.WAREHOUSE_CD warehouseCd, \n" +
                    "    w.WAREHOUSE_NAME warehouseName,\n" +
                    "    dw.DIVI_WAREHOUSE_ID warehouseDivisionId " +
                    "from M_部門 as d\n" +
                    "left join M_部門_倉庫 as dw on d.DIVISION_ID = dw.DIVISION_ID\n" +
                    "left join M_倉庫 as w on dw.WAREHOUSE_ID = w.WAREHOUSE_ID\n" +
                    "where d.DIVISION_ID =:divisionId";

    public static final String SEARCH_DIVISION_BY_DIVISION_ID_LIST =
            "select \n" +
                    "    d.DIVISION_ID id, \n" +
                    "    d.DIVISION_CD divisionCd, \n" +
                    "    d.DIVISION_NAME divisionName, \n" +
                    "    w.WAREHOUSE_ID as warehouseId, \n" +
                    "    w.WAREHOUSE_CD warehouseCd, \n" +
                    "    w.WAREHOUSE_NAME warehouseName \n" +
                    "from M_部門 as d\n" +
                    "left join M_部門_倉庫 as dw on d.DIVISION_ID = dw.DIVISION_ID\n" +
                    "left join M_倉庫 as w on dw.WAREHOUSE_ID = w.WAREHOUSE_ID\n" +
                    "where d.DIVISION_ID IN :divisionIdList";


    public static final String SEARCH_DIVISION =
            "select \n" +
                    "    d.DIVISION_ID as id,\n" +
                    "    d.DIVISION_CD as divisionCd,\n" +
                    "    d.DIVISION_NAME as divisionName,\n" +
                    "    STRING_AGG(w.WAREHOUSE_CD, ', ') as warehouseCd,\n" +
                    "    STRING_AGG(w.WAREHOUSE_NAME, ', ') as warehouseName\n" +
                    "from M_部門 d\n" +
                    "join M_部門_倉庫 dw on d.DIVISION_ID = dw.DIVISION_ID\n" +
                    "join M_倉庫 w on dw.WAREHOUSE_ID = w.WAREHOUSE_ID\n" +
                    "where \n" +
                    "   (:divisionCd IS NULL OR d.DIVISION_CD like CONCAT('%', :divisionCd, '%'))\n" +
                    "   and (:divisionName IS NULL OR d.DIVISION_NAME like CONCAT('%', :divisionName, '%'))\n" +
                    "   and (:warehouseName IS NULL OR EXISTS (\n" +
                    "           select 1 \n" +
                    "           from M_倉庫 w2 \n" +
                    "           join M_部門_倉庫 dw2 on dw2.WAREHOUSE_ID = w2.WAREHOUSE_ID \n" +
                    "           where dw2.DIVISION_ID = d.DIVISION_ID \n" +
                    "           and w2.WAREHOUSE_NAME like CONCAT('%', :warehouseName, '%')\n" +
                    "       ))\n" +
                    "group by d.DIVISION_ID, d.DIVISION_CD, d.DIVISION_NAME\n";

}
