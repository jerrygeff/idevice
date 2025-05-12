package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.access.v2;

import lombok.Data;

/**
 * @author jerryge
 */
@Data
public class DoorOnlineStatusInfo {
    private String deviceType;
    private String deviceIndexCode;
    private String regionIndexCode;
    private String collectTime;
    private String regionName;
    private String indexCode;
    private String cn;
    private String treatyType;
    private String manufacturer;
    private String ip;
    private Integer port;
    private Integer online;
}