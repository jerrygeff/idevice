package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.event.common;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ExtEventIdentityCardInfo {
    private String Address;
    private String Birth;
    private String EndDate;
    private String IdNum;
    private String IssuingAuthority;
    private String Name;
    private int Nation;
    private int Sex;
    private String StartDate;
    private int TermOfValidity;
}
