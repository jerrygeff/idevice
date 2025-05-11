package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.access.v2;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CardBindingInfo {
    private String cardNo;
    private String personId;
    private String orgIndexCode;
    private Integer cardType;
}