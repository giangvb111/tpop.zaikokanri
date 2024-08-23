package com.tpop.zaikokanri.master.repository;

import com.tpop.zaikokanri.constants.MenuSettingQueryConstant;
import com.tpop.zaikokanri.master.dto.IScreenDisplaySettingDto;
import com.tpop.zaikokanri.master.dto.ScreenDisplaySettingDto;
import com.tpop.zaikokanri.master.entities.ScreenDisplaySetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScreenDisplaySettingRepository extends JpaRepository<ScreenDisplaySetting, Integer> {

    @Query(nativeQuery = true , value = MenuSettingQueryConstant.SEARCH_SCREEN_DISPLAY_SETTING)
    List<IScreenDisplaySettingDto> getParentScreenDisplaySetting(@Param("lang") String lang);

    @Query(nativeQuery = true , value = MenuSettingQueryConstant.SEARCH_CHILDREN_SCREEN_DISPLAY_SETTING)
    List<IScreenDisplaySettingDto> getChildrenScreenDisplaySetting(@Param("parentIdList") List<Integer> parentIdList);

}
