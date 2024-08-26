package com.tpop.zaikokanri.master.repository;

import com.tpop.zaikokanri.master.entities.ScreenDisplayDetailSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreenDisplayDetailSettingRepository extends JpaRepository<ScreenDisplayDetailSetting, Integer> {

//    @Query(nativeQuery = true , value = MenuSettingQueryConstant.SEARCH_SCREEN_DISPLAY_SETTING)
//    List<IScreenDisplaySettingDto> getParentScreenDisplaySetting(@Param("lang") String lang);
//
//    @Query(nativeQuery = true , value = MenuSettingQueryConstant.SEARCH_CHILDREN_SCREEN_DISPLAY_SETTING)
//    List<IScreenDisplaySettingDto> getChildrenScreenDisplaySetting(List<Integer> parentIdList);

}
