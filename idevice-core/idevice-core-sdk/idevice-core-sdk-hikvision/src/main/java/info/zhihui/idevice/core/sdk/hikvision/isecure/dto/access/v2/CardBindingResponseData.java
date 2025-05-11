package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.access.v2;

import lombok.Data;

@Data
public class CardBindingResponseData {
    private String cardNo;
    private String personId;
    private String orgIndexCode;
    private Integer cardType;
}