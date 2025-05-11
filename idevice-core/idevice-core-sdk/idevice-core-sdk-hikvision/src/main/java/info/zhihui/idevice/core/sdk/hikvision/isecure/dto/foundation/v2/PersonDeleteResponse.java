package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.foundation.v2;

import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.ISecureResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author jerryge
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PersonDeleteResponse extends ISecureResponse {
    private List<PersonDeleteResult> data;
} 