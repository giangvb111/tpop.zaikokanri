package com.tpop.zaikokanri.master.repository;

import com.tpop.zaikokanri.constants.MenuSettingQueryConstant;
import com.tpop.zaikokanri.master.dto.IScreenDetailDisplaySettingDto;
import com.tpop.zaikokanri.master.entities.ScreenDisplayDetailSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScreenDisplayDetailSettingRepository extends JpaRepository<ScreenDisplayDetailSetting, Integer> {

    @Query(nativeQuery = true, value = MenuSettingQueryConstant.SEARCH_SCREEN_DETAIL_SETTING)
    List<IScreenDetailDisplaySettingDto> getScreenDetailSettingByFunctionCode(String functionCode, List<Integer> displaySection);

    @Query(nativeQuery = true, value = MenuSettingQueryConstant.GET_DISPLAY_SECTION_FUNCTION_CODE)
    List<Integer> getDisplaySectionByFunctionCode(String functionCode);
}
