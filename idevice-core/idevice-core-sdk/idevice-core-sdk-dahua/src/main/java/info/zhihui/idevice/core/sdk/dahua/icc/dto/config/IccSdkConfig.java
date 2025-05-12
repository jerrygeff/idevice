package info.zhihui.idevice.core.sdk.dahua.icc.dto.config;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class IccSdkConfig {
    private String iccSdkHost;
    private String iccSdkClientId;
    private String iccSdkClientSecret;
    private String iccSdkUserName;
    private String iccSdkPassword;
    private String iccSdkGrantType;
    private Boolean isEnableHttpTest = false;
    private String port;
}
