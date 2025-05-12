package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.event.common;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Event<T> {
    private String seq;
    private String eventType;
    private String happenTime;
    private String srcIndex;
    private String srcName;
    private String srcParentIndex;
    private String srcType;
    private int status;
    private int timeout;
    private String eventId;
    private T data;
}
