package com.tpop.zaikokanri.constants;

public class CategoryQueryConstant {

    public static final String SEARCH_CATEGORY =
            "SELECT \n" +
                    "        c.CATEGORY_ID AS id,\n" +
                    "        c.CATEGORY_CD AS categoryCd,\n" +
                    "        c.MAJOR_CATEGORY AS majorCategory,\n" +
                    "        c.MEDIUM_CATEGORY AS mediumCategory,\n" +
                    "        c.SUB_CATEGORY AS subCategory\n" +
                    "    FROM \n" +
                    "        M_カテゴリ c\n" +
                    "    WHERE \n" +
                    "        (:#{#categoryDto.categoryCd} IS NULL OR c.CATEGORY_CD LIKE CONCAT('%', :#{#categoryDto.categoryCd}, '%'))\n" +
                    "        AND (:#{#categoryDto.majorCategory} IS NULL OR c.MAJOR_CATEGORY LIKE CONCAT('%', :#{#categoryDto.majorCategory}, '%'))\n" +
                    "        AND (:#{#categoryDto.mediumCategory} IS NULL OR c.MEDIUM_CATEGORY LIKE CONCAT('%', :#{#categoryDto.mediumCategory}, '%'))\n" +
                    "        AND (:#{#categoryDto.subCategory} IS NULL OR c.SUB_CATEGORY LIKE CONCAT('%', :#{#categoryDto.subCategory}, '%'))";

}
