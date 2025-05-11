package info.zhihui.idevice.core.sdk.dahua.icc.dto.brm.v5;

import com.dahuatech.hutool.http.ContentType;
import com.dahuatech.hutool.http.Method;
import com.dahuatech.icc.brm.constant.BrmConstant;
import com.dahuatech.icc.oauth.profile.IccProfile;
import info.zhihui.idevice.core.sdk.dahua.icc.dto.IccPageRequest;

import java.util.List;

/**
 * 设备分页查询
 *
 * @author 232676
 * @since 1.0.0 2020/11/5 11:27
 */
public class BrmDevicePageRequest extends IccPageRequest<BrmDevicePageResponse> {
    private static final String VERSION = "1.2.0";

    /**
     * 设备所属组织编码集合
     */
    private List<String> ownerCodes;
    /**
     * 是否获取设备所属组织子节点下设备记录
     */
    private Integer showChildNodeData;
    /**
     * 单元类型,多个
     */
    private List<Integer> unitTypes;
    /**
     * 设备大类
     */
    private List<Integer> categorys;
    /**
     * 设备小类,设备小类的格式是:大类_小类
     */
    private List<String> types;
    /**
     * 设备编码列表 最大支持500
     */
    private List<String> deviceCodes;
    /**
     * 设备标识码列表 最大支持500
     */
    private List<String> deviceSns;
    /**
     * 设备地址 最大支持500
     */
    private List<String> deviceIps;
    /**
     * isOnline
     */
    private Integer isOnline;

    public BrmDevicePageRequest() {
        super(String.format(IccProfile.URL_SCHEME + BrmConstant.BRM_URL_DEVICE_PAGE_POST, VERSION), Method.POST);
        httpRequest.contentType(ContentType.JSON.toString());
    }

    @Override
    public Class<BrmDevicePageResponse> getResponseClass() {
        return BrmDevicePageResponse.class;
    }

    public List<String> getOwnerCodes() {
        return ownerCodes;
    }

    public void setOwnerCodes(List<String> ownerCodes) {
        this.ownerCodes = ownerCodes;
        putBodyParameter("ownerCodes", ownerCodes);
    }

    public int getShowChildNodeData() {
        return showChildNodeData;
    }

    public void setShowChildNodeData(int showChildNodeData) {
        this.showChildNodeData = showChildNodeData;
        putBodyParameter("showChildNodeData", showChildNodeData);
    }

    public List<Integer> getUnitTypes() {
        return unitTypes;
    }

    public void setUnitTypes(List<Integer> unitTypes) {
        this.unitTypes = unitTypes;
        putBodyParameter("unitTypes", unitTypes);
    }

    public List<Integer> getCategorys() {
        return categorys;
    }

    public void setCategorys(List<Integer> categorys) {
        this.categorys = categorys;
        putBodyParameter("categorys", categorys);
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
        putBodyParameter("types", types);
    }

    public List<String> getDeviceCodes() {
        return deviceCodes;
    }

    public void setDeviceCodes(List<String> deviceCodes) {
        this.deviceCodes = deviceCodes;
        putBodyParameter("deviceCodes", deviceCodes);
    }

    public List<String> getDeviceSns() {
        return deviceSns;
    }

    public void setDeviceSns(List<String> deviceSns) {
        this.deviceSns = deviceSns;
        putBodyParameter("deviceSns", deviceSns);
    }

    public List<String> getDeviceIps() {
        return deviceIps;
    }

    public void setDeviceIps(List<String> deviceIps) {
        this.deviceIps = deviceIps;
        putBodyParameter("deviceIps", deviceIps);
    }

    public Integer getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Integer isOnline) {
        this.isOnline = isOnline;
        putBodyParameter("isOnline", isOnline);
    }

    @Override
    public String toString() {
        return "BrmDevicePageRequest{"
                + "pageNum="
                + pageNum
                + ", pageSize="
                + pageSize
                + ", ownerCodes="
                + ownerCodes
                + ", showChildNodeData="
                + showChildNodeData
                + ", unitTypes="
                + unitTypes
                + ", categorys="
                + categorys
                + ", types="
                + types
                + ", deviceCodes="
                + deviceCodes
                + ", deviceSns="
                + deviceSns
                + ", deviceIps="
                + deviceIps
                + ", isOnline="
                + isOnline
                + '}';
    }

}
