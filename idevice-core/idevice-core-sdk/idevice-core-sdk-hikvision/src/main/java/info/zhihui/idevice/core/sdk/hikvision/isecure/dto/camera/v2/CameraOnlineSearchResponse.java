package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.camera.v2;

import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.ISecurePageResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 摄像头在线状态搜索响应
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CameraOnlineSearchResponse extends ISecurePageResponse<CameraOnlineInfo> {
}