package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.config;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author jerryge
 */
@Data
@Accessors(chain = true)
public class ISecureSDKConfig {
    private String host;
    private String appKey;
    private String appSecret;
    private String httpScheme;
    private String proxy;
    private ISecureExtendConfig extendConfig;
}
