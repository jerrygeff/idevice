package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.camera.v2;

import info.zhihui.idevice.core.sdk.hikvision.isecure.constants.ISecureCameraConstant;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.ISecureJsonPageRequest;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * 摄像头在线状态搜索请求
 */
@Getter
@Builder(builderClassName = "Builder")
public class CameraOnlineSearchRequest extends ISecureJsonPageRequest<CameraOnlineSearchResponse> {
    private static final String VERSION = "v1";
    private static final String API_URL = String.format(ISecureCameraConstant.CAMERA_ONLINE_SEARCH, VERSION);

    /** 区域编号 */
    private final String regionId;
    /** 是否包含下级区域（0-不包含，1-包含） */
    private final String includeSubNode;
    /** 资源唯一标识列表 */
    private final List<String> indexCodes;
    /** 在线状态（1-在线，0-离线，-1-未检测） */
    private final String status;

    private CameraOnlineSearchRequest(String regionId, String includeSubNode, List<String> indexCodes, String status) {
        super(API_URL);
        this.regionId = regionId;
        this.includeSubNode = includeSubNode;
        this.indexCodes = indexCodes;
        this.status = status;
    }

    @Override
    public Class<CameraOnlineSearchResponse> getResponseClass() {
        return CameraOnlineSearchResponse.class;
    }

    @Override
    public void validate() {
        super.validate();
    }
}