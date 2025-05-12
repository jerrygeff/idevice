package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.foundation.v2;

import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.ISecureResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jerryge
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PersonAddResponse extends ISecureResponse {
    private PersonAddResponseData data;
}
