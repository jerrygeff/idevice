package info.zhihui.idevice.core.module.event.service.parser;

import info.zhihui.idevice.core.module.event.bo.EventBo;
import info.zhihui.idevice.core.module.event.bo.RawEventBo;

import java.util.List;

public interface EventParser<T extends EventBo> {

    /**
     * 解析事件
     * @param rawEventBo 事件原始数据
     * @return T 事件对象
     */
    List<T> parseEvent(RawEventBo rawEventBo);
}
