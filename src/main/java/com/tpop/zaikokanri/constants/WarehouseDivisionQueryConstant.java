package com.tpop.zaikokanri.constants;

public class WarehouseDivisionQueryConstant {

    public static final String SEARCH_WAREHOUSE_DIVISION_ID_BY_DIVISION_ID =
            "SELECT m.DIVI_WAREHOUSE_ID FROM M_部門_倉庫 m WHERE m.DIVISION_ID =:divisionId";

}
