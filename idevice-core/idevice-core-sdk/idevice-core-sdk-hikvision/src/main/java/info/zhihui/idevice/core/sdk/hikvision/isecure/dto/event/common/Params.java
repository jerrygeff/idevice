package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.event.common;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class Params<T> {
    private String ability;
    private List<Event<T>> events;
    private String sendTime;
}
