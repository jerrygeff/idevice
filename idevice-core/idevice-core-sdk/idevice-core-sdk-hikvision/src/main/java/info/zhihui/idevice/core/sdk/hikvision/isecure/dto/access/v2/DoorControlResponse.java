package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.access.v2;

import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.ISecureResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author jerryge
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DoorControlResponse extends ISecureResponse {
    private List<DoorControlResponseData> data;
}
