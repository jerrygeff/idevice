package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.access.v2;

import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.ISecureResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jerryge
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ConfigAddResponse extends ISecureResponse {
    private ConfigOperateResponseData data;
}