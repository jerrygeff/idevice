package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.event.common;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Notification<T> {
    private String method;
    private Params<T> params;
}
