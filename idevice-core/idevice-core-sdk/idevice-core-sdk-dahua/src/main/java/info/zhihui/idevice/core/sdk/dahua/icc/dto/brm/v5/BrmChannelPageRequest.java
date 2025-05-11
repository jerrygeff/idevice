package info.zhihui.idevice.core.sdk.dahua.icc.dto.brm.v5;

import com.dahuatech.hutool.http.ContentType;
import com.dahuatech.hutool.http.Method;
import com.dahuatech.icc.oauth.profile.IccProfile;
import info.zhihui.idevice.core.sdk.dahua.icc.dto.IccPageRequest;

import java.util.List;

import static info.zhihui.idevice.core.sdk.dahua.icc.constants.IccBrmConstant.BRM_URL_CHANNEL_PAGE_POST;
import static info.zhihui.idevice.core.sdk.dahua.icc.constants.IccBrmConstant.DEFAULT_PAGE_SIZE;

/**
 * 通道分页查询请求
 *
 * @author jerryge
 */
public class BrmChannelPageRequest extends IccPageRequest<BrmChannelPageResponse> {
    private static final String VERSION = "1.2.0";

    private String sortType;
    private String sort;
    private String ownerCode;
    private List<String> deviceCodeList;
    private List<String> channelCodeList;
    private Integer deviceCategory;
    private Integer deviceType;
    private List<Integer> unitTypeList;
    private List<String> channelTypeList;
    private Integer isOnline;
    private Integer access;
    private Integer isVirtual;
    private Integer stat;
    private Boolean includeSubOwnerCodeFlag;

    public BrmChannelPageRequest() {
        super(String.format(IccProfile.URL_SCHEME + BRM_URL_CHANNEL_PAGE_POST, VERSION), Method.POST);
        httpRequest.contentType(ContentType.JSON.toString());
        setPageNum(1);
        setPageSize(DEFAULT_PAGE_SIZE);
    }

    @Override
    public Class<BrmChannelPageResponse> getResponseClass() {
        return BrmChannelPageResponse.class;
    }

    // Getters and Setters
    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
        putBodyParameter("sortType", sortType);
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
        putBodyParameter("sort", sort);
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
        putBodyParameter("ownerCode", ownerCode);
    }

    public List<String> getDeviceCodeList() {
        return deviceCodeList;
    }

    public void setDeviceCodeList(List<String> deviceCodeList) {
        this.deviceCodeList = deviceCodeList;
        putBodyParameter("deviceCodeList", deviceCodeList);
    }

    public List<String> getChannelCodeList() {
        return channelCodeList;
    }

    public void setChannelCodeList(List<String> channelCodeList) {
        this.channelCodeList = channelCodeList;
        putBodyParameter("channelCodeList", channelCodeList);
    }

    public Integer getDeviceCategory() {
        return deviceCategory;
    }

    public void setDeviceCategory(Integer deviceCategory) {
        this.deviceCategory = deviceCategory;
        putBodyParameter("deviceCategory", deviceCategory);
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
        putBodyParameter("deviceType", deviceType);
    }

    public List<Integer> getUnitTypeList() {
        return unitTypeList;
    }

    public void setUnitTypeList(List<Integer> unitTypeList) {
        this.unitTypeList = unitTypeList;
        putBodyParameter("unitTypeList", unitTypeList);
    }

    public List<String> getChannelTypeList() {
        return channelTypeList;
    }

    public void setChannelTypeList(List<String> channelTypeList) {
        this.channelTypeList = channelTypeList;
        putBodyParameter("channelTypeList", channelTypeList);
    }

    public Integer getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Integer isOnline) {
        this.isOnline = isOnline;
        putBodyParameter("isOnline", isOnline);
    }

    public Integer getAccess() {
        return access;
    }

    public void setAccess(Integer access) {
        this.access = access;
        putBodyParameter("access", access);
    }

    public Integer getIsVirtual() {
        return isVirtual;
    }

    public void setIsVirtual(Integer isVirtual) {
        this.isVirtual = isVirtual;
        putBodyParameter("isVirtual", isVirtual);
    }

    public Integer getStat() {
        return stat;
    }

    public void setStat(Integer stat) {
        this.stat = stat;
        putBodyParameter("stat", stat);
    }

    public Boolean getIncludeSubOwnerCodeFlag() {
        return includeSubOwnerCodeFlag;
    }

    public void setIncludeSubOwnerCodeFlag(Boolean includeSubOwnerCodeFlag) {
        this.includeSubOwnerCodeFlag = includeSubOwnerCodeFlag;
        putBodyParameter("includeSubOwnerCodeFlag", includeSubOwnerCodeFlag);
    }
}