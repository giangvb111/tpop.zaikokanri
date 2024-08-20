package com.tpop.zaikokanri.constants;

public class LocationQueryConstant {

    public static final String SEARCH_LOCATION_BY_LOCATION_ID =
            "SELECT t.LOCATION_ID as id ,\n" +
                    "t.LOCATION_CD as locationCd ,\n" +
                    "t.LOCATION_NAME as locationName ,\n" +
                    "s.WAREHOUSE_ID as warehouseId \n" +
                    "FROM M_棚番 AS t\n" +
                    "JOIN M_倉庫 AS s ON t.WAREHOUSE_ID = s.WAREHOUSE_ID \n" +
                    "WHERE t.LOCATION_ID =:locationId";

    public static final String SEARCH_LOCATION_PAGE =
            "SELECT t.LOCATION_ID as id ,\n" +
                    "t.LOCATION_CD as locationCd ,\n" +
                    "t.LOCATION_NAME as locationName ,\n" +
                    "s.WAREHOUSE_NAME as warehouseName \n" +
                    "FROM M_棚番 AS t\n" +
                    "JOIN M_倉庫 AS s ON t.WAREHOUSE_ID = s.WAREHOUSE_ID\n" +
                    "WHERE t.LOCATION_CD LIKE CONCAT('%', :locationCd, '%') \n" +
                    "AND t.LOCATION_NAME LIKE CONCAT('%', :locationName, '%') \n" +
                    "AND s.WAREHOUSE_NAME LIKE CONCAT('%', :warehouseName, '%')";
}
