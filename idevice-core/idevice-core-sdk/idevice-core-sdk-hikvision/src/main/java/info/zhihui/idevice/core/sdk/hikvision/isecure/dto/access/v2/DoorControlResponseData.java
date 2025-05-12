package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.access.v2;

import lombok.Data;

/**
 * @author jerryge
 */
@Data
public class DoorControlResponseData {
    private Integer controlResultCode;
    private String controlResultDesc;
    private String doorIndexCode;
}
