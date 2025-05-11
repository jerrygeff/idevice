package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.event.common;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ExtEventCustomerNumInfo {
    private int AccessChannel;
    private int EntryTimes;
    private int ExitTimes;
    private int TotalTimes;
}
