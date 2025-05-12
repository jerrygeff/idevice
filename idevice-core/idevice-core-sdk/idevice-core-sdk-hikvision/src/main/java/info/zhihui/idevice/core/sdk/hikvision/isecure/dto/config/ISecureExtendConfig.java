package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.config;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author jerryge
 */
@Data
@Accessors(chain = true)
public class ISecureExtendConfig {
    private String tagId;
    private Boolean accessPermissionAutoSync;
}
