package com.tpop.zaikokanri.master.dto;

import lombok.Setter;

@Setter
public class ScreenDisplaySettingDto implements IScreenDisplaySettingDto {

    private Integer menuId;

    private Integer functionId;

    private String displayName;

    private Integer parentId;

    private Integer displayOrder;

    private String functionSection;

    private String functionName;

    private String functionCode;

    private String url;

    @Override
    public Integer getMenuId() {
        return menuId;
    }

    @Override
    public Integer getFunctionId() {
        return functionId;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public Integer getParentId() {
        return parentId;
    }

    @Override
    public Integer getDisplayOrder() {
        return displayOrder;
    }

    @Override
    public String getFunctionSection() {
        return functionSection;
    }

    @Override
    public String getFunctionName() {
        return functionName;
    }

    @Override
    public String getFunctionCode() {
        return functionCode;
    }

    @Override
    public String getUrl() {
        return url;
    }
}
