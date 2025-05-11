package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.camera.v2;

import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.ISecureResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 回放取流响应
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PlaybackLinkResponse extends ISecureResponse {
    private PlaybackLinkResponseData data;
}